package com.askdog.bootstrap;

import com.askdog.model.entity.Label;
import com.askdog.model.entity.User;

import java.util.Optional;

public interface DataFinder {
    Optional<User> findUser(String identifier);
    Optional<Label> findLabel(String labelName);
}
