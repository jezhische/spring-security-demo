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
//        System.out.println("@GetMapping(\"\") return \"home\"");
        return "home"; // handle with terminalViewResolver
    }

    @GetMapping("_404")
    public String _404() {
        log.warn("@GetMapping(\"_404\") return \"_404\"");
        return "_404"; // handle with terminalViewResolver
    }

    @GetMapping("authentication/login")
    public String showLoginPage() {
        log.warn("@GetMapping(\"authentication/login\") return \"WEB-INF/securityPgs/plain-login.jsp\"");
        return "WEB-INF/securityPgs/plain-login.jsp"; // handle with commonViewResolver
    }

    @PostMapping("authentication/login/process")
    public String authenticate() {
        log.warn("@PostMapping(\"authentication/login/process\") return \"home\"");
        return "home"; // handle with terminalViewResolver
    }

    @GetMapping("test")
    public String test() {
        log.warn(" @GetMapping(\"test\") return \"redirect:/static/test.html\" " +
                "(real path \"static/html/test/test.html\")");
        return "redirect:/static/test.html"; // handle without any viewResolver: as in DemoAppConfig I have
// addResourceHandlers(), therefore all paths starting with "/static/" will be turn into appropriate locations paths.
// In this case redirect to "/static/test.html" will be turned into "(webapp)/static/html/test/test.html"
//        (т.е. вместо "/static/" будет вставлена одна из указанных локаций, в данном случае "/static/html/test/")
    }

        @GetMapping("test1")
    public String test1() {
        log.warn("@GetMapping(\"test1\") return \"test1\"");
        return "test1"; // handle with testJSPViewResolver
    }
    @GetMapping("test2")
    public String test2() {
        log.warn("@GetMapping(\"test2\") return \"/static/html/test/test2.jsp\"");
        return "/static/html/test/test2.jsp"; // handle with commonViewResolver
    }

    @GetMapping("testH")
    public String testHTML() {
        log.warn("@GetMapping(\"testH\") return \"test\"; // handle with testHTMLViewResolver");
        return "forward:static/test.html"; // handle with testHTMLViewResolver
    }
}
