package co.tide.@@package-name@@;

import co.tide.v4.spring.exception.ExceptionAdvice;
import co.tide.v4.spring.json.JacksonConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationITest {

    static final PostgreSQLContainer<?> postgreSQLContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres").withTag("11.17-alpine")).waitingFor(
                Wait.forListeningPort()
        );
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void contextLoads(ApplicationContext context) {

        assertThat(context).isNotNull();
        assertThat(context.getBeanNamesForType(ExceptionAdvice.class))
                .contains("customExceptionAdvice", "co.tide.v4.spring.exception.ExceptionAdvice");
        assertThat(context.getBean(JacksonConfig.class)).isNotNull();
    }

}
