package com.jezh.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// в DemoSecurityConfig у меня помечено: .antMatchers("/common/**").permitAll()
// Но это всего лишь дает возможность зайти на страничку юзеру с любой ролью, но не отменяет залогинивание
@RequestMapping("/common")
public class CommonController {

    @GetMapping("")
    public String getCommonPage() {
        return "WEB-INF/testPgs/commonPage";
    }
}
