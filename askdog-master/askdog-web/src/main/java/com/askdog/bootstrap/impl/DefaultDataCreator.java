package com.askdog.bootstrap.impl;

import com.askdog.bootstrap.DataCreator;
import com.askdog.model.entity.*;
import com.askdog.model.entity.builder.LabelBuilder;
import com.askdog.model.repository.*;
import com.askdog.service.event.annotation.TriggerEvent;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.askdog.model.entity.inner.EventType.CREATE_QUESTION;

@Component
public class DefaultDataCreator implements DataCreator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private IncentivePolicyRepository incentivePolicyRepository;

    @Autowired
    private AopSupport aopSupport;

    @Override
    public DataCreator user(User user) {
        userRepository.save(user);
        return this;
    }

    @Override
    public DataCreator question(Question question) {
        List<Label> givenLabels = question.getLabels();
        updateQuestion(question, givenLabels, getSavedLabelMap(givenLabels));
        aopSupport.createQuestion(question);
        return this;
    }

    @Override
    public DataCreator label(Set<String> labelNames) {
        labelRepository.save(LabelBuilder.of(Sets.newHashSet(labelNames)));
        return this;
    }

    @Override
    public DataCreator template(Template template) {
        templateRepository.save(template);
        return this;
    }

    @Override
    public DataCreator incentivePolicy(IncentivePolicy incentivePolicy) {
        incentivePolicyRepository.save(incentivePolicy);
        return this;
    }

    private Map<String, Label> getSavedLabelMap(List<Label> givenLabels) {
        List<String> names = givenLabels.stream().map(Label::getName).collect(Collectors.toList());
        List<Label> savedLabels = labelRepository.findByNameIn(names);
        return savedLabels.stream().collect(Collectors.toMap(Label::getName, label -> label));
    }

    private void updateQuestion(Question question, List<Label> givenLabels, Map<String, Label> savedLabelMaps) {
        List<Label> labels = givenLabels.stream().map(label -> {
            if (savedLabelMaps.containsKey(label.getName())) {
                return savedLabelMaps.get(label.getName());
            }

            return label;
        }).collect(Collectors.toList());
        question.setLabels(labels);
    }

    @Component
    public static class AopSupport {

        @Autowired
        private QuestionRepository questionRepository;

        @TriggerEvent(@TriggerEvent.TriggerEventItem(performerId = "question.author.uuid", eventType = CREATE_QUESTION, targetId = "returnValue.uuid"))
        public Question createQuestion(Question question) {
            return questionRepository.save(question);
        }
    }
}