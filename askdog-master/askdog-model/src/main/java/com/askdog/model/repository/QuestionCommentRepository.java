package com.askdog.model.repository;

import com.askdog.model.entity.QuestionComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionCommentRepository extends CrudRepository<QuestionComment, String> {
    Optional<QuestionComment> findByUuid(String questionCommentId);

    Optional<QuestionComment> findByCommenter_UuidAndUuid(String opUserId, String questionCommentId);

    List<QuestionComment> findByQuestionUuidOrderByCreationTimeDesc(String questionId);

    long countByQuestionUuid(String questionId);
}
