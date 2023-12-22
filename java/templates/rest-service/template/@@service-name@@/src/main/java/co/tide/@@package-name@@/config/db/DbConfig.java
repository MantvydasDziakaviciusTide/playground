package co.tide.@@package-name@@.config.db;

import co.tide.logging.ExceptionLoggable;
import javax.sql.DataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

/**
 * General configuration for the database.
 */
@Configuration
@EnableConfigurationProperties({LiquibaseProperties.class})
public class DbConfig implements ExceptionLoggable {
    private boolean migrationsHaveRun = false;

    @Bean
    public SpringLiquibase liquibase(
            DataSource dataSource,
            LiquibaseProperties liquibaseProperties,
            Environment springEnvironment) {

        boolean skipMigrations = springEnvironment.acceptsProfiles(Profiles.of("fast"));
        boolean runMigrations = !skipMigrations && !migrationsHaveRun && liquibaseProperties.isEnabled();
        SpringLiquibase liquibase = new SpringLiquibase();

        if (runMigrations) {
            liquibase.setShouldRun(true);
            liquibase.setDataSource(dataSource);
            liquibase.setChangeLog("classpath:liquibase/master.xml");
            liquibase.setContexts(liquibaseProperties.getContexts());
            liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
            liquibase.setDropFirst(liquibaseProperties.isDropFirst());

            getLogger().info("Migrations will run.");
        } else {
            liquibase.setShouldRun(false);
            getLogger().info("Migrations will not run.");
        }

        migrationsHaveRun = true;

        return liquibase;
    }
}