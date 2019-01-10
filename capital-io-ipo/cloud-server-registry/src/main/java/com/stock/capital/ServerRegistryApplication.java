package com.stock.capital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * [RegistryServerApplication].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class ServerRegistryApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerRegistryApplication.class, args);
  }
}
