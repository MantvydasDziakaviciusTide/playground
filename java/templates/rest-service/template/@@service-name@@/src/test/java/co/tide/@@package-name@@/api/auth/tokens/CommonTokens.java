package co.tide.@@package-name@@.api.auth.tokens;

import co.tide.@@package-name@@.api.auth.model.AuthDetails;
import java.util.Collections;
import java.util.List;

import static co.tide.@@package-name@@.api.auth.TestCredentials.*;
import static co.tide.security.rest.Roles.*;

/**
 * A collection of standard tokens that can be used when testing writing API tests. Each represents a representative
 * token in use at Tide.
 */
public class CommonTokens {

    /**
     * Auth details for a standard user of the app. These correspond to directors who are accessing the app on mobile.
     */
    public static final AuthDetails VALID_DIRECTOR_ACCESS_KEY =
            new AuthDetails("VALID_DIRECTOR_ACCESS_KEY", USER_ID, COMPANY_ID, null, null, null,
                    List.of(ACCESS_KEY, ACTIVE_INSTALLATION));

    /**
     * Auth details for a standard user logged in on web. These are all directors.
     */
    public static final AuthDetails VALID_DIRECTOR_WEB_ACCESS_KEY =
            new AuthDetails("VALID_DIRECTOR_WEB_ACCESS_KEY", USER_ID, COMPANY_ID, null, null, null,
                    List.of(WEB_ACCESS_KEY, ACTIVE_INSTALLATION));

    /**
     * Auth details for someone logged in to new auth with reader credentials. These are typically team members with
     * very limited access.
     */
    public static final AuthDetails VALID_READER =
            new AuthDetails("VALID_READER", USER_ID, COMPANY_ID, USER_IDENTIFIER, CREDENTIAL_ID, null,
                    List.of("READER"));

    /**
     * Auth details for a Tide staff member, e.g. member support or PSO. These calls are via staff platform or retool.
     */
    public static final AuthDetails VALID_STAFF_USER =
            new AuthDetails("VALID_STAFF_USER", 0, null, null, null, STAFF_USER_ID,
                    List.of(STAFF_USER));

    /**
     * Auth details for an open banking request trying to get a new consent.
     */
    public static final AuthDetails VALID_CONSENT =
            new AuthDetails("VALID_CONSENT", 0, null, null, null, null,
                    List.of(CONSENT_ACCESS_KEY, ACTIVE_INSTALLATION));

    /**
     * Auth details for a call between different microservices.
     */
    public static final AuthDetails VALID_SERVICE_CALL =
            new AuthDetails("VALID_BATCH_JOB", 0, null, null, null, null,
                    List.of(BATCH_JOB));

    /**
     * Auth details for an open banking request trying to perform an action.
     */
    public static final AuthDetails VALID_OPEN_BANKING_GATEWAY =
            new AuthDetails("VALID_OPEN_BANKING_GATEWAY", 0, null, null, null, null,
                    List.of(OPEN_BANKING_GATEWAY));

    /**
     * Auth details for an empty token.
     */
    public static final AuthDetails EMPTY_TOKEN =
            new AuthDetails("EMPTY_TOKEN", 0, null, null, null, null,
                    Collections.emptyList());

    private CommonTokens() {
    }
}
