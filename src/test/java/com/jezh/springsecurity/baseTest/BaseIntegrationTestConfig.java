package com.jezh.springsecurity.baseTest;

import com.jezh.springsecurity.config.appConfig.DemoAppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
// "to load the context configuration and bootstrap the context that the test will use":
@ContextConfiguration(/*loader = MyContextLoader.class, */classes = {DemoAppConfig.class/*, TestContext.class*/})
// "This annotation ensures that the application context which is loaded for our test is a WebApplicationContext..."
// Иначе получаем  "No qualifying bean of type 'javax.servlet.ServletContext' available"
@WebAppConfiguration(value = "com.jezh.springsecurity.config.appConfig.WebServletConfiguration")
public abstract class BaseIntegrationTestConfig {

    @Autowired
    public Logger log;

    public MockMvc mockMvc;

    public ServletContext servletContext;

    @Autowired
    public WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        servletContext = webApplicationContext.getServletContext();
    }

    @BeforeClass
    public static void init() throws Exception {
// Just to check that this configuration has successfully attached to test classes and the @BeforeClass is runned normally:
//        log.trace("@BeforeClass checks the jdbc drivers enable: ");
//        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
//        while (driverEnumeration.hasMoreElements()) log.debug(driverEnumeration.nextElement());
// ------------------------------------------------------------------------------------------------
    }

// verify that I'm loading the WebApplicationContext object properly, and also that the right servletContext is being attached:
    @Test
    public void verifyWebContextAndServletContext() {
        log.info(">>>test... ---------------------------------------------------------------");
        log.warn(String.format("%n%-147s Verify that the WebApplicationContext object was loading properly. " +
                "%n%-147s Verify that the ServletContext is not null." +
                "%n%-147s Verify that the ServletContext instance is the instance of MockServletContext class." +
                "%n%-147s Verify that the demoController bean is not null." +
                "%n%-147s Verify that the demoAppConfig bean is not null." +
                "%n%-147s Verify that the demoSecurityConfig bean is not null.", "", "", "", "", "", ""));
//        servletContext = webApplicationContext.getServletContext();
        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("demoController"));
        assertNotNull(webApplicationContext.getBean("demoAppConfig"));
        assertNotNull(webApplicationContext.getBean("demoSecurityConfig"));
        log.info(">>>true ---------------------------------------------------------------");
    }
}


//class MyContextLoader implements ContextLoader {
//
////    @Autowired
////    ApplicationContext applicationContext;
//
//    @Override
//    public String[] processLocations(Class<?> aClass, String... locations) {
//        return locations;
//    }
//
//    @Override
//    public ApplicationContext loadContext(String... locations) throws Exception {
//        ServletContext mockServletContext = new MockServletContext("");
//        ServletConfig mockServletConfig = new MockServletConfig(mockServletContext);
//        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
//        webApplicationContext.setServletContext(mockServletContext);
//        webApplicationContext.setServletConfig(mockServletConfig);
//        webApplicationContext.setConfigLocations(locations);
//        webApplicationContext.refresh();
//        return webApplicationContext;
//    }
//}
