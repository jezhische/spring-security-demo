package com.jezh.springsecurity.controller;

import com.jezh.springsecurity.user.CrmUser;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserDetailsManager userDetailsManager;
    @Autowired
    private Logger log;
    @Autowired
    MessageSource messageSource;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

// "Annotation that identifies methods which initialize the WebDataBinder... Such init-binder methods support
// all arguments that RequestMapping supports..."
// WebDataBinder - "Special DataBinder for data binding from web request parameters to JavaBean objects"
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
// ------------------------------------------------------------------------------------------------------------- get
    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model model) {
        // CRM = Customer Relationship Management
// Добавляем в модель сущность, которой отдадим на хранение пароль и юзернэйм из формы - фактически, сущность является
// просто Map<String, String>, хранящей два значения, но для удобства обращения(?) создана как джавакласс.
        model.addAttribute("crmUser", new CrmUser());
        return "WEB-INF/securityPgs/registration-form";
    }

// ------------------------------------------------------------------------------------------------------------- post

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("crmUser") CrmUser crmUser,
            BindingResult bindingResult,
            Model model) {

        String userName = crmUser.getUserName();

        log.info("Processing registration form for: " + userName);

        // form validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("crmUser", new CrmUser());
            model.addAttribute("registrationError", "User name/password can not be empty.");
            log.warn("User name/password can not be empty.");
            return "WEB-INF/securityPgs/registration-form";
        }

        // check the database if user already exists
        boolean userExists = doesUserExist(userName);

        if (userExists) {
            model.addAttribute("crmUser", new CrmUser());
            model.addAttribute("registrationError", "User name already exists.");

            log.warn("User name already exists.");

            return "WEB-INF/securityPgs/registration-form";
        }


        // encrypt the password
        String encodedPassword = passwordEncoder.encode(crmUser.getPassword());

        // prepend the encoding algorithm id
        encodedPassword = "{bcrypt}" + encodedPassword;

        // give user default role of "employee"
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_EMPLOYEE");

        // create user object (from Spring Security framework)
        User tempUser = new User(userName, encodedPassword, authorities);

        // save user in the database
        userDetailsManager.createUser(tempUser);

        log.info("Successfully created user: " + userName);

        return "WEB-INF/securityPgs/registration-confirmation";
    }

// ------------------------------------------------------------------------------------------------------------- util
    private boolean doesUserExist(String userName) {
        log.info("Checking if user exists: " + userName);
        // check the database if the user already exists
        // userDetailsManager - bean was created in DemoSequrityConfig as new JdbcUserDetailsManager()
        boolean exists = userDetailsManager.userExists(userName);
        log.info("User: " + userName + ", exists: " + exists);
        return exists;
    }
}
