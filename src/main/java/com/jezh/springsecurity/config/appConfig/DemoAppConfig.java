package com.jezh.springsecurity.config.appConfig;

import com.jezh.springsecurity.util.SmartInternalResourceViewResolver;
import com.jezh.springsecurity.util.TerminalViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import com.jezh.springsecurity.util.ChainableUrlBasedViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.ServletContext;

@Configuration
@EnableWebMvc
@ComponentScan("com.jezh.springsecurity")
// https://dzone.com/articles/spring-mvc-and-java-based-configuration-1: пробуем implement WebMvcConfigurer:
// "Defines callback methods to customize the Java-based configuration for Spring MVC enabled via @EnableWebMvc".
// todo: NB: extends WebMvcConfigurerAdapter - is deprecated in Spring 5, use implement WebMvcConfigurer instead of it!
public class DemoAppConfig implements WebMvcConfigurer {
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
        viewResolver.setOrder(3);
        return viewResolver;
    }

//    @Bean
//    public ViewResolver smartViewResolver() {
//        InternalResourceViewResolver viewResolver = new SmartInternalResourceViewResolver();
//        viewResolver.setPrefix("/static/html/test/");
//        viewResolver.setSuffix(".html");
//        viewResolver.setOrder(3);
//        return viewResolver;
//    }

    // solution: https://blog.frankel.ch/chaining-url-view-resolvers-in-spring-mvc/
    @Bean
    public ViewResolver commonViewResolver() {
        UrlBasedViewResolver viewResolver = new ChainableUrlBasedViewResolver();
        viewResolver.setPrefix("/");
//        viewResolver.setSuffix(".html");
        viewResolver.setOrder(2);
        return viewResolver;
    }

//    @Bean
//    public FreeMarkerViewResolver HTMLViewResolver() {
//        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
//        viewResolver.setPrefix("WEB-INF/static/html/");
//        viewResolver.setSuffix(".html");
//        viewResolver.setCache(true);
//        viewResolver.setExposeSpringMacroHelpers(true);
//        viewResolver.setExposeRequestAttributes(true);
//        viewResolver.setAllowRequestOverride(false);
//        viewResolver.setExposeSessionAttributes(true);
//        viewResolver.setAllowSessionOverride(false);
//        viewResolver.setExposePathVariables(true);
//        viewResolver.setRequestContextAttribute("rc");
//        return viewResolver;
//    }

//    @Bean
//    public ViewResolver testJSPViewResolver() {
//        UrlBasedViewResolver viewResolver = new ChainableUrlBasedViewResolver();
//        viewResolver.setPrefix("/WEB-INF/test/");
//        viewResolver.setSuffix(".jsp");
//        viewResolver.setOrder(2);
//        return viewResolver;
//    }
//
//    @Bean
//    public ViewResolver testHTMLViewResolver() {
//        UrlBasedViewResolver viewResolver = new ChainableUrlBasedViewResolver();
//        viewResolver.setPrefix("/static/");
//        viewResolver.setSuffix(".html");
//        viewResolver.setOrder(2);
//        return viewResolver;
//    }

    // another solution: ResourceBundleViewResolver or BeanNameViewResolver or XmlViewResolver (the default is "/WEB-INF/views.xml")
    // https://www.concretepage.com/spring/spring-mvc/spring-mvc-resourcebundleviewresolver-example-with-java-config
    // http://www.baeldung.com/spring-mvc-view-resolver-tutorial

//    default method from WebMvcConfigurer:
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
// <!--todo: маппинг "/static/" - это начало ОТНОСИТЕЛЬНОГО пути ко всем локациям ресурсов в запросе; а локация=значение
//  этой мапы /static/ (пара "name=value"). А ** означают, видимо, что можно идти и в поддиректории:
//        "...See AntPathMatcher for more details on the syntax."
//  Т.е., здесь у "/static/" указано, например, значение "/static/html/".
//  Т.о., если есть файл /static/html/logo.html или /static/html/test/logo.html, то запрос к нему будет: /static/logo.html-->
        registry.addResourceHandler("/static/**").addResourceLocations("/static/html/", "/static/html/test/",
                "classpath:static/", "static/bootstrap3.3.7/css/", "static/bootstrap3.3.7/fonts/",
                "static/bootstrap3.3.7/js/");
    }

}
