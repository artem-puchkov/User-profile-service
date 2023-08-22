package com.iprody.user.profile.e2e.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Class for configuration TestContext.
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
            "cucumber.junit-platform.naming-strategy=long",
            "javax.net.ssl.keyStoreType=pkcs12",
            "javax.net.ssl.keyStore=${client-config.trust.store.file}",
            "javax.net.ssl.keyStorePassword=${client-config.trust.store.password}"
})
public class CucumberTestContextConfiguration {

}
