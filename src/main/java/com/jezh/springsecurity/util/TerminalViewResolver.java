package com.jezh.springsecurity.util;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.InputStream;

public class TerminalViewResolver extends InternalResourceViewResolver {
    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        String url = getPrefix() + viewName + getSuffix();
        InputStream stream = getServletContext().getResourceAsStream(url);
        if (stream == null)
            return super.buildView("_404");
        return super.buildView(viewName);
    }
}
