package uk.doh.oht.rina.registration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * Created by peterwhitehead on 28/04/2017.
 */

@SpringBootApplication
@ComponentScan(basePackages = { "uk.doh.oht" })
@EnableOAuth2Client
@Slf4j
public class OHTRinaRegistrationApplication {
    public static void main(final String... args) throws Exception {
        log.info("Starting oht-rina-registration");
        SpringApplication.run(OHTRinaRegistrationApplication.class, args);
    }
}
