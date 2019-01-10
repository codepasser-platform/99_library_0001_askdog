package com.stock.capital.ipo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * [WebApplication].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.stock.capital.ipo.service"})
@ComponentScan(basePackages = {"com.stock.capital.common", "com.stock.capital.ipo"})
public class IpoConsoleApplication {

  public static void main(String[] args) {
    SpringApplication.run(IpoConsoleApplication.class, args);
  }
}
