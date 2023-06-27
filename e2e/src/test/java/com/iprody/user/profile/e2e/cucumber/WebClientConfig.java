package com.iprody.user.profile.e2e.cucumber;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for create bean RestTemplate to use in generated class.
 */
@Configuration
public class WebClientConfig {

    /**
     * Create bean RestTemplate.
     * @return instance RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
