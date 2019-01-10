package com.askdog.bootstrap.impl;

import com.askdog.bootstrap.DataFinder;
import com.askdog.model.entity.Label;
import com.askdog.model.entity.User;
import com.askdog.model.repository.LabelRepository;
import com.askdog.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultDataFinder implements DataFinder {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Override
    public Optional<User> findUser(String identifier) {
        return identifier.contains("@") ? userRepository.findByEmail(identifier) : userRepository.findByName(identifier);
    }

    @Override
    public Optional<Label> findLabel(String labelName) {
        return labelRepository.findByName(labelName);
    }
}
