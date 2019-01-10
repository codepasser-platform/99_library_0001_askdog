package com.askdog.service;

import com.askdog.model.entity.IncentivePolicy;

import javax.annotation.Nonnull;

public interface IncentivePolicyService {
    @Nonnull IncentivePolicy save(@Nonnull IncentivePolicy incentivePolicy);
}
