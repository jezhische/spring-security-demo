package com.jezh.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

        @GetMapping("/test1")
    public String test1() {
        return "test1";
    }
}
