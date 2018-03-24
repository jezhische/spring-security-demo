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

import javax.servlet.ServletContext;

@Controller
public class BeanInfoPageController {

    @Autowired
    private Logger log;

    @Autowired
    private ApplicationContext appContext;
    //    @Autowired
//    private AnnotationConfigWebApplicationContext webContext;
    @Autowired
    private ServletContext servletContext;
//    @Autowired
//    ContextLoaderListener contextLoaderListener;


    @GetMapping("/beans")
    public ModelAndView getBeansDefinition(ModelAndView mav) {
        log.warn("@GetMapping(\"/beans\") return mav");
        String[] beanArray = appContext.getBeanDefinitionNames();
//        mav = new ModelAndView("beans", "beanArray", beanArray);
        mav.setViewName("beans");
        mav.addObject("beanArray", beanArray);
        for (String beanName :
                beanArray) {
            log.info("appContext bean: " + beanName + "; ");
        }
//        WebApplicationContext webContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
//
//        String[] webBeanArray = webContext.getBeanDefinitionNames();
//        for (String webBeanName :
//                webBeanArray) {
//            log.info("webContext bean: " + webBeanName + "; ");
//        }
        return mav;
    }
}
