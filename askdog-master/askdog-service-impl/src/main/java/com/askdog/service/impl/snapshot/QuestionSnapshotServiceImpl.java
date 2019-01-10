package com.askdog.service.impl.snapshot;

import com.askdog.model.data.snapshot.QuestionSnapshot;
import com.askdog.model.repository.mongo.snapshot.QuestionSnapshotRepository;
import com.askdog.service.bo.AnswerDetail;
import com.askdog.service.bo.CommentDetail;
import com.askdog.service.bo.QuestionDetail;
import com.askdog.service.exception.NotFoundException;
import com.askdog.service.snapshot.QuestionSnapshotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

import static com.askdog.service.exception.NotFoundException.Error.QUESTION_SNAPSHOT;

@Service
public class QuestionSnapshotServiceImpl implements QuestionSnapshotService {

    @Autowired
    private QuestionSnapshotRepository questionSnapshotRepository;

    @Override
    public QuestionSnapshot save(QuestionDetail questionDetail) {
        return questionSnapshotRepository.save(new QuestionSnapshot()
                .setQuestionId(questionDetail.getUuid())
                .setSubject(questionDetail.getSubject())
                .setContent(questionDetail.getContent())
                .setAuthorId(questionDetail.getAuthor().getUuid())
                .setAnonymous(questionDetail.isAnonymous())
                .setShare(questionDetail.isExperience())
                .setState(questionDetail.getState())
                .setLanguage(questionDetail.getLanguage())
                .setLocation(questionDetail.getLocation())
                .setCreationTime(questionDetail.getCreationTime())
                .setLabelNames(questionDetail.getLabels())
                .setViewCount(questionDetail.getViewCount())
                .setFollowCount(questionDetail.getFollowCount())
                .setUpVoteCount(questionDetail.getUpVoteCount())
                .setDownVoteCount(questionDetail.getDownVoteCount())
                .setAnswerIds(questionDetail.getAnswers().stream().map(AnswerDetail::getUuid).collect(Collectors.toList()))
                .setCommentIds(questionDetail.getComments().stream().map(CommentDetail::getUuid).collect(Collectors.toList()))
                .setQrLink(questionDetail.getQrLink())
                .setBestAnswerId(questionDetail.getBestAnswer() == null ? null : questionDetail.getBestAnswer().getUuid())
                .setSnapshotTime(new Date()));
    }


    @Override
    public QuestionSnapshot findByQuestionId(String questionId) throws NotFoundException {
        return questionSnapshotRepository.findByQuestionId(questionId).orElseThrow(() -> new NotFoundException(QUESTION_SNAPSHOT));
    }
}
