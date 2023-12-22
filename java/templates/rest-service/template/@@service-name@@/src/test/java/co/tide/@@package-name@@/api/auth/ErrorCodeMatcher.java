package co.tide.@@package-name@@.api.auth;

import co.tide.v4.rest.response.ApiError;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minidev.json.JSONArray;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ErrorCodeMatcher extends TypeSafeMatcher<JSONArray> {

    private final List<ApiError> expectedErrorCodes;

    private ErrorCodeMatcher(List<ApiError> expectedErrorCodes) {

        this.expectedErrorCodes = expectedErrorCodes;
    }

    @Override
    protected boolean matchesSafely(JSONArray actual) {

        if (actual.size() != expectedErrorCodes.size()) {
            return false;
        } else {
            return errorsMatch(actual, expectedErrorCodes);
        }
    }

    @Override
    public void describeTo(Description description) {

        description.appendText("error codes should match [");

        expectedErrorCodes.forEach(apiError ->
                description.appendText("{errorCode='%s', path='%s'}"
                        .formatted(apiError.getErrorCode(), apiError.getPath())));

        description.appendText("]");
    }


    public static Matcher<JSONArray> errorCodesMatch(List<ApiError> expectedErrorCodes) {

        return new ErrorCodeMatcher(expectedErrorCodes);
    }

    private boolean errorsMatch(List<Object> candidates, List<ApiError> expectedErrorCodes) {

        for (Object candidate : candidates) {
            if (expectedErrorCodes.stream().noneMatch(expectedErrorCode -> errorMatches(candidate, expectedErrorCode))) {
                return false;
            }
        }
        return true;
    }

    private boolean errorMatches(Object candidate, ApiError expectedErrorCode) {

        if (candidate instanceof Map<?, ?> candidateMap) {

            return candidateMap.get("errorCode").equals(expectedErrorCode.getErrorCode().toString()) &&
                    Objects.equals(candidateMap.get("path"), expectedErrorCode.getPath());
        }
        return false;
    }
}
