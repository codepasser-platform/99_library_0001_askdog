package com.askdog.service.impl;

import com.askdog.model.entity.User;
import com.askdog.model.entity.inner.IncentiveReason;
import com.askdog.model.entity.inner.IncentiveType;
import com.askdog.model.repository.AnswerRepository;
import com.askdog.model.repository.QuestionRepository;
import com.askdog.model.repository.UserRepository;
import com.askdog.model.repository.mongo.action.AnswerFavoriteRepository;
import com.askdog.model.repository.mongo.action.QuestionFollowRepository;
import com.askdog.model.repository.mongo.action.QuestionViewRepository;
import com.askdog.model.validation.Group.Create;
import com.askdog.service.IncentiveLogService;
import com.askdog.service.UserService;
import com.askdog.service.bo.AnswerDetail;
import com.askdog.service.bo.QuestionDetail;
import com.askdog.service.bo.UserDetail;
import com.askdog.service.exception.ConflictException;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.exception.ServiceException;
import com.askdog.service.impl.annotation.EvictUserCache;
import com.askdog.service.impl.cell.AnswerCell;
import com.askdog.service.impl.cell.QuestionCell;
import com.askdog.service.impl.cell.UserCell;
import com.askdog.service.event.ListenerRegistry;
import com.askdog.service.event.core.EventBuilder;
import com.askdog.service.impl.mail.RegistrationTokenMail;
import com.askdog.service.utils.ConvertFunction;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.askdog.common.RegexPattern.REGEX_MAIL;
import static com.askdog.common.RegexPattern.REGEX_USER_NAME;
import static com.askdog.model.entity.inner.EventType.PERFECT_PERSONAL_INFORMATION;
import static com.askdog.model.entity.inner.user.UserStatus.MAIL_CONFIRMED;
import static com.askdog.service.exception.ConflictException.Error.USER_MAIL;
import static com.askdog.service.exception.ConflictException.Error.USER_NAME;
import static com.askdog.service.exception.NotFoundException.Error.USER;
import static com.google.common.base.Preconditions.checkArgument;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RegistrationTokenMail registrationTokenMail;
    @Autowired
    private AnswerFavoriteRepository answerFavoriteRepository;
    @Autowired
    private QuestionFollowRepository questionFollowRepository;
    @Autowired
    private QuestionViewRepository questionViewRepository;
    @Autowired
    private AnswerCell answerCell;
    @Autowired
    private UserCell userCell;
    @Autowired
    private QuestionCell questionCell;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ListenerRegistry listenerRegistry;
    @Autowired
    private IncentiveLogService incentiveLogService;

    @Override
    public Optional<User> findById(@Nonnull String userId) {
        return userRepository.findByUuid(userId);
    }

    @Nonnull
    @Override
    public Optional<User> findByName(@Nonnull String name) {
        checkArgument(name.matches(REGEX_USER_NAME), "user name not right");
        return userRepository.findByName(name);
    }

    @Nonnull
    @Override
    public Optional<User> findByEmail(@Nonnull String mail) {
        checkArgument(mail.matches(REGEX_MAIL), "mail address not right or not support");
        return userRepository.findByEmail(mail);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByName(@Nonnull String name) {
        return name.matches(REGEX_USER_NAME) && userRepository.existsByName(name);
    }

    @Override
    public boolean existsByEmail(@Nonnull String mail) {
        return mail.matches(REGEX_MAIL) && userRepository.existsByEmail(mail);
    }

    @Override
    public UserDetail create(@Nonnull @Validated(Create.class) User user) throws ServiceException {
        checkUserExistence(user);
        user.setRegistrationTime(new Date());
        // TODO the password encode should be done in client ?
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        registrationTokenMail.send(savedUser);
        return userCell.findDetail(savedUser.getUuid());
    }

    @Override
    @EvictUserCache
    public void updateAvatar(@Nonnull String userId, @Nonnull String linkId) throws ServiceException {
        userCell.updateAvatar(userId, linkId);
    }

    @Override
    public User decorate(String userId) throws ServiceException {
        Optional<User> foundUser = userRepository.findByUuid(userId);
        if (foundUser.isPresent()) {
            return userCell.innerDecorate(foundUser.get());
        }
        return null;
    }

    @Override
    public UserDetail confirmRegistration(@Nonnull String token) throws ServiceException {
        String mail = registrationTokenMail.redeemToken(token);
        User user = userRepository.findByEmail(mail).orElseThrow(() -> new NotFoundException(USER));
        user.addStatus(MAIL_CONFIRMED);
        userRepository.save(user);
        return userCell.findDetail(user.getUuid());
    }

    @Override
    public Page<AnswerDetail> findFavorites(@Nonnull String userId, Pageable pageable) throws ServiceException {
        return rePage(answerFavoriteRepository.findByUser(userId, pageable), pageable, e -> answerCell.findDetailElseSnapshot(userId, e.getTarget(), true));
    }

    @Override
    public Page<QuestionDetail> findFollows(@Nonnull String userId, Pageable pageable) throws ServiceException {
        return rePage(questionFollowRepository.findByUser(userId, pageable), pageable, e -> questionCell.findDetailElseSnapshot(userId, e.getTarget(), true));
    }

    @Override
    public Page<QuestionDetail> findViews(@Nonnull String userId, Pageable pageable) throws ServiceException {
        return rePage(questionViewRepository.findByUser(userId, pageable), pageable, e -> questionCell.findDetailElseSnapshot(userId, e.getTarget(), true));
    }

    @Override
    public Page<QuestionDetail> findSharings(@Nonnull String userId, Pageable pageable) throws ServiceException {
        return rePage(questionRepository.findByAuthor_UuidAndExperienceTrue(userId, pageable), pageable, e -> questionCell.findDetail(userId, e.getUuid(), true));
    }

    @Override
    public Page<QuestionDetail> findQuestions(String userId, Pageable pageable) throws ServiceException {
        return rePage(questionRepository.findByAuthor_Uuid(userId, pageable), pageable, e -> questionCell.findDetail(userId, e.getUuid(), true));
    }

    @Override
    public Page<AnswerDetail> findAnswers(String userId, Pageable pageable) throws ServiceException {
        return rePage(answerRepository.findByAnswerer_Uuid(userId, pageable), pageable, e -> answerCell.findDetail(userId, e.getUuid(), true));
    }

    @Nonnull
    @Override
    public Set<String> behaviorLabels(@Nonnull String userId) {
        return userCell.behaviorLabels(userId);
    }

    @Nonnull
    @Override
    public UserDetail deductionPoints(@Nonnull String id, int points) throws ServiceException {
        return userCell.deductionPoints(id, points);
    }

    @Override
    public UserDetail findDetail(@Nonnull String uuid) throws ServiceException {
        return userCell.findDetail(uuid);
    }

    @Override
    @EvictUserCache
    public UserDetail updateDetail(@Nonnull String userId, @Nonnull UserDetail detail) throws ServiceException {
        boolean isPerfectionBeforeUpdate = isProfilePerfection(userCell.findDetail(userId));

        UserDetail userDetail = userCell.updateUserDetail(detail.setUuid(userId));

        boolean isPerfectionAfterUpdate = isProfilePerfection(userCell.findDetail(userId));
        boolean isHaveIncentive = incentiveLogService.findByUserAndIncentiveTypeAndIncentiveReson(userId, IncentiveType.POINTS, IncentiveReason.PERFECT_PERSONAL_INFORMATION).isPresent();

        if (!isPerfectionBeforeUpdate && isPerfectionAfterUpdate && !isHaveIncentive) {
            listenerRegistry.fire(EventBuilder.getBuilder()
                    .setPerformerId(userId)
                    .setEventType(PERFECT_PERSONAL_INFORMATION)
                    .build());
        }

        return userDetail;
    }

    private boolean isProfilePerfection(UserDetail userDetail) {
        return !StringUtils.isEmpty(userDetail.getPhone())
                && userDetail.getGender() != null
                && userDetail.getAddress() != null
                && !StringUtils.isEmpty(userDetail.getOccupation())
                && !StringUtils.isEmpty(userDetail.getMajor())
                && !StringUtils.isEmpty(userDetail.getSchool())
                && userDetail.getInterestTag() != null && userDetail.getInterestTag().size() > 0;

    }

    private <T, E> PageImpl<E> rePage(Page<T> page, Pageable pageable, ConvertFunction<T, E> convert) throws ServiceException {
        List<E> result = Lists.newArrayList();
        if (page.hasContent()) {
            for (T t : page.getContent())
                result.add(convert.apply(t));
        }
        return new PageImpl<>(result, pageable, page.getTotalElements());
    }

    private void checkUserExistence(User user) throws ServiceException {
        if (userRepository.existsByName(user.getName())) {
            throw new ConflictException(USER_NAME);
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException(USER_MAIL);
        }
    }

}