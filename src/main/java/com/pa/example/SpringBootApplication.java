package com.pa.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {
    private static Logger LOG = LoggerFactory
            .getLogger(SpringBootApplication.class);

    public static void main(String []args){
        SpringApplication.run(SpringBootApplication.class,args);
    }
}
