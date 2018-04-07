package com.jezh.springsecurity.config.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
//@ComponentScan("com.jezh.springsecurity")
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

//    без аннотации @ComponentScan("com.jezh.springsecurity") не могу заавтовайрить следующий бин. Хотя, при этом
//    все работает. Но могу написать @Resource вместо @Autowired, тогда IDEA не подчеркивает красным и без @ComponentScan:
//    @Autowired
    @Resource
    private DataSource securityDataSource;

    //configure security of web paths in application, login, logout etc.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .anyRequest().authenticated()
//                .antMatchers("/").hasRole("EMPLOYEE")
                .antMatchers("/register/**").anonymous()
                .antMatchers("/systems/**").hasRole("ADMIN")
                .antMatchers("/leaders/**").hasAnyRole("MANAGER", "ADMIN")
//  todo: NB: security doesn't work without following line, as there's no restriction for any URL
                .antMatchers("/**").hasRole("EMPLOYEE") // "for all authorized users" permission must
                // following after "admin" permission, otherwise I get permission for all users to ALL pages
                .antMatchers("/common/**").permitAll()
                .and()
//                if the invalid login occured, Spring appends an error parameter: "http://localhost:8086/ssd/authentication/login?error"
                .formLogin() // customizing the form login process
                .loginPage("/authentication/login") // return "WEB-INF/securityPgs/login-form"
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
// ----------------------------------------------------------------------------------------set security data source
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(securityDataSource);
//        auth.userDetailsService(userDetailsManager());
    }

// ------------------------------------------------------------------------------- USER REGISTRATION from lectures code
//    see RegistrationController
    @Bean
    public UserDetailsManager userDetailsManager() {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();

        jdbcUserDetailsManager.setDataSource(securityDataSource);

        return jdbcUserDetailsManager;
    }

// todo: see @InitBinder  public void initBinder(WebDataBinder dataBinder) {...} in RegistrationController for explaining
// registration binding

// --------------------------------------------------------------------------------------------- GET USERLIST necessary

// "As I know it allows to get information about lifecycle (create, destroy) of sessions. "
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }

    // БИН httpSessionEventPublisher, необходимый для работы sessionRegistry, ЗАРЕГИСТРИРОВАН В WebServletConfiguration.
    // Но можно просто создать бин, как в закомментированном блоке выше.
    // ("For this class to function correctly in a web application, it is important that you register an
    // HttpSessionEventPublisher in the web.xml file so that this class is notified of sessions that expire.")
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


}
