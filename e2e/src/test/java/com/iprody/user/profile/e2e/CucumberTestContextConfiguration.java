package com.iprody.user.profile.e2e;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Class for configuration TestContext.
 */
@CucumberContextConfiguration
@SpringBootTest(properties = { "cucumber.junit-platform.naming-strategy=long" })
public class CucumberTestContextConfiguration {

}
