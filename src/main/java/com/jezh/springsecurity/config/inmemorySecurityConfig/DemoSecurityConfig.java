package com.jezh.springsecurity.config.inmemorySecurityConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    //configure security of web paths in application, login, logout etc.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .anyRequest().authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN") // either:   .anyRequest().authenticated()
                .antMatchers("/**").hasRole("USER") // "for all users" permission must following after
                // "admin" permission, otherwise I get permission for all users to ALL pages
                .and()
                .formLogin() // customizing the form login process
                .loginPage("/authentication/login") // return "WEB-INF/securityPgs/plain-login"
//                .failureUrl("/authentication/login?failed")
                .loginProcessingUrl("/authentication/login/process") // return "home"
                .permitAll(true); // allow everyone to see the login page

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add users for in memory authentication
// Default passwords use plain text
        User.UserBuilder users = User.withDefaultPasswordEncoder();

//        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> managerConfigurer = auth.inMemoryAuthentication();
        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER"); // без использования UserBuilder
                .withUser(users.username("john").password("test123").roles("EMPLOYEE", "HUHA", "USER")) // parameter=UserBuilder
                .withUser(users.username("mary").password("test123").roles("MANAGER", "USER"))
                .withUser(users.username("susan").password("test123").roles("ADMIN", "USER"));
    }

}
