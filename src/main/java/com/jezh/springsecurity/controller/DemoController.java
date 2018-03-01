package com.jezh.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DemoController {

    @GetMapping("")
    public String home() {
        return "home";
    }

    @GetMapping("_404")
    public String _404() {
        return "_404";
    }

        @GetMapping("test1")
    public String test1() {
        return "test1";
    }
    @GetMapping("test2")
    public String test2() {
        return "test2";
    }
}
