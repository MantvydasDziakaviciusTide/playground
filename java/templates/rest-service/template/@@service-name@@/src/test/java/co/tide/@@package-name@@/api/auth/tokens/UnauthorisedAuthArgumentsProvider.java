package co.tide.@@package-name@@.api.auth.tokens;

import co.tide.@@package-name@@.api.auth.model.AuthDetails;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static co.tide.@@package-name@@.api.auth.tokens.CommonTokens.*;
import static co.tide.@@package-name@@.api.auth.TestCredentials.*;
import static co.tide.security.rest.Roles.*;

/**
 * The purpose of these methods are to assist in writing ApiTests. They are essentially pre-defined test suites that
 * validate that an endpoint has been secured to a particular level. This is a way of standardising
 * the API tests across BE (Java) rather than each team having a different way of testing permissions/roles
 * for API tests in each service.
 */
public class UnauthorisedAuthArgumentsProvider {

    private UnauthorisedAuthArgumentsProvider() {}

    /**
     * <p>This test data JWT details that should return UNAUTHORISED
     * for the annotation RequireAuthenticatedDirector.</p>
     *
     * <p>To see how to integrate this into a test see:</p>
     * <ol>
     * <li> SampleApiTest.TestDirector </li>
     * <li> SampleApiTest.TestDirectorWithExtraRole </li>
     * </ol>
     *
     * <p>VALID_DIRECTOR_ACCESS_KEY or VALID_DIRECTOR_WEB_ACCESS_KEY would be the correct Auth to make a test pass </p>
     */
    public static class UnauthorisedSetupForRequireAuthDirector implements ArgumentsProvider {

        private static final AuthDetails ACCESS_KEY_WITHOUT_ACTIVE_INSTALLATION =
                new AuthDetails("ACCESS_KEY_WITHOUT_ACTIVE_INSTALLATION", USER_ID, COMPANY_ID, null, null, null,
                        List.of(ACCESS_KEY));

        private static final AuthDetails WEB_ACCESS_KEY_WITHOUT_ACTIVE_INSTALLATION =
                new AuthDetails("WEB_ACCESS_KEY_WITHOUT_ACTIVE_INSTALLATION", USER_ID, COMPANY_ID, null, null, null,
                        List.of(WEB_ACCESS_KEY));

        private static final AuthDetails ACTIVE_INSTALLATION_WITHOUT_ACCESS_KEY =
                new AuthDetails("ACTIVE_INSTALLATION_WITHOUT_ACCESS_KEY", USER_ID, COMPANY_ID, null, null, null,
                        List.of(ACTIVE_INSTALLATION));

        private static final AuthDetails VALID_ACCESS_KEY_WITH_NEGATIVE_USER =
                new AuthDetails("VALID_ACCESS_KEY_WITH_NEGATIVE_USER", -1, COMPANY_ID, null, null, null,
                        List.of(ACTIVE_INSTALLATION, ACCESS_KEY));

        private static final AuthDetails VALID_ACCESS_KEY_WITH_ZEROED_USER =
                new AuthDetails("VALID_ACCESS_KEY_WITH_ZEROED_USER", 0, COMPANY_ID, null, null, null,
                        List.of(ACTIVE_INSTALLATION, ACCESS_KEY));

        private static final AuthDetails NO_PERMISSIONS =
                new AuthDetails("NO_PERMISSIONS", -1, COMPANY_ID, null, null, null,
                        Collections.emptyList());

