package com.jezh.springsecurity.config.appConfig;

import com.jezh.springsecurity.config.appConfig.DemoAppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


// Создаем диспетчер-сервлет, и даем ему веб-контекст (контейнер для его сохранения?) - см. доки по new DispatcherServlet(webCtx)
public class WebServletConfiguration implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // На один веб-контекст - один диспетчер-сервлет!
//        Создаем веб-контекст:
        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
//        Указываем, откуда брать конфигурацию веб-контекста (аналог dispatcher-servlet.xml)
//       (здесь vararg, можно много "аннотированных классов" вставить):
        webCtx.register(DemoAppConfig.class);
//        Устанавливаем интерфейс ServletContext для данного веб-контекста, который даст сервлету набор
// методов "to communicate with its servlet container, for example, to get the MIME type of a file, dispatch requests, or write to a log file."
        webCtx.setServletContext(servletContext);
//        Теперь можно воспользоваться интерфейсом ServletContext для того, чтобы создать и установить диспетчер-сервлет
//      new DispatcherServlet(webCtx) с именем "dispatcher" для данного веб-контекста (аналог того же действия в web.xml).
//      Кроме того, метод addServlet() возвращает объект интерфейса ServletRegistration.Dynamic, который можно
//      использовать для ДАЛЬНЕЙШЕЙ конфигурации диспетчер-сервлета:
        ServletRegistration.Dynamic servletConfigurator = servletContext.addServlet("dispatcher", new DispatcherServlet(webCtx));
//        Продолжаем конфигурировать диспетчер-сервлет, используя объект ServletRegistration.Dynamic
        servletConfigurator.setLoadOnStartup(1);
        servletConfigurator.addMapping("/");
    }
}
