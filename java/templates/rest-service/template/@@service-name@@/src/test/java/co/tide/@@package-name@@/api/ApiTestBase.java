package co.tide.@@package-name@@.api;

import co.tide.@@package-name@@.api.auth.model.AuthDetails;
import co.tide.v4.rest.response.ApiError;
import co.tide.v4.spring.exception.ExceptionAdvice;
import co.tide.v4.spring.json.JacksonConfig;
import co.tide.security.auth.jwt.AuthenticationMetadata;
import co.tide.security.auth.jwt.TideAuthenticationJwtDecoder;
import co.tide.security.auth.jwt.TideAuthenticationJwtEncoder;
import co.tide.security.rest.JwtAuthenticationFilter;
import co.tide.security.rest.RestAuthenticationConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static co.tide.@@package-name@@.api.MediaTypes.DUMMY_USER;
import static co.tide.@@package-name@@.api.auth.ErrorCodeMatcher.errorCodesMatch;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>The API Test base provides helper methods and components to make it easy to test an API.</p>
 *
 * <p>API tests should instantiate an instance of the service, and use mockMvc to hit the various endpoints.
 * These endpoints should be limited to the controller layer, so the actual service implementation should be
 * mocked.</p>
 */
@Import({
        RestAuthenticationConfig.class,
        JacksonConfig.class,
        ExceptionAdvice.class,
})
@ActiveProfiles("test")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ImportAutoConfiguration(PropertySourcesPlaceholderConfigurer.class)
public abstract class ApiTestBase {

    protected static final String HTTP_AUTHORIZATION_HEADER = "Authorization";
    protected static final String HEADER_IDEMPOTENCY_KEY = "Idempotency-Key";

    private static final String ERROR_PATH = "$.errors";
    private static final String DATA_PATH = "$.data";
    private static final String LINKS_PATH = "$.links";
    private static final String META_PATH = "$.meta";

    @Autowired
    private TideAuthenticationJwtDecoder decoder;

    @Autowired
    private TideAuthenticationJwtEncoder encoder;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RequestMatcher requestMatcher;

    protected MockMvc mockMvc;

