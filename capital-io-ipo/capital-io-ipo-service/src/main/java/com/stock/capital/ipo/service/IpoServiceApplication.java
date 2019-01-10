package com.stock.capital.ipo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * [ServiceApplication].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.stock.capital.common", "com.stock.capital.ipo"})
@EntityScan(basePackages = {"com.stock.capital.ipo.model.entity"})
@EnableJpaRepositories(basePackages = {"com.stock.capital.ipo.dao.repository"})
@EnableMongoRepositories(basePackages = {"com.stock.capital.ipo.dao.repository"})
public class IpoServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(IpoServiceApplication.class, args);
  }
}
