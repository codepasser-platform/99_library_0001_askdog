package com.askdog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// TODO: store package scan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}