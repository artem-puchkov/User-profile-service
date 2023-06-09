package com.iprody.user.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class that is used to bootstrap and launch spring application.
 */
@SpringBootApplication
public class UserProfileServiceApplication {
    /**
     * @param args application arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(UserProfileServiceApplication.class, args);
    }
}
