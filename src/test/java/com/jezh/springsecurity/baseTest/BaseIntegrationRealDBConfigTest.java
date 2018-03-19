package com.jezh.springsecurity.baseTest;

import com.jezh.springsecurity.config.DemoTestAppConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DemoTestAppConfig.class)
public abstract class BaseIntegrationRealDBConfigTest {

}
