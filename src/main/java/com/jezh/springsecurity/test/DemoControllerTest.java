package com.jezh.springsecurity.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class DemoControllerTest {
    private final static Logger log = LogManager.getLogger();

// todo: NB: if "redirect:", not "forward:", then html doc cannot bind bootstrap. Use "forward:"!!! (see @GetMapping("testH"))
    @GetMapping("test")
    public String test() {
        log.warn(" @GetMapping(\"test\") return \"redirect:/static/test.html\" " +
                "(real path \"static/html/test/test.html\")");
        return "redirect:/static/test.html"; // handle without any viewResolver: in DemoAppConfig I have
// addResourceHandlers(), therefore all paths starting with "redirect:" or "forward:" will be turn into appropriate
// locations paths. In this case redirect from "/static/test.html" will be turned into "(webapp)/static/html/test/test.html"
// (т.е. вместо "/static/" будет вставлена одна из указанных локаций, в данном случае "/static/html/test/")
    }

    @GetMapping("test1")
    public String test1() {
        log.warn("@GetMapping(\"test1\") return \"static/html/test/test1\"");
        return "static/html/test/test1"; // handle with commonViewResolver
    }
    @GetMapping("test2")
    public String test2() {
        log.warn("@GetMapping(\"test2\")");
        return "forward:static/test2.html"; // handle with commonViewResolver
    }

    @GetMapping("testH")
    public String testHTML() {
        log.warn("@GetMapping(\"testH\") return \"test\"; // handle with testHTMLViewResolver");
        return "forward:static/test.html"; // handle with addResourceHandlers(), see @GetMapping("test")
    }
}
