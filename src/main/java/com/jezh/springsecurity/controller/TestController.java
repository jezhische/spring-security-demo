package com.jezh.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/common")
public class TestController {

    @GetMapping("/testPathVar/{name}")
    public ModelAndView getTestPathVar(@PathVariable String name, ModelAndView modelAndView) {
        modelAndView.setViewName("WEB-INF/testPgs/testPathWar");
        modelAndView.addObject("message", "Hi, " + name);
        return modelAndView;
    }

//    @GetMapping("/restTestPathVar/{name}")
//    @ResponseBody
//    public String getRestTestPathVar(@PathVariable String name, Model model) {
////        modelAndView.addObject("restMsg", "Hey, you, " + name);
//        model.addAttribute("restMsg", "Hey, you, " + name);
//        return "Hey, you, " + name;
//    }
}
