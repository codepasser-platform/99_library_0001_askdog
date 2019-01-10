package com.askdog.service.impl;

import com.askdog.model.entity.Label;
import com.askdog.model.entity.User;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface LabelService {

    Optional<Label> findByName(@Nonnull String name);

}
