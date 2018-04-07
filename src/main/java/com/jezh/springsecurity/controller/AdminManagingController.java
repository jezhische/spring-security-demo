package com.jezh.springsecurity.controller;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/systems")
public class AdminManagingController {

    @Autowired
    private UserDetailsManager userDetailsManager;
    @Autowired
    private Logger log;
//    @Autowired
//    private SecurityContextHolder securityContextHolder;

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    @GetMapping("/userlist")
    public ModelAndView getUserList() {
        ModelAndView modelAndView = new ModelAndView();
        List<Object> principals = sessionRegistry.getAllPrincipals();
//      for testing this list access:
        principals.add(new Object());


        modelAndView.setViewName("WEB-INF/adminPgs/userlist");
        modelAndView.addObject("principals", principals);
        return modelAndView;
    }

// --------------------------------------------------------------------------------------------------------- TEST
    @Autowired
    JdbcUserDetailsManager jdbcUserDetailsManager;
    @GetMapping("/testPrincipal")
    public ModelAndView testPrincipal(ModelAndView modelAndView) {
// SecurityContext - это объект, который хранится в ThreadLocal:
        SecurityContext context = SecurityContextHolder.getContext();
// interface, extends java.security.Principal, java.io.Serializable... Represents the token for an authentication request
// or for an authenticated principal once the request has been processed by the AuthenticationManager.authenticate(Authentication)...
        Authentication authentication = context.getAuthentication();

// class org.springframework.security.core.userdetails.User (implements CredentialsContainer, UserDetails):
        Object principal = authentication.getPrincipal();
// class org.springframework.security.web.authentication.WebAuthenticationDetail:
        Object det = authentication.getDetails();
// All the classes, that implement interface UserDetailsService, can use its only method loadUserByUsername(String username), e.g.:
        UserDetails userDetails = jdbcUserDetailsManager.loadUserByUsername("mary"); // и много еще полезных методов в классе!

        String maryname = userDetails.getUsername();
        Collection<? extends GrantedAuthority> maryauthorities = userDetails.getAuthorities();

        String user = principal.toString();
        String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
        String password = principal instanceof UserDetails ? ((UserDetails) principal).getPassword() : principal.toString();
        String details = det != null ? det.toString() : null;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
// When I do the following, I get "Some 500 Internal Server Error has thrown...":
//        String credentials = authentication.getCredentials().toString();

        modelAndView.setViewName("WEB-INF/testPgs/testPrincipal");
        modelAndView
                .addObject("maryname", maryname)
                .addObject("maryauthorities", maryauthorities)
                .addObject("user", user)
                .addObject("username", username)
                .addObject("password", password)
                .addObject("details", details)
                .addObject("authorities", authorities)
//                .addObject("credentials", credentials)
        ;
        return modelAndView;
    }

// --------------------------------------------------------------------------------------------------------- TEST






// from https://stackoverflow.com/questions/29072984/cant-get-active-user-list-from-spring-security-session

//    @Autowired
//    @Qualifier("sessionRegistry")
//    private SessionRegistryImpl sessionRegistry;


//    @RequestMapping(value = "/authenticate", method = {RequestMethod.POST },consumes ="application/json",produces = "application/json")
//    public @ResponseBody
//    LoginResponse authentication(@RequestBody User user, HttpServletRequest request) throws AuthenticationException {
//
//        String userName=user.getUsername();
//        String password=user.getPassword();
//
//        List<Object> principals = sessionRegistry.getAllPrincipals();
//
//        List<User> usersNamesList = new ArrayList<User>();
//
//        for (Object principal: principals) {
//            if (principal instanceof User) {
//                usersNamesList.add((User) principal);
//            }
//        }
//        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
//                userName, password);
//        Authentication authentication = authenticationManager
//                .authenticate(authenticationToken);
//
//
//        SecurityContext securityContext = SecurityContextHolder
//                .getContext();
//
//        securityContext.setAuthentication(authentication);
//
//        HttpSession session = request.getSession(true);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//        LoginResponse response = new LoginResponse("success", session.getId());
//
//        return response;
//    }
}
