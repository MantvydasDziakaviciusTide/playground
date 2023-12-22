package co.tide.@@package-name@@.service;

import co.tide.security.auth.jwt.AuthenticationMetadata;
import co.tide.security.rest.Roles;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthorisationServiceUTest {
    private AuthorisationService service;

    @BeforeEach
    void setup() {
        service = new AuthorisationService();
    }

    @Test
    void userAccess_rejects_whenUserIdIsNegative() {
        var md = getUserToken();
        var outcome = service.canRequestAccessUser(md, "-1");

        assertFalse(outcome);
    }

    @Test
    void userAccess_rejects_whenUserIdIsBlank() {
        var md = getUserToken();
        var outcome = service.canRequestAccessUser(md, "");

        assertFalse(outcome);
    }

    @Test
    void userAccess_allowsAccess_whenUserIdMatchesToken() {
        var md = getUserToken();
        var outcome = service.canRequestAccessUser(md, "1");

        assertTrue(outcome);
    }

    @Test
    void userAccess_allowsAccess_forInternalCall() {
        var md = getInternalToken();
        var outcome = service.canRequestAccessUser(md, "1");

        assertTrue(outcome);
    }

    @Test
    void userAccess_allowsAccess_forStaffToken() {
        var md = getStaffToken();
        var outcome = service.canRequestAccessUser(md, "1");

        assertTrue(outcome);
    }

    @Test
    void userAccess_rejects_forIdMisMatch() {
        var md = getUserToken();
        var outcome = service.canRequestAccessUser(md, "2");

        assertFalse(outcome);
    }

    private AuthenticationMetadata getUserToken() {
        return AuthenticationMetadata.newBuilder()
                .withRequestId("requestId")
                .withPrincipalUserId("1")
                .withInstallationId(2)
                .withUserType("NORMAL_USER")
                .withAuthorities(List.of(Roles.CLIENT_KEY))
                .build();
    }

    private AuthenticationMetadata getInternalToken() {
        return AuthenticationMetadata.newBuilder()
                .withRequestId("requestId")
                .withUserType("BATCH_JOB")
                .withAuthorities(List.of(Roles.BATCH_JOB))
                .build();
    }

    private AuthenticationMetadata getStaffToken() {
        return AuthenticationMetadata.newBuilder()
                .withRequestId("requestId")
                .withAuthorities(List.of(Roles.STAFF_USER))
                .build();
    }
}