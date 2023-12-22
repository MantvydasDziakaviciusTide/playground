package co.tide.@@package-name@@.config;

import co.tide.security.rest.RestAuthenticationConfig;
import co.tide.v4.spring.json.JacksonConfig;
import co.tide.v4.spring.exception.ExceptionAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * General configuration of the application.
 */
@Import({
        JacksonConfig.class,
        ExceptionAdvice.class,
        RestAuthenticationConfig.class,
})
@Configuration
public class AppConfig {

}
