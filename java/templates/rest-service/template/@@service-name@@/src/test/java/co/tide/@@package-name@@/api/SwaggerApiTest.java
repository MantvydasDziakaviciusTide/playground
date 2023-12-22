package co.tide.@@package-name@@.api;

import org.junit.jupiter.api.Test;
import org.springdoc.core.*;
import org.springdoc.webmvc.core.SpringDocWebMvcConfiguration;
import org.springdoc.webmvc.ui.SwaggerIndexPageTransformer;
import org.springdoc.webmvc.ui.SwaggerWebMvcConfigurer;
import org.springdoc.webmvc.ui.SwaggerWelcomeWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SwaggerWelcomeWebMvc.class)
@ContextConfiguration(classes = {
        SwaggerUiConfigProperties.class,
        SwaggerUiOAuthProperties.class,
        SpringDocWebMvcConfiguration.class,
        SpringDocConfiguration.class,
        SpringDocConfigProperties.class,
        SwaggerWebMvcConfigurer.class,
        SwaggerUiConfigParameters.class,
        SwaggerIndexPageTransformer.class,
        SwaggerWelcomeWebMvc.class
})
class SwaggerApiTest extends ApiTestBase {

    @Test
    void openApiV3JsonDefinition_returns200_withoutAuth() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }

    @Test
    void swaggerUi_returns200_withoutAuth() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }
}
