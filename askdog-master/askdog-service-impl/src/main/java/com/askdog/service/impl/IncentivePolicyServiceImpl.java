package com.askdog.service.impl;

import com.askdog.model.entity.IncentivePolicy;
import com.askdog.model.repository.IncentivePolicyRepository;
import com.askdog.service.IncentivePolicyService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import javax.validation.Valid;

public class IncentivePolicyServiceImpl implements IncentivePolicyService {

    @Autowired IncentivePolicyRepository incentivePolicyRepository;

    @Nonnull @Override public IncentivePolicy save(@Nonnull @Valid IncentivePolicy incentivePolicy) {
        return incentivePolicyRepository.save(incentivePolicy);
    }
}
