package com.askdog.service.impl.cell;

import com.askdog.model.data.EventLog;
import com.askdog.model.data.UserAttribute;
import com.askdog.model.entity.Label;
import com.askdog.model.entity.Question;
import com.askdog.model.entity.StorageLink;
import com.askdog.model.entity.User;
import com.askdog.model.entity.builder.LabelBuilder;
import com.askdog.model.repository.LabelRepository;
import com.askdog.model.repository.QuestionRepository;
import com.askdog.model.repository.UserRepository;
import com.askdog.model.repository.mongo.EventLogRepository;
import com.askdog.model.repository.mongo.UserAttributeRepository;
import com.askdog.service.bo.UserDetail;
import com.askdog.service.exception.ForbiddenException;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.storage.StorageRecorder;
import com.askdog.service.storage.Provider;
import com.askdog.service.storage.StorageAgent;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.Map.Entry;

import static com.askdog.model.data.inner.StorageProvider.ALI_OSS;
import static com.askdog.model.entity.inner.EventType.VIEW_QUESTION;
import static com.askdog.service.exception.ForbiddenException.Error.DEDUCTION_POINTS;
import static com.askdog.service.exception.NotFoundException.Error.USER;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.*;
import static org.elasticsearch.common.Strings.isNullOrEmpty;

@Component
@Transactional
public class UserCell {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAttributeRepository userAttributeRepository;

    @Autowired
    private LabelRepository labelRepository;
    @Autowired
    private EventLogRepository eventLogRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Provider(ALI_OSS)
    @Autowired
    private StorageAgent storageAgent;
    @Autowired
    private StorageRecorder storageRecorder;

    public void checkUserId(String userId, boolean optional) throws NotFoundException {
        if (!optional || !isNullOrEmpty(userId)) {
            checkUserId(userId);
        }
    }

    public void checkUserId(String userId) throws NotFoundException {
        if (!userRepository.exists(userId)) {
            throw new NotFoundException(USER);
        }
    }

    public User findExists(@Nonnull String uuid) throws NotFoundException {
        return checkExist(uuid);
    }

    public User checkExist(@Nonnull String uuid) throws NotFoundException {
        return userRepository.findByUuid(uuid).orElseThrow(() -> new NotFoundException(USER));
    }

    public Optional<User> find(@Nonnull String uuid) {
        return userRepository.findByUuid(uuid);
    }

    public List<User> find(@Nonnull List<String> userIds) {
        return newArrayList(userRepository.findAll(userIds));
    }

    @Cacheable(value = "cache:cell:user:detail", key = "#uuid")
    public UserDetail findDetail(String uuid) throws ServiceException {
        User user = findExists(uuid);
        UserDetail userDetail = new UserDetail().from(innerDecorate(user));
        Optional<UserAttribute> userAttributeOptional = userAttributeRepository.findByUserId(uuid);
        if (userAttributeOptional.isPresent()) {
            userDetail.setAttributes(userAttributeOptional.get());
        }
        return userDetail;
    }

    public UserDetail updateUserDetail(@Nonnull UserDetail detail) throws ServiceException {
        UserAttribute attribute = buildAttribute(detail);
        saveNewLabels(attribute);
        userAttributeRepository.save(attribute);
        // TODO: may be update any user's property on the db.
        // userRepository.updatePhone(userId, detail.getPhone());
        return findDetail(detail.getUuid());
    }

    public void updateAvatar(@Nonnull String userId, @Nonnull String linkId) throws NotFoundException {
        User user = checkExist(userId);
        StorageLink storageLink = storageRecorder.checkExist(linkId);
        user.setAvatar(storageLink);
        userRepository.save(user);
    }

    @Nonnull
    public Set<String> behaviorLabels(@Nonnull String userId) {
        Set<String> behaviorLabels = new HashSet<>(findUserLabels(userId));
        behaviorLabels.addAll(topRecentLabels(recentLabels(topRecentQuestions(userId))));
        return behaviorLabels;
    }

    @Nonnull
    public UserDetail deductionPoints(@Nonnull String id, int points) throws ServiceException {
        User user = findExists(id);
        if (points <= 0 || user.getPoints() < points) {
            throw new ForbiddenException(DEDUCTION_POINTS);
        }
        user.setPoints(user.getPoints() - points);
        userRepository.save(user);
        return new UserDetail().from(user);
    }

    public User innerDecorate(User user) throws ServiceException {
        StorageLink avatar = user.getAvatar();
        if (avatar != null) {
            user.setAvatarUrl(storageAgent.find(avatar.getUuid()).getUrl());
        }
        return user;
    }

    @Nonnull
    private Set<String> findUserLabels(@Nonnull String userId) {
        Optional<UserAttribute> foundUserAttribute = userAttributeRepository.findByUserId(userId);
        if (foundUserAttribute.isPresent()) {
            Set<String> interestTag = foundUserAttribute.get().getInterestTag();
            if (interestTag != null) {
                return interestTag;
            }
        }

        return new HashSet<>();
    }

    @Nonnull
    private UserAttribute buildAttribute(UserDetail detail) {
        UserAttribute attribute = detail.convert();
        userAttributeRepository.findByUserId(detail.getUuid()).ifPresent(saved -> attribute.setId(saved.getId()));
        Set<String> labelSet = Sets.newHashSet();
        if (attribute.getInterestTag() != null && attribute.getInterestTag().size() > 0) {
            attribute.getInterestTag().forEach(label -> labelSet.add(label.trim()));
            attribute.setInterestTag(labelSet);
        }
        return attribute;
    }

    private void saveNewLabels(UserAttribute attribute) {
        if (attribute.getInterestTag() != null && attribute.getInterestTag().size() > 0) {
            attribute.getInterestTag().stream().forEach(label -> {
                Optional<Label> optionalSavedLabel = labelRepository.findByName(label);
                if (!optionalSavedLabel.isPresent()) {
                    labelRepository.save(LabelBuilder.build(label));
                }
            });
        }
    }

    @Nonnull
    private List<Question> topRecentQuestions(String userId) {
        List<EventLog> topViewQuestions = eventLogRepository.topTargetByPerformerAndEventType(userId, VIEW_QUESTION, 100);
        List<String> topQuestionIds = topViewQuestions.stream().map(EventLog::getTargetId).collect(toList());
        if (topQuestionIds.size() > 0) {
            return questionRepository.findByUuidIn(topQuestionIds);
        }

        return new ArrayList<>();
    }

    @Nonnull
    private List<Label> recentLabels(@Nonnull List<Question> recentQuestions) {
        return recentQuestions.stream().map(Question::getLabels)
                .reduce(new ArrayList<>(), (left, right) -> {
                    left.addAll(right);
                    return left;
                });
    }

    @Nonnull
    private List<String> topRecentLabels(@Nonnull List<Label> questionLabels) {
        Map<String, Integer> groupLabels = questionLabels.stream().collect(groupingBy(Label::getName, summingInt(item -> 1)));
        List<Entry<String, Integer>> sortLabels = newArrayList(groupLabels.entrySet());
        if (sortLabels.size() > 0) {
            Collections.sort(sortLabels, (left, right) -> ((Integer) ((Entry) left).getValue()).compareTo((Integer) ((Entry) right).getValue()));
            return sortLabels.stream().map(Entry::getKey).collect(toList()).stream().limit(10).collect(toList());
        }

        return new ArrayList<>();
    }
}
