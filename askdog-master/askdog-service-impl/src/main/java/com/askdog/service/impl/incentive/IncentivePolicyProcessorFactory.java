package com.askdog.service.impl.incentive;

import com.askdog.model.entity.inner.IncentiveType;
import com.askdog.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class IncentivePolicyProcessorFactory {

    @Autowired
    private UserRepository userRepository;

    public IncentivePolicyProcessor get(IncentiveType incentiveType) {
        return incentiveType.equals(IncentiveType.EXP) ? expIncentivePolicyProcessor() : pointIncentivePolicyProcessor();
    }

    @Bean
    public IncentivePolicyProcessor expIncentivePolicyProcessor() {
        return (user, val) -> userRepository.updateExp(user.getUuid(), val);
    }

    @Bean
    public IncentivePolicyProcessor pointIncentivePolicyProcessor() {
        return (user, val) -> userRepository.updatePoints(user.getUuid(), val);
    }

}
