package com.askdog.service.proxy;

import com.askdog.model.entity.User;
import com.askdog.service.UserService;
import com.askdog.service.bo.AnswerDetail;
import com.askdog.service.bo.QuestionDetail;
import com.askdog.service.bo.UserDetail;
import com.askdog.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Override
    public Optional<User> findById(@Nonnull String userId) {
        return null;
    }

    @Nonnull
    @Override
    public Optional<User> findByName(@Nonnull String name) {
        return null;
    }

    @Nonnull
    @Override
    public Optional<User> findByEmail(@Nonnull String mail) {
        return null;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean existsByName(@Nonnull String name) {
        return false;
    }

    @Override
    public boolean existsByEmail(@Nonnull String mail) {
        return false;
    }

    @Override
    public UserDetail create(@Nonnull User user) throws ServiceException {
        return null;
    }

    @Override
    public void updateAvatar(@Nonnull String userId, @Nonnull String linkId) throws ServiceException {

    }

    @Override
    public User decorate(String userId) throws ServiceException {
        return null;
    }

    @Override
    public UserDetail confirmRegistration(@Nonnull String token) throws ServiceException {
        return null;
    }

    @Override
    public Page<QuestionDetail> findFollows(@Nonnull String userId, Pageable pageable) throws ServiceException {
        return null;
    }

    @Override
    public Page<AnswerDetail> findFavorites(@Nonnull String userId, Pageable pageable) throws ServiceException {
        return null;
    }

    @Override
    public Page<QuestionDetail> findViews(@Nonnull String userId, Pageable pageable) throws ServiceException {
        return null;
    }

    @Override
    public Page<QuestionDetail> findSharings(@Nonnull String userId, Pageable pageable) throws ServiceException {
        return null;
    }

    @Override
    public UserDetail findDetail(@Nonnull String userId) throws ServiceException {
        return null;
    }

    @Override
    public UserDetail updateDetail(@Nonnull String userId, @Nonnull UserDetail detail) throws ServiceException {
        return null;
    }

    @Override
    public Page<QuestionDetail> findQuestions(String userId, Pageable pageable) throws ServiceException {
        return null;
    }

    @Override
    public Page<AnswerDetail> findAnswers(String userId, Pageable pageable) throws ServiceException {
        return null;
    }

    @Nonnull
    @Override
    public Set<String> behaviorLabels(@Nonnull String userId) {
        return null;
    }

    @Nonnull
    @Override
    public UserDetail deductionPoints(@Nonnull String id, int points) throws ServiceException {
        return null;
    }

}