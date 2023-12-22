package co.tide.@@package-name@@.api.auth.model;

import java.util.List;

/**
 * A data class representing details that can be used to populate a token.
 */
public class AuthDetails {

    private String label;

    private int userId;

    private String companyId;

    private String credentialId;

    private String staffUserId;

    private String userIdentifier;

    private List<String> permissions;

    /**
     * Populates all possible authentication details of a client hitting a Tide API.
     *
     * @param label A descriptive label for these details
     * @param userId The legacy userId for a member
     * @param companyId The id of the company the member belongs to
     * @param userIdentifier The uuid of the user (using access management based auth)
     * @param credentialId The uuid of the webauthn credential used to log in, effectively equivalent
     *                     to an installation (using access management based auth)
     * @param staffUserId The id of a Tide staff member making a call
     * @param permissions The list of permissions/roles that the caller has
     */
    public AuthDetails(String label, int userId, String companyId, String userIdentifier,
            String credentialId, String staffUserId, List<String> permissions) {
        this.label = label;
        this.userId = userId;
        this.companyId = companyId;
        this.credentialId = credentialId;
        this.staffUserId = staffUserId;
        this.userIdentifier = userIdentifier;
        this.permissions = permissions;
    }

    /**
     * @return The label for AuthDetail object
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return The legacy userId for a member
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return The id of the company the member belongs to
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * @return The uuid of the user (using access management based auth)
     */
    public String getUserIdentifier() {
        return userIdentifier;
    }

    /**
     * @return The uuid of the webauthn credential used to log in,
     *         effectively equivalent to an installation (using access management based auth)
     */
    public String getCredentialId() {
        return credentialId;
    }

    /**
     * @return The id of a Tide staff member making a call
     */
    public String getStaffUserId() {
        return staffUserId;
    }

    /**
     * @return The list of permissions that the caller has
     */
    public List<String> getPermissions() {
        return permissions;
    }

    public AuthDetails setLabel(String label) {
        this.label = label;
        return this;
    }

    public AuthDetails setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public AuthDetails setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public AuthDetails setCredentialId(String credentialId) {
        this.credentialId = credentialId;
        return this;
    }

    public AuthDetails setStaffUserId(String staffUserId) {
        this.staffUserId = staffUserId;
        return this;
    }

    public AuthDetails setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
        return this;
    }

    public AuthDetails setPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    @Override
    public String toString() {
        if (label == null || label.isEmpty()) {
            return "AuthDetails{" +
                    "userId=" + userId +
                    ", companyId='" + companyId + '\'' +
                    ", userIdentifier='" + userIdentifier + '\'' +
                    ", credentialId='" + credentialId + '\'' +
                    ", staffUserId='" + staffUserId + '\'' +
                    ", permissions=" + permissions +
                    '}';
        } else {
            return label;
        }
    }
}
