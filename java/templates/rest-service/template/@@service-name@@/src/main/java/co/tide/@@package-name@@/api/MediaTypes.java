package co.tide.@@package-name@@.api;

/**
 * Utility class that contains constants defining the media types produced and required by your service.
 *
 * @see <a href="https://tideaccount.atlassian.net/wiki/spaces/TBE/pages/2986377989/APIv4+guideline#MUST-use-custom-media-types-instead-of-application/json">APIv4 Guideline</a>
 */
public final class MediaTypes {

    private MediaTypes() {}

    public static final String DUMMY_USER = "application/vnd.tide.@@service-name@@.dummy-user+json;version=1";
}
