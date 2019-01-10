package com.askdog.model.repository;

import com.askdog.model.entity.AnswerComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerCommentRepository extends CrudRepository<AnswerComment, String> {
    Optional<AnswerComment> findByUuid(String answerCommentId);

    Optional<AnswerComment> findByCommenter_UuidAndUuid(String opUserId, String answerCommentId);

    List<AnswerComment> findByAnswerUuidOrderByCreationTimeDesc(String answerId);

    long countByAnswerUuid(String answerId);
}
