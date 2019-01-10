package com.askdog.service.impl;

import com.askdog.model.entity.Label;
import com.askdog.model.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Optional;

@Service
public class LabelServiceImpl implements LabelService {

    @Autowired private LabelRepository labelRepository;

    @Override
    public Optional<Label> findByName(@Nonnull String name) {
        return labelRepository.findByName(name);
    }
}
