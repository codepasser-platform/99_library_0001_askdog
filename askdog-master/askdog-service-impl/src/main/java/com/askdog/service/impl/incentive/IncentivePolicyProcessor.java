package com.askdog.service.impl.incentive;

import com.askdog.model.entity.User;

public interface IncentivePolicyProcessor {
    int process(User user, int value);
}
