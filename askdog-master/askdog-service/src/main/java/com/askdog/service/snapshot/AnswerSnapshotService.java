package com.askdog.service.snapshot;

import com.askdog.model.data.snapshot.AnswerSnapshot;
import com.askdog.service.bo.AnswerDetail;
import com.askdog.service.exception.NotFoundException;

public interface AnswerSnapshotService {

    AnswerSnapshot save(AnswerDetail answerDetail);

    AnswerSnapshot findByAnswerId(String answerId) throws NotFoundException;
}
