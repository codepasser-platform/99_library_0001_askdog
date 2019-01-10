package com.askdog.model.repository;

import com.askdog.model.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, String>, JpaSpecificationExecutor<Answer> {
    Optional<Answer> findByUuid(String answerId);
    List<Answer> findAllByQuestionUuid(String questionId);
    long countByQuestionUuid(String questionId);
    Page<Answer> findByQuestionUuid(String questionUuid, Pageable paging);
    Optional<Answer> findByAnswerer_UuidAndUuid(String answererId, String answerId);
    Optional<Answer> findTopByQuestionUuidOrderByCreationTimeDesc(String questionId);
    Optional<Answer> findByAnswererUuidAndQuestionUuid(String answererId, String questionId);
    Page<Answer> findByAnswerer_Uuid(String userId, Pageable pageable);
}
