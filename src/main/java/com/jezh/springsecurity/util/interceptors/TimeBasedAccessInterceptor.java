package com.jezh.springsecurity.util.interceptors;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

import static java.util.Calendar.HOUR_OF_DAY;

public class TimeBasedAccessInterceptor extends HandlerInterceptorAdapter {
    private int openingTime;
    private int closingTime;

//    Constructor:
    public TimeBasedAccessInterceptor(int openingTime, int closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

//    public void setOpeningTime(int openingTime) {
//        this.openingTime = openingTime;
//    }
//
//    public void setClosingTime(int closingTime) {
//        this.closingTime = closingTime;
//    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(HOUR_OF_DAY);
        if (openingTime <= hour && hour < closingTime) {
            return true;
        }
        response.sendRedirect("https://docs.spring.io/spring/docs/5.0.0.RC3/spring-framework-reference/web.html#mvc-controller"
                /*"http://localhost:8086/ssd/WEB-INF/view/timeBasedAccessInterceptorPage.html"*/);//2-й вариант - не работает! (мешает секьюрити?)
//      Значение false означает, что запрос не идет дальше в DispatcherServlet, а возвращает указанное выше view
        return false;
    }
}
