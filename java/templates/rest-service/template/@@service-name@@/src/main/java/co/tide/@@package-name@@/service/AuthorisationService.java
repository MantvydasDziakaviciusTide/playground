package co.tide.@@package-name@@.service;

import co.tide.logging.ExceptionLoggable;
import co.tide.security.auth.jwt.AuthenticationMetadata;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import static co.tide.security.rest.Roles.BATCH_JOB;
import static co.tide.security.rest.Roles.STAFF_USER;

/**
 * A bare-bones service that validates whether a request should be granted access to resources
 * associated with a given user ID. In the future, this service will be moved into the JWT library.
 * The main reason for not doing that now is that to validate company and account IDs, we need
 * to make gRPC calls to the User Service and Account Service. I do not want to promote that
 * sort of behaviour, but you can extend this class if that's what you want to do.
 */
@Service
public class AuthorisationService implements ExceptionLoggable {

    /**
     * Validates whether the authentication metadata from the request should be able to access resources
     * associated with the given user ID. Staff Platform requests and internal requests are allowed to
     * access any resource. For external requests, we check whether the
     * user ID matches the ID in the authentication metadata. This method will raise the necessary security
     * alerts, so no need to do that yourself.
     *
     * @param authMetadata the authentication metadata
     * @param principalUserId the user ID
     * @return true if access should be granted, false otherwise
     */
    public boolean canRequestAccessUser(AuthenticationMetadata authMetadata, String principalUserId) {

        if (principalUserId.isBlank() || principalUserId.equals("-1")) {
            getExceptionLogger().warn("SECURITY: Invalid user ID being requested [user={}]", principalUserId);
            return false;
        }

        if (containsRole(authMetadata, BATCH_JOB)) {
            // We should be requiring a user ID in internal tokens, but for backwards compatibility, we're allowing this
            return true;
        }

        if (containsRole(authMetadata, STAFF_USER)) {
            // Staff Platform users can access any user
            return true;
        }

        if (!authMetadata.getPrincipalUserId().equals(principalUserId)) {
            // The user ID in the auth data is in the MDC, so no need to include in the logs
            getExceptionLogger().warn("SECURITY: Attempt to access unauthorised user [{}]", principalUserId);
            return false;
        }
        return true;
    }

    private boolean containsRole(AuthenticationMetadata authMetadata, String role) {
        return authMetadata.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(s -> s.equals(role));
    }
}
