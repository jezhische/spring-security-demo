package com.jezh.springsecurity.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DemoController {

    private final static Logger log = LogManager.getLogger();
    @GetMapping("")
    public String home() {
        log.warn("@GetMapping(\"\") return \"home\"");
        return "home"; // handle with terminalViewResolver
    }

    @GetMapping("_404")
    public String _404() {
        log.warn("@GetMapping(\"_404\") return \"_404\"");
        return "_404"; // handle with terminalViewResolver
    }
}
