package com.stock.capital.common.web.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * [ObjectMapperAutoConfiguration].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@Configuration
@AutoConfigureAfter(JacksonAutoConfiguration.class)
public class ObjectMapperAutoConfiguration {

  @Autowired private ObjectMapper objectMapper;

  @PostConstruct
  public void init() {
    objectMapper.registerModule(new Jdk8Module());
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
