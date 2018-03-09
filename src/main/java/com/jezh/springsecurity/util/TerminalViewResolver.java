package com.jezh.springsecurity.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.InputStream;
import java.util.Objects;

public class TerminalViewResolver extends InternalResourceViewResolver {
    @NotNull
    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        String url = getPrefix() + viewName + getSuffix();
        InputStream stream = getServletContext().getResourceAsStream(url);
////        Для случая возникновения NullPointerException - оборачиваем в метод requireNonNull() из Objects:
//        InputStream stream = Objects.requireNonNull(getServletContext()).getResourceAsStream(url);

// Смысл в том, что если в контроллере есть такой метод, но ресурс отсутствует, кинет страницу _404.jsp. Но работает
// только для простых запросов типа "greet", для более сложных типа "greet/name" возвращает не страницу _404, а ее url
// в виде "message /ssd/greet/WEB-INF/view/_404.jsp"
        if (stream == null)
            return super.buildView("_404");
//            return super.buildView("redirect:/static/_404.html");
        return super.buildView(viewName);
    }
}
