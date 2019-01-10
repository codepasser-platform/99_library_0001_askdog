package com.askdog.service;

import com.askdog.model.entity.User;
import com.askdog.service.bo.AnswerDetail;
import com.askdog.service.bo.QuestionDetail;
import com.askdog.service.bo.UserDetail;
import com.askdog.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    Optional<User> findById(@Nonnull String userId);

    @Nonnull
    Optional<User> findByName(@Nonnull String name);

    @Nonnull
    Optional<User> findByEmail(@Nonnull String mail);

    User save(User user);

    boolean existsByName(@Nonnull String name);

    boolean existsByEmail(@Nonnull String mail);

    UserDetail create(@Nonnull User user) throws ServiceException;

    void updateAvatar(@Nonnull String userId, @Nonnull String linkId) throws ServiceException;

    User decorate(String userId) throws ServiceException;

    UserDetail confirmRegistration(@Nonnull String token) throws ServiceException;

    Page<QuestionDetail> findFollows(@Nonnull String userId, Pageable pageable) throws ServiceException;

    Page<AnswerDetail> findFavorites(@Nonnull String userId, Pageable pageable) throws ServiceException;

    Page<QuestionDetail> findViews(@Nonnull String userId, Pageable pageable) throws ServiceException;

    Page<QuestionDetail> findSharings(@Nonnull String userId, Pageable pageable) throws ServiceException;

    UserDetail findDetail(@Nonnull String userId) throws ServiceException;

    UserDetail updateDetail(@Nonnull String userId, @Nonnull UserDetail detail) throws ServiceException;

    Page<QuestionDetail> findQuestions(String userId, Pageable pageable) throws ServiceException;

    Page<AnswerDetail> findAnswers(String userId, Pageable pageable) throws ServiceException;

    @Nonnull
    Set<String> behaviorLabels(@Nonnull String userId);

    @Nonnull
    UserDetail deductionPoints(@Nonnull String id, int points) throws ServiceException;

}
