package com.askdog.service.snapshot;

import com.askdog.model.data.snapshot.QuestionSnapshot;
import com.askdog.service.bo.QuestionDetail;
import com.askdog.service.exception.NotFoundException;

import java.util.Optional;

public interface QuestionSnapshotService {

    QuestionSnapshot save(QuestionDetail questionDetail);

    QuestionSnapshot findByQuestionId(String questionId) throws NotFoundException;
}
