package com.jezh.springsecurity.config.appConfig;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{DemoAppConfig.class};
    }

    @NotNull
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
