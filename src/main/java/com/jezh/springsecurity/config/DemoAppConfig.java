package com.jezh.springsecurity.config;

import com.jezh.springsecurity.util.TerminalViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import com.jezh.springsecurity.util.ChainableUrlBasedViewResolver;

import javax.servlet.ServletContext;

@Configuration
@EnableWebMvc
@ComponentScan("com.jezh.springsecurity")
public class DemoAppConfig {

//    http://www.baeldung.com/spring-mvc-view-resolver-tutorial
// "We can simply chain view resolvers by adding more than one resolver to the configuration... we’ll need to define
// an order for these resolvers... The higher the order property (largest order number), the later the view resolver
// is positioned in the chain. ...To define the order: bean.setOrder(0); "
// Spring InternalResourceViewResolver docs: " When chaining ViewResolvers, an InternalResourceViewResolver always needs
// to be last, as it will attempt to resolve any view name, no matter whether the underlying resource actually exists"
//    "This means I cannot configure two InternalResourceViewResolver in my context, or more precisely I can but
//    the first will terminate the lookup process. "
//    todo: hence, "InternalResourceViewResolver should have a higher order "

    @Bean
    public ViewResolver terminalViewResolver() {
        InternalResourceViewResolver viewResolver = new TerminalViewResolver();
        viewResolver.setPrefix("WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setOrder(1);
        return viewResolver;
    }
    // solution: https://blog.frankel.ch/chaining-url-view-resolvers-in-spring-mvc/
    @Bean
    public ViewResolver handmadeViewResolver() {
        UrlBasedViewResolver viewResolver = new ChainableUrlBasedViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setOrder(0);
        return viewResolver;
    }
    // another solution: ResourceBundleViewResolver or BeanNameViewResolver or XmlViewResolver (the default is "/WEB-INF/views.xml")
    // https://www.concretepage.com/spring/spring-mvc/spring-mvc-resourcebundleviewresolver-example-with-java-config
    // http://www.baeldung.com/spring-mvc-view-resolver-tutorial

}
