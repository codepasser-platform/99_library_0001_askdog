package com.askdog.service.impl.snapshot;

import com.askdog.model.data.snapshot.AnswerSnapshot;
import com.askdog.model.repository.mongo.snapshot.AnswerSnapshotRepository;
import com.askdog.service.bo.AnswerDetail;
import com.askdog.service.bo.CommentDetail;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.snapshot.AnswerSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

import static com.askdog.service.exception.NotFoundException.Error.ANSWER_SNAPSHOT;

@Service
public class AnswerSnapshotServiceImpl implements AnswerSnapshotService {

    @Autowired private AnswerSnapshotRepository answerSnapshotRepository;

    public AnswerSnapshot save(AnswerDetail answerDetail) {
        return answerSnapshotRepository.save(new AnswerSnapshot()
                .setAnswerId(answerDetail.getUuid())
                .setQuestionId(answerDetail.getQuestion().getUuid())
                .setContent(answerDetail.getContent())
                .setAnonymous(answerDetail.isAnonymous())
                .setAnswererId(answerDetail.getAnswerer().getUuid())
                .setLanguage(answerDetail.getLanguage())
                .setUpVoteCount(answerDetail.getUpVoteCount())
                .setDownVoteCount(answerDetail.getDownVoteCount())
                .setCreationTime(answerDetail.getCreationTime())
                .setCommentIds(answerDetail.getComments().stream().map(CommentDetail::getUuid).collect(Collectors.toList()))
                .setSnapshotTime(new Date()));
    }

    @Override public AnswerSnapshot findByAnswerId(String answerId) throws NotFoundException {
        return answerSnapshotRepository.findByAnswerId(answerId).orElseThrow(() -> new NotFoundException(ANSWER_SNAPSHOT));
    }
}
