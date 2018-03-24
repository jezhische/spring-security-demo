package com.jezh.springsecurity.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerSample {

    @Bean
    public Logger log() {
        return LogManager.getLogger(this.getClass().getName());
    }

//    public static final Logger log = LogManager.getLogger();
}
