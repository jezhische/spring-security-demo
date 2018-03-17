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
//                .antMatchers("/").hasRole("EMPLOYEE")
                .antMatchers("/systems/**").hasRole("ADMIN")
                .antMatchers("/leaders/**").hasAnyRole("MANAGER", "ADMIN")
//  todo: NB: security doesn't work without following line, as there's no restriction for any URL
                .antMatchers("/**").hasRole("EMPLOYEE") // "for all authorized users" permission must
                // following after "admin" permission, otherwise I get permission for all users to ALL pages
                .antMatchers("/commons/**").permitAll()
                .and()
//                if the invalid login occured, Spring appends an error parameter: "http://localhost:8086/ssd/authentication/login?error"
                .formLogin() // customizing the form login process
                .loginPage("/authentication/login") // return "WEB-INF/securityPgs/plain-login"
//                .failureUrl("/authentication/login?failed")
                .loginProcessingUrl("/authentication/login/process") // return "home"
                .permitAll(true) // allow everyone to see the login page
                .and()
                .logout() // so that Spring to append a logout parameter: "http://localhost:8086/ssd/authentication/login?logout"
                .permitAll()
                .and()
                .exceptionHandling()
//              NB: errorPage must begin with '/', either I get IllegalArgumentException
                .accessDeniedPage("/access-denied");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add users for in memory authentication
// Default passwords use plain text
        User.UserBuilder users = User.withDefaultPasswordEncoder();

//        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> managerConfigurer = auth.inMemoryAuthentication();
        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER"); // без использования UserBuilder
                .withUser(users.username("john").password("test123").roles("EMPLOYEE", "HUHA")) // parameter=UserBuilder
                .withUser(users.username("mary").password("test123").roles("MANAGER", "EMPLOYEE"))
                .withUser(users.username("susan").password("test123").roles("ADMIN", "EMPLOYEE"));
    }

}
