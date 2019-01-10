package com.askdog.store.web.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@AutoConfigureAfter(JacksonAutoConfiguration.class)
public class ObjectMapperAutoConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        // Jackson entity to NULL or JSON for the space does not participate in serialization
        // Only for the role of VO, Map\List does not work
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