        private static final AuthDetails GLOBALLY_INCORRECT_PERMISSIONS =
                new AuthDetails("GLOBALLY_INCORRECT_PERMISSIONS", -1, COMPANY_ID, null, null, null,
                        List.of("Cabbages", "Obsidian"));

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(EMPTY_TOKEN,
                            ACCESS_KEY_WITHOUT_ACTIVE_INSTALLATION,
                            WEB_ACCESS_KEY_WITHOUT_ACTIVE_INSTALLATION,
                            ACTIVE_INSTALLATION_WITHOUT_ACCESS_KEY,
                            VALID_ACCESS_KEY_WITH_NEGATIVE_USER,
                            VALID_ACCESS_KEY_WITH_ZEROED_USER,
                            NO_PERMISSIONS,
                            GLOBALLY_INCORRECT_PERMISSIONS,
                            VALID_READER,
                            VALID_STAFF_USER,
                            VALID_CONSENT,
                            VALID_SERVICE_CALL,
                            VALID_OPEN_BANKING_GATEWAY)
                    .map(Arguments::of);
        }
    }

    /**
     * <p>This test data represents JWT details that should return UNAUTHORISED
     * for the annotation RequireAuthenticatedReader.</p>
     *
     * <p>To see how to integrate this into a test see:</p>
     * <ol>
     * <li> SampleApiTest.TestReader </li>
     * <li> SampleApiTest.TestReaderWithExtraRole </li>
     * </ol>
     *
     * <p> VALID_READER would be the correct Auth to make a test pass </p>
     */
    public static class UnauthorisedSetupForRequireAuthReader implements ArgumentsProvider {
        public static final String READER = "READER";

        private static final AuthDetails MISSING_READER_ROLE =
                new AuthDetails("MISSING_READER_ROLE", USER_ID, COMPANY_ID, USER_IDENTIFIER, CREDENTIAL_ID, null,
                        Collections.emptyList());

        private static final AuthDetails MISSING_USER_IDENTIFIER =
                new AuthDetails("MISSING_USER_IDENTIFIER", USER_ID, COMPANY_ID, null, CREDENTIAL_ID, null,
                        List.of(READER));

        private static final AuthDetails MISSING_CREDENTIAL_ID =
                new AuthDetails("MISSING_CREDENTIAL_ID", USER_ID, COMPANY_ID, USER_IDENTIFIER, null, null,
                        List.of(READER));

        private static final AuthDetails MISSING_COMPANY_ID =
                new AuthDetails("MISSING_COMPANY_ID", USER_ID, null, USER_IDENTIFIER, CREDENTIAL_ID, null,
                        List.of(READER));

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(EMPTY_TOKEN,
                            MISSING_READER_ROLE,
                            MISSING_USER_IDENTIFIER,
                            MISSING_CREDENTIAL_ID,
                            MISSING_COMPANY_ID,
                            VALID_DIRECTOR_ACCESS_KEY,
                            VALID_DIRECTOR_WEB_ACCESS_KEY,
                            VALID_STAFF_USER,
                            VALID_CONSENT,
                            VALID_SERVICE_CALL,
                            VALID_OPEN_BANKING_GATEWAY)
                    .map(Arguments::of);
        }
    }

    /**
     * <p>This test data represents JWT details that should return UNAUTHORISED
     * for the annotation RequireAuthenticatedStaffUser.</p>
     *
     * <p>To see how to integrate this into a test see:</p>
     * <ol>
     * <li> SampleApiTest.TestStaff</li>
     * <li> SampleApiTest.TestStaffExtraRole</li>
     * </ol>
     *
     * <p> VALID_STAFF_USER would be the correct Auth to make a test pass </p>
     */
    public static class UnauthorisedSetupForRequireAuthStaffUser implements ArgumentsProvider {

        private static final AuthDetails NO_STAFF_USER_ID =
                new AuthDetails("NO_STAFF_USER_ID", 0, null, null, null, null, List.of(STAFF_USER));

        private static final AuthDetails NO_STAFF_ROLE =
                new AuthDetails("NO_STAFF_ROLE", 0, null, null, null, STAFF_USER_ID, Collections.emptyList());

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(EMPTY_TOKEN,
                            NO_STAFF_USER_ID,
                            NO_STAFF_ROLE,
                            VALID_DIRECTOR_ACCESS_KEY,
                            VALID_DIRECTOR_WEB_ACCESS_KEY,
                            VALID_READER,
                            VALID_CONSENT,
                            VALID_SERVICE_CALL,
                            VALID_OPEN_BANKING_GATEWAY)
                    .map(Arguments::of);
        }
    }

    /**
     * <p>This test data represents JWT details that should return UNAUTHORISED
     * for the annotation RequireConsentAccess.</p>
     *
     * <p>To see how to integrate this into a test see:</p>
     * <ol>
     * <li> SampleApiTest.TestConsent</li>
     * <li> SampleApiTest.TestConsentExtraRole</li>
     * </ol>
     *
     * <p> VALID_CONSENT would be the correct Auth to make a test pass </p>
     */
    public static class UnauthorisedSetupForRequireConsentAccess implements ArgumentsProvider {

        private static final AuthDetails NO_ACTIVE_INSTALLATION =
                new AuthDetails("NO_ACTIVE_INSTALLATION", 0, null, null, null, null, List.of(CONSENT_ACCESS_KEY));

        private static final AuthDetails NO_CONSENT_ACCESS_KEY =
                new AuthDetails("NO_CONSENT_ACCESS_KEY", 0, null, null, null, null, List.of(ACTIVE_INSTALLATION));

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(EMPTY_TOKEN,
                            NO_ACTIVE_INSTALLATION,
                            NO_CONSENT_ACCESS_KEY,
                            VALID_DIRECTOR_ACCESS_KEY,
                            VALID_DIRECTOR_WEB_ACCESS_KEY,
                            VALID_READER,
                            VALID_STAFF_USER,
                            VALID_SERVICE_CALL,
                            VALID_OPEN_BANKING_GATEWAY)
                    .map(Arguments::of);
        }
    }

    /**
     * <p>This test data represents JWT details that should return UNAUTHORISED
     * for the annotation RequireInternalService.</p>
     *
     * <p>To see how to integrate this into a test see:</p>
     * <ol>
     * <li> SampleApiTest.TestInternal</li>
     * <li> SampleApiTest.TestInternalExtraRole</li>
     * </ol>
     *
     * <p> VALID_BATCH_JOB would be the correct Auth to make a test pass </p>
     */
    public static class UnauthorisedSetupForRequireInternalService implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(EMPTY_TOKEN,
                            VALID_DIRECTOR_ACCESS_KEY,
                            VALID_DIRECTOR_WEB_ACCESS_KEY,
                            VALID_READER,
                            VALID_STAFF_USER,
                            VALID_CONSENT,
                            VALID_OPEN_BANKING_GATEWAY)
                    .map(Arguments::of);
        }
    }

    /**
     * <p>This test data represents JWT details that should return UNAUTHORISED
     * for the annotation RequireOpenBankingGateway.</p>
     *
     * <p>To see how to integrate this into a test see:</p>
     * <ol>
     * <li> SampleApiTest.TestOpenBanking</li>
     * <li> SampleApiTest.TestOpenBankingExtraRole</li>
     * </ol>
     *
     * <p> VALID_OPEN_BANKING_GATEWAY would be the correct Auth to make a test pass </p>
     */
    public static class UnauthorisedSetupForRequireOpenBankingGateway implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {

            return Stream.of(EMPTY_TOKEN,
                            VALID_DIRECTOR_ACCESS_KEY,
                            VALID_DIRECTOR_WEB_ACCESS_KEY,
                            VALID_READER,
                            VALID_STAFF_USER,
                            VALID_CONSENT,
                            VALID_SERVICE_CALL)
                    .map(Arguments::of);
        }
    }
}