    @BeforeEach
    protected void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new JwtAuthenticationFilter(decoder, requestMatcher))
                .build();
    }

    /**
     * <p>Validates that an internal server error (500) can be thrown according to the API V4 Spec.</p>
     *
     * <p>Please note that this method does not have an overloaded version that allows additional
     * error parameters. As this is a 500 error, any error codes we return would expose system
     * implementation details.</p>
     *  @param request             The http request to be performed
     */
    public ResultActions validateInternalServerError(MockHttpServletRequestBuilder request) throws Exception {

        return mockMvc.perform(request)
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(ERROR_PATH).exists())
                .andExpect(jsonPath(ERROR_PATH).isEmpty())
                .andExpect(jsonPath(DATA_PATH).doesNotHaveJsonPath())
                .andExpect(jsonPath(LINKS_PATH).doesNotHaveJsonPath())
                .andExpect(jsonPath(META_PATH).doesNotHaveJsonPath());
    }

    /**
     * <p>Validates that "I'm a teapot exception" (418) can be thrown</p>
     *
     * <p>When userId is 100 this exception is thrown</p>
     *
     * @param request The http request to be performed
     */
    public ResultActions validateTeapotException(MockHttpServletRequestBuilder request) throws Exception {

        return mockMvc.perform(request)
                .andExpect(status().isIAmATeapot())
                .andExpect(content().contentType(DUMMY_USER))
                .andExpect(jsonPath(ERROR_PATH).exists())
                .andExpect(jsonPath(ERROR_PATH).isEmpty())
                .andExpect(jsonPath(DATA_PATH).doesNotHaveJsonPath())
                .andExpect(jsonPath(LINKS_PATH).doesNotHaveJsonPath())
                .andExpect(jsonPath(META_PATH).doesNotHaveJsonPath());
    }

    /**
     * <p>Validates that a forbidden error (403) can be thrown according to the API V4 Spec.</p>
     *
     * <p>Please note that this method does not have an overloaded version that allows additional
     * error parameters. As this is a 403 error, any error codes we return would expose system
     * implementation details (e.g. whether a resource exists or belongs to someone else).</p>
     *  @param request             The http request to be performed
     *
     */
    public ResultActions validateForbidden(MockHttpServletRequestBuilder request) throws Exception {

        return mockMvc.perform(request)
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(ERROR_PATH).exists())
                .andExpect(jsonPath(ERROR_PATH).isEmpty())
                .andExpect(jsonPath(DATA_PATH).doesNotHaveJsonPath())
                .andExpect(jsonPath(LINKS_PATH).doesNotHaveJsonPath())
                .andExpect(jsonPath(META_PATH).doesNotHaveJsonPath());
    }

    /**
     * <p>Validates that an Unauthorized (401) can be thrown according to the API V4 Spec.</p>
     *
     * <p>Please note that this method does not have an overloaded version that allows additional
     * error parameters. As this is a 401 error, any error codes we return would expose system
     * implementation details.</p>
     *  @param request             The http request to be performed
     */
    public ResultActions validateUnauthorised(MockHttpServletRequestBuilder request) throws Exception {

        return mockMvc.perform(request)
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(ERROR_PATH).exists())
                .andExpect(jsonPath(ERROR_PATH).isEmpty())
                .andExpect(jsonPath(DATA_PATH).doesNotHaveJsonPath())
                .andExpect(jsonPath(LINKS_PATH).doesNotHaveJsonPath())
                .andExpect(jsonPath(META_PATH).doesNotHaveJsonPath());
    }

    /**
     * <p>Validates that a BadRequest (400) can be thrown according to the API V4 Spec.</p>
     *
     * <p>This version expects a 400 error without any specific error codes.</p>
     *  @param request             The http request to be performed
     *
     */
    public ResultActions validateBadRequest(MockHttpServletRequestBuilder request) throws Exception {

        return validateBadRequest(request, Collections.emptyList());
    }

    /**
     * <p>Validates that a BadRequest (400) can be thrown according to the API V4 Spec.</p>
     *
     * <p>Validates that the explicit error codes returned by the API match the expected errors.  </p>
     *  @param request             The http request to be performed
     * @param expectedErrorCodes  A list of any expected error codes, and the error path (if any) to the field with the
     */
    public ResultActions validateBadRequest(MockHttpServletRequestBuilder request,
            List<ApiError> expectedErrorCodes) throws Exception {

        return mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(ERROR_PATH).exists())
                .andExpect(jsonPath(ERROR_PATH + ".*", hasSize(expectedErrorCodes.size())))
                .andExpect(jsonPath(ERROR_PATH + ".*").value(errorCodesMatch(expectedErrorCodes)))
                .andExpect(jsonPath(DATA_PATH).doesNotHaveJsonPath())
                .andExpect(jsonPath(LINKS_PATH).doesNotHaveJsonPath())
                .andExpect(jsonPath(META_PATH).doesNotHaveJsonPath());
    }


    /**
     * <p>Validates that a successful response (200) can be thrown according to the API V4 Spec.</p>
     *
     * <p>This checks the basic contents required in all 200 responses, and returns the ResultActions allowing
     * further checks.</p>
     *
     * @param request             The http request to be performed
     * @param responseContentType The content type the response is meant to be in
     * @param selfLink            The URL for the self link that should be included in the response
     * @return The ResultActions of the request, allowing further test checks
     */
    public ResultActions validateSuccess(MockHttpServletRequestBuilder request,
            String responseContentType,
            String selfLink) throws Exception {

        return mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(responseContentType))
                .andExpect(jsonPath(DATA_PATH).exists())
                .andExpect(jsonPath(LINKS_PATH + ".self").value(selfLink))
                .andExpect(jsonPath(LINKS_PATH + ".self").value(containsString("api")))
                .andExpect(jsonPath(META_PATH).exists())
                .andExpect(jsonPath(ERROR_PATH).doesNotHaveJsonPath());
    }

    /**
     * Creates a JWT token that can be used to hit the service. The JWT is defined by the credentials in the
     * authDetails object.
     *
     * @param authDetails An object describing the credentials the token should be for.
     * @param extraPermissions  An optional series of permissions that should be added to those in the auth
     *                      details. Used to combine standard permissions/roles (e.g. those for a web token),
     *                      with member specific permissions/roles (e.g. ACCOUNTANT) or action specific
     *                      permissions/roles (e.g. MAKE_PAYMENT)
     * @return A valid JWT token
     */
    protected String createJwt(AuthDetails authDetails, String... extraPermissions) {

        var combinedPermissions = new ArrayList<String>();
        combinedPermissions.addAll(authDetails.getPermissions());
        combinedPermissions.addAll(List.of(extraPermissions));

        var metadata = AuthenticationMetadata.newBuilder()
                .withRequestId(UUID.randomUUID().toString())
                .withUserId(authDetails.getUserId())
                .withCompanyId(authDetails.getCompanyId())
                .withUserIdentifier(authDetails.getUserIdentifier())
                .withStaffUserId(authDetails.getStaffUserId())
                .withCredentialId(UUID.randomUUID().toString())
                .withAuthorities(combinedPermissions)
                .build();

        return encoder.generateAuthenticationHeader(metadata);
    }
}