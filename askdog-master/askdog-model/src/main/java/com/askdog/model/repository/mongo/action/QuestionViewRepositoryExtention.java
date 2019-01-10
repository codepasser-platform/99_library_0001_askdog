package com.askdog.model.repository.mongo.action;

import com.askdog.model.data.Actions.QuestionView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionViewRepositoryExtention {
    Page<QuestionView> findByUser(String userId, Pageable pageable);
}
