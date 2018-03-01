package com.jezh.springsecurity.util;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;


//https://blog.frankel.ch/chaining-url-view-resolvers-in-spring-mvc/
public class ChainableUrlBasedViewResolver extends UrlBasedViewResolver {

    public ChainableUrlBasedViewResolver() {
        setViewClass(InternalResourceView.class);
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        // NPE checkup
        String url = getPrefix() + viewName + getSuffix();
        InputStream stream = getServletContext().getResourceAsStream(url);
        if (stream == null)
            return new NonExistentView();
        return super.buildView(viewName);
    }

    private class NonExistentView extends AbstractUrlBasedView {

        @Override
        protected boolean isUrlRequired() {
            return false;
        }

        @Override
        public boolean checkResource(Locale locale) throws Exception {
            return false;
        }

        @Override
        protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest httpServletRequest,
                                               HttpServletResponse httpServletResponse) throws Exception {
            // Purposely empty, it should never get called
        }
    }
}
