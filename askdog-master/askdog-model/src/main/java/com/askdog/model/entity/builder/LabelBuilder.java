package com.askdog.model.entity.builder;

import com.askdog.model.entity.Label;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class LabelBuilder {

    public static List<Label> of(Set<String> labelNames) {
        if (labelNames == null || labelNames.isEmpty()) return null;
        return labelNames.stream().map(labelName -> {
            Label label = new Label();
            label.setName(labelName);
            return label;
        }).collect(Collectors.toList());
    }

    public static Label build(String labelName) {
        Assert.notNull(labelName);
        Label label = new Label();
        label.setName(labelName);
        return label;
    }

}
