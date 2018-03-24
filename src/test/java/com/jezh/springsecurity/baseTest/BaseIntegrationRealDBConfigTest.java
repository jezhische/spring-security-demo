package com.jezh.springsecurity.baseTest;

import com.jezh.springsecurity.config.DemoTestAppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(/*loader = MyContextLoader.class, */classes = DemoTestAppConfig.class)
public abstract class BaseIntegrationRealDBConfigTest {

    private static final Logger log = LogManager.getLogger(com.jezh.springsecurity.util.LoggerSample.class);
//    @Autowired
//    ApplicationContext applicationContext;

    @BeforeClass
    public static void init() throws Exception {
// Just to check that this configuration has successfully attached to test classes and the @BeforeClass is runned normally:
        log.trace("there are drivers enable: ");
        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
        while (driverEnumeration.hasMoreElements()) log.debug(driverEnumeration.nextElement());
// ------------------------------------------------------------------------------------------------

    }
}
class MyContextLoader implements ContextLoader {

//    @Autowired
//    ApplicationContext applicationContext;

    @Override
    public String[] processLocations(Class<?> aClass, String... locations) {
        return locations;
    }

    @Override
    public ApplicationContext loadContext(String... locations) throws Exception {
        ServletContext mockServletContext = new MockServletContext("");
        ServletConfig mockServletConfig = new MockServletConfig(mockServletContext);
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.setServletContext(mockServletContext);
        webApplicationContext.setServletConfig(mockServletConfig);
        webApplicationContext.setConfigLocations(locations);
        webApplicationContext.refresh();
        return webApplicationContext;
    }
}
