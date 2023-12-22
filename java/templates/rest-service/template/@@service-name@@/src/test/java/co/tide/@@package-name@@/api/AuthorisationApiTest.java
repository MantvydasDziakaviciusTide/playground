package co.tide.@@package-name@@.api;

import co.tide.@@package-name@@.api.auth.model.AuthDetails;
import co.tide.@@package-name@@.api.auth.tokens.UnauthorisedAuthArgumentsProvider.UnauthorisedSetupForRequireAuthDirector;
import co.tide.@@package-name@@.service.AuthorisationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static co.tide.@@package-name@@.api.auth.tokens.CommonTokens.VALID_DIRECTOR_ACCESS_KEY;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(DummyController.class)
class AuthorisationApiTest extends ApiTestBase {

    @MockBean
    private AuthorisationService authorisationService;

    @Nested
    @DisplayName("GET - /api/v4/demo")
    class TestStaffDemoUserEndpoint {

        private static final String URL = "/api/v4/demo/users/38280";

        private static final String URL_NOT_MATCH = "/api/v4/demo/users/38281";

        private static final String URL_USER_ID_100 = "/api/v4/demo/users/100";

        @ParameterizedTest
        @ArgumentsSource(UnauthorisedSetupForRequireAuthDirector.class)
        @DisplayName("The argumentSource will ensure the endpoint requires director JWT")
        void testUnauthenticated_returns401(AuthDetails authDetails) throws Exception {

            var token = createJwt(authDetails);

            var request = MockMvcRequestBuilders.get(URL)
                    .header("Authorization", token)
                    .accept(MediaTypes.DUMMY_USER);

            validateUnauthorised(request);
        }

        @Test
        @DisplayName("Access should not be granted when userIds do not match")
        void testAccess_whenUserIdsDoNotMatch() throws Exception {

            var token = createJwt(VALID_DIRECTOR_ACCESS_KEY);

            var request = MockMvcRequestBuilders.get(URL_NOT_MATCH)
                    .header("Authorization", token)
                    .accept(MediaTypes.DUMMY_USER);

            when(authorisationService.canRequestAccessUser(any(), anyString()))
                    .thenReturn(false);

            validateUnauthorised(request);
        }

        @Test
        @DisplayName("Access should be granted when userIds match")
        void testAccess_whenUserIdsMatch() throws Exception {

            var token = createJwt(VALID_DIRECTOR_ACCESS_KEY);

            var request = MockMvcRequestBuilders.get(URL)
                    .header("Authorization", token)
                    .accept(MediaTypes.DUMMY_USER);

            when(authorisationService.canRequestAccessUser(any(), anyString()))
                    .thenReturn(true);

            validateSuccess(request, MediaTypes.DUMMY_USER, URL)
                    .andExpect(jsonPath("$.data.info").value("You can access 38280"))
                    .andExpect(jsonPath("$.links.self").value(URL));
        }

        @Test
        @DisplayName("UserId 100 should throw TeapotException")
        void expect_TeapotException_whenUserId_Is_100() throws Exception {

            var token = createJwt(VALID_DIRECTOR_ACCESS_KEY);

            var request = MockMvcRequestBuilders.get(URL_USER_ID_100)
                    .header("Authorization", token)
                    .accept(MediaTypes.DUMMY_USER);

            validateTeapotException(request);
        }
    }
}
