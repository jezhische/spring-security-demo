package com.jezh.springsecurity.config.appConfig;

import com.jezh.springsecurity.config.appConfig.DemoAppConfig;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

// todo: ЭТО И ЕСТЬ СОЗДАНИЕ И КОНФИГУРАЦИЯ ДИСПЕТЧЕР-СЕРВЛЕТА, и при создании он помещается в контейнер webCtx (в веб-контекст):
// (..., new DispatcherServlet(webCtx));  , который конфигурируется с помощью класса DemoAppConfig
// webCtx.register(DemoAppConfig.class); (т.е., в нем размещаются различные бины типа ViewResolver, и
// благодаря тому, что диспетчер-сервлет также размещен в контексте, он получает доступ ко всем этим полезностям).
// В XML-конфигурации вместо DemoAppConfig - файл типа dispatcher-servlet.xml, который становится доступным или по имени
// по умолчанию, или же с помощью new XmlWebApplicationContext().setConfigLocation("/WEB-INF/spring/dispatcher-config.xml")
// или же <context-param><param-name>contextConfigLocation</param-name><param-value>/WEB-INF/dispatcher-servlet.xml</param-value></context-param>
public class WebServletConfiguration implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        // На один веб-контекст - один диспетчер-сервлет!
//        Создаем веб-контекст:
        AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
//        Указываем, откуда брать конфигурацию веб-контекста (аналог dispatcher-servlet.xml)
//       (здесь vararg, можно много "аннотированных классов" вставить):
        webCtx.register(DemoAppConfig.class);
//        Устанавливаем интерфейс ServletContext для данного веб-контекста, который даст сервлету набор
// методов "to communicate with its servlet container, for example, to get the MIME type of a file, dispatch requests, or write to a log file."
        webCtx.setServletContext(container);

// Листенер, нужный для того, чтобы добраться до содержимого сессии - в моем случае, чтобы получить список всех principals
// с помощью бина SessionRegistryImpl (создан в DemoSequrityConfig), который получает информацию о сессии, только если
// зарегистрирован этот листенер. Но можно просто создать бин в конфигурации - см. DemoSequrityConfig
// "Publishes HttpSessionApplicationEvents to the Spring Root WebApplicationContext."
        container.addListener(HttpSessionEventPublisher.class);



//        Теперь можно воспользоваться интерфейсом ServletContext для того, чтобы создать и установить диспетчер-сервлет
//      new DispatcherServlet(webCtx) с именем "dispatcher" для данного веб-контекста (аналог того же действия в web.xml).
//      Кроме того, метод addServlet() возвращает объект интерфейса ServletRegistration.Dynamic, который можно
//      использовать для ДАЛЬНЕЙШЕЙ конфигурации диспетчер-сервлета:
        ServletRegistration.Dynamic servletConfigurator = container.addServlet("dispatcher", new DispatcherServlet(webCtx));
//        Продолжаем конфигурировать диспетчер-сервлет, используя объект ServletRegistration.Dynamic
        servletConfigurator.setLoadOnStartup(1);
        servletConfigurator.addMapping("/");

//        Еще один диспетчер-сервлет:
//        AnnotationConfigWebApplicationContext commonWebCtx = new AnnotationConfigWebApplicationContext();
//        commonWebCtx.register(CommonDemoAppConfig.class);
//        ServletRegistration.Dynamic commonServletConfigurator = container.addServlet("commonDispatcher", new DispatcherServlet(commonWebCtx));
//        commonServletConfigurator.setLoadOnStartup(2);
//        commonServletConfigurator.addMapping("/common/");
    }
}
