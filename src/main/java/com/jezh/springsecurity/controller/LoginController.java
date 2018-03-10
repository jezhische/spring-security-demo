package com.jezh.springsecurity.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final static Logger log = LogManager.getLogger();

    @GetMapping("/authentication/login")
    public String showLoginPage() {
        log.warn("@GetMapping(\"authentication/login\") return \"WEB-INF/securityPgs/plain-login.jsp\"");
        return "WEB-INF/securityPgs/plain-login"; // handle with commonViewResolver
    }

    @PostMapping("/authentication/login/process")
    public String authenticate() {
        log.warn("@PostMapping(\"authentication/login/process\") return \"home\"");
        return "home"; // handle with terminalViewResolver
    }

    @GetMapping("/authentication/login?failed")
    public String showLoginFailurePage() {
        log.warn("@GetMapping(\"/authentication/login?failed\") return \"WEB-INF/securityPgs/login-failure\"");
        return "WEB-INF/securityPgs/login-failure"; // handle with commonViewResolver
    }
}
