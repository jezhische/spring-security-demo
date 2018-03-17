package com.jezh.springsecurity.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityAuxController {

    private final static Logger log = LogManager.getLogger();

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        log.warn("@PostMapping(\"/access-denied\") return \"WEB-INF/securityPgs/access-denied\"");
        return "WEB-INF/securityPgs/_403_access-denied"; // handle with commonViewResolver
    }
}
