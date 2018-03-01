package com.jezh.springsecurity.config.inmemorySecurityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add users for in memory authentication
// Default passwords use plain text
        User.UserBuilder users = User.withDefaultPasswordEncoder();

//        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> managerConfigurer = auth.inMemoryAuthentication();
        auth.inMemoryAuthentication()
                .withUser(users.username("john").password("test123").roles("EMPLOYEE", "HUHA"))
                .withUser(users.username("mary").password("test123").roles("MANAGER"))
                .withUser(users.username("susan").password("test123").roles("ADMIN"));
    }
}
