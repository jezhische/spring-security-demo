package com.jezh.springsecurity.config.appConfig.testConfig;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class TestContext {
    @Bean
    public MessageSource messageSource() {
//        to try ReloadableResourceBundleMessageSource instead!
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }
//    @Bean
//    public TodoService todoService() {
//        return Mockito.mock(TodoService.class);
//    }
}
