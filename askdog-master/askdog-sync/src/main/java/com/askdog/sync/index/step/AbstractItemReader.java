package com.askdog.sync.index.step;

import com.askdog.model.entity.inner.TextContent;
import com.askdog.service.QuestionService;
import com.askdog.service.bo.AnswerDetail;
import com.askdog.service.bo.QuestionDetail;
import com.askdog.service.bo.UserDetail;
import com.askdog.service.impl.cell.AnswerCell;
import com.askdog.service.impl.cell.QuestionCell;
import com.askdog.sync.index.QuestionIndex;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Joiner.on;
import static java.util.stream.Collectors.toList;

class AbstractItemReader implements ItemReader<QuestionIndex> {

    @Autowired private QuestionService questionService;
    @Autowired private QuestionCell questionCell;
    @Autowired private AnswerCell answerCell;

    private Iterator<QuestionIndex> questions;

    @Override
    public QuestionIndex read() throws Exception {
        if (questions.hasNext()) {
            return questions.next();
        }

        return null;
    }

    void setQuestions(Iterator<QuestionIndex> questions) {
        this.questions = questions;
    }

    QuestionIndex.User toIndexUser(UserDetail user) {
        QuestionIndex.User author = new QuestionIndex.User();
        author.setId(user.getUuid());
        author.setName(user.getName());
        author.setAvatar(user.getAvatar());
        return author;
    }

    public QuestionIndex.Answer toIndexAnswer(AnswerDetail answer) {

        if (answer == null) {
            return null;
        }

        QuestionIndex.Answer indexAnswer = null;
        switch (answer.getContent().getType()) {
            case TEXT:
                QuestionIndex.TextAnswer textAnswer = new QuestionIndex.TextAnswer();
                textAnswer.setContentText(((TextContent) answer.getContent()).getContent());
                indexAnswer = textAnswer;
                break;
            case PIC:
                QuestionIndex.PicAnswer picAnswer = new QuestionIndex.PicAnswer();
                // TODO picAnswer.setContentPic(((PicContent) answer.getContent()).getContent());
                indexAnswer = picAnswer;
                break;
        }

        indexAnswer.setId(answer.getUuid());
        indexAnswer.setAnonymous(answer.isAnonymous());
        indexAnswer.setAnswerer(toIndexUser(answer.getAnswerer()));
        indexAnswer.setType(answer.getContent().getType());
        indexAnswer.setScore(answerCell.calculateScore(answer));
        indexAnswer.setUpVoteCount(answer.getUpVoteCount());
        indexAnswer.setDownVoteCount(answer.getDownVoteCount());
        indexAnswer.setFavoriteCount(answer.getFavoriteCount());
        indexAnswer.setShareCount(answer.getShareCount());
        indexAnswer.setCreateTime(answer.getCreationTime());
        return indexAnswer;
    }

    List<String> getAnswerContents(QuestionDetail question) {
        return question.getAnswers().stream().map(answer -> answer.getContent().textContent()).collect(toList());
    }

    QuestionIndex.Answer findNewestAnswer(String questionId) {
        return questionService.findLatestAnswer(questionId).map(this::toIndexAnswer).orElse(null);
    }

    public QuestionIndex toQuestionIndex(QuestionDetail question) {
        String questionId = question.getUuid();
        TextContent content = (TextContent) question.getContent();
        QuestionIndex questionIndex = new QuestionIndex();
        questionIndex.setId(questionId);
        questionIndex.setSubject(question.getSubject());
        questionIndex.setContent(content.getContent());
        questionIndex.setAnswerCount(question.getAnswers().size());
        questionIndex.setAnonymous(question.isAnonymous());
        questionIndex.setExperience(question.isExperience());
        questionIndex.setCreateTime(question.getCreationTime());
        questionIndex.setLabels(on(' ').skipNulls().join(question.getLabels()));
        questionIndex.setFullAnswerContent(on(' ').skipNulls().join(getAnswerContents(question)));
        questionIndex.setState(question.getState());
        questionIndex.setLanguage(question.getLanguage());
        questionIndex.setAuthor(toIndexUser(question.getAuthor()));

        QuestionIndex.Answer bestAnswer = toIndexAnswer(question.getBestAnswer());
        questionIndex.setBestAnswer(bestAnswer);
        questionIndex.setAnswerScore(bestAnswer != null ? bestAnswer.getScore() : null); // TODO all answer score

        QuestionIndex.Answer newestAnswer = findNewestAnswer(questionId);
        questionIndex.setNewestAnswer(newestAnswer);

        questionIndex.setUpVoteCount(question.getUpVoteCount());
        questionIndex.setDownVoteCount(question.getDownVoteCount());
        questionIndex.setViewCount(question.getViewCount());
        questionIndex.setFollowCount(question.getFollowCount());
        questionIndex.setHotScore(questionCell.calculateScore(question)); // TODO
        // questionIndex.setShareCount(question.getS);
        // questionIndex.setLocation(null);

        return questionIndex;
    }

}
