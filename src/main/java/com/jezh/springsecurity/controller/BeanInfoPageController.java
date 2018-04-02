package com.jezh.springsecurity.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Controller
public class BeanInfoPageController {

    @Autowired
    private Logger log;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private WebApplicationContext webContext;
//    NB: another way - within the method, that takes in parameter HttpServletRequest request:
//    webContext = RequestContextUtils.findWebApplicationContext(request);

    @Autowired
    private ServletContext servletContext;


    @GetMapping("/beans")
    public ModelAndView getBeansDefinition(ModelAndView mav, HttpServletRequest request) {
        log.warn("@GetMapping(\"/beans\") return mav");

//        webContext = RequestContextUtils.findWebApplicationContext(request);

        String[] beanArray = appContext.getBeanDefinitionNames();
//        mav = new ModelAndView("beans", "beanArray", beanArray);
        mav.setViewName("beans");

        log.warn("ApplicationContext beans list: -----------------------");

        mav.addObject("beanArray", beanArray);
        for (String beanName :
                beanArray) {
            log.info("appContext bean: " + beanName + "; ");
                    mav.addObject("beanArray", beanArray);
        }

        log.warn("WebApplicationContext beans list: -----------------------");

            String[] webBeanArray = webContext.getBeanDefinitionNames();
            mav.addObject("webBeanArray", webBeanArray);
            for (String webBeanName :
                    webBeanArray) {
            log.info("WebApplicationContext bean: " + webBeanName + "; ");

        }
        return mav;
    }
}
