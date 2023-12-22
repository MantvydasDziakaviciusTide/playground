package co.tide.@@package-name@@.api.auth;

/**
 * A helper class with representative values of common authentication test values.
 */
public final class TestCredentials {

    /**
     * A sample legacy userId for a member
     */
    public static final int USER_ID = 38280;

    /**
     * A sample id of the company the member belongs to
     */
    public static final String COMPANY_ID = "25021";

    /**
     * A sample uuid of the webauthn credential used to log in, effectively equivalent
     * to an installation (using access management based auth)
     */
    public static final String CREDENTIAL_ID = "eccb102b-d41f-4a60-8037-a91a9035f003";

    /**
     * A sample id of a Tide staff member making a call
     */
    public static final String STAFF_USER_ID = "192920";

    /**
     * A sample uuid of the user (using access management based auth)
     */
    public static final String USER_IDENTIFIER = "d732ad6b-704b-4e33-a554-8f69bfb96005";

    private TestCredentials() {
    }
}
