package com.askdog.service.impl.event.log;

import com.askdog.model.entity.Answer;
import com.askdog.model.repository.AnswerRepository;
import com.askdog.service.event.annotation.EventExtraData;
import com.askdog.service.event.annotation.ExtraDataCreator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.askdog.model.entity.inner.EventType.DELETE_ANSWER;

@EventExtraData(type = DELETE_ANSWER)
public class DeleteAnswerExtraDataCreator implements ExtraDataCreator<String> {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public String prepare(Object prepareParam) {
        String answerId = (String) prepareParam;
        Optional<Answer> foundAnswer = answerRepository.findByUuid(answerId);
        if (foundAnswer.isPresent()) {
            return foundAnswer.get().getQuestion().getUuid();
        }

        return null;
    }
}
