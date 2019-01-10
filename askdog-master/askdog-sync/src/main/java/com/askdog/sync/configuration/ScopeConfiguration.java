package com.askdog.sync.configuration;

import org.springframework.batch.core.scope.JobScope;
import org.springframework.batch.core.scope.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScopeConfiguration {

	private StepScope stepScope = new StepScope();

	private JobScope jobScope = new JobScope();

	@Bean
	public StepScope stepScope() {
		stepScope.setAutoProxy(false);
		return stepScope;
	}

	@Bean
	public JobScope jobScope() {
		jobScope.setAutoProxy(false);
		return jobScope;
	}
}