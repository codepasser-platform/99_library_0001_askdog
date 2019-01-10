package com.askdog.model.repository;

import com.askdog.model.entity.Question;
import com.askdog.model.entity.Question_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.askdog.model.entity.inner.State.OPEN;

@Repository
public interface QuestionRepository extends CrudRepository<Question, String>, JpaSpecificationExecutor<Question> {

    Optional<Question> findByUuid(String questionId);

    Optional<Question> findByAuthor_UuidAndUuid(String authorId, String questionId);

    Page<Question> findByAuthor_Uuid(String authorId, Pageable pageable);

    Page<Question> findByAuthor_UuidAndExperienceTrue(String authorId, Pageable pageable);

    List<Question> findByUuidIn(Collection<String> questionIds);

    @Modifying
    @Transactional
    @Query("delete from Question q where q.author.uuid = ?1")
    void deleteByAuthor_Uuid(String authorId);


    class QuestionSpecs {

        public static Specification<Question> opened(Locale language) {
            return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(
                    criteriaBuilder.equal(root.get(Question_.state), OPEN),
                    criteriaBuilder.equal(root.get(Question_.language), language)
            );
        }
    }
}
