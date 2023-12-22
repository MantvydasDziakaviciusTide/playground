package co.tide.@@package-name@@.api;

import co.tide.@@package-name@@.api.auth.model.AuthDetails;
import co.tide.@@package-name@@.api.auth.tokens.UnauthorisedAuthArgumentsProvider.UnauthorisedSetupForRequireAuthStaffUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static co.tide.@@package-name@@.api.auth.tokens.CommonTokens.VALID_STAFF_USER;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DummyStaffController.class)
class StaffAuthorisationApiTest extends ApiTestBase {

    @Nested
    @DisplayName("GET - /agent-api/v4/staff-demo/users")
    class TestStaffDemoUserEndpoint {

        private static final String URL = "/agent-api/v4/staff-demo/users/192920";

        @ParameterizedTest
        @ArgumentsSource(UnauthorisedSetupForRequireAuthStaffUser.class)
        @DisplayName("The argumentSource will ensure the endpoint requires staff JWT")
        void preventsAccess_nonStaffJwt(AuthDetails authDetails) throws Exception {

            var token = createJwt(authDetails);

            var request = MockMvcRequestBuilders.get(URL)
                    .header("Authorization", token)
                    .accept(MediaTypes.DUMMY_USER);

            validateUnauthorised(request);
        }

        @Test
        @DisplayName("Access should be granted with staff JWT")
        void allowsAccess_withStaffJwt() throws Exception {

            var token = createJwt(VALID_STAFF_USER);

            var request = MockMvcRequestBuilders.get(URL)
                    .header("Authorization", token)
                    .accept(MediaTypes.DUMMY_USER);

            validateSuccess(request, MediaTypes.DUMMY_USER, URL)
                    .andExpect(jsonPath("$.data.info").value("You're staff, so of course you can access 192920"));
        }
    }
}
