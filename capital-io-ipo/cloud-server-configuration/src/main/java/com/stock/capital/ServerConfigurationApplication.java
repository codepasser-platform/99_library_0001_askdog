package com.stock.capital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * [ConfigurationServerApplication].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@SpringBootApplication
@EnableConfigServer
public class ServerConfigurationApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServerConfigurationApplication.class, args);
  }
}
