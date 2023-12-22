package co.tide.@@package-name@@.api;

import co.tide.logging.ExceptionLoggable;
import co.tide.security.auth.jwt.AuthenticationMetadata;
import co.tide.security.rest.annotations.RequireAuthenticatedStaffUser;
import co.tide.v4.rest.response.Response;
import co.tide.v4.rest.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static co.tide.@@package-name@@.api.CommonApiUtils.selfLinkOf;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * A sample REST controller containing some staff endpoints. Either rename and add your endpoints here, or delete. See
 * the {@code AuthorisationApiTest} test class for an example of testing the endpoint in here.
 */
@RestController
@RequestMapping("/agent-api/v4/staff-demo")
@Tag(name = "Dummy Staff Controller", description ="Demo API endpoints. Be worried if you see this in prod...")
public class DummyStaffController implements ExceptionLoggable {

    @Operation(
            summary = "Get a user",
            security = { @SecurityRequirement(name = "bearer-key")})
    // As per the request guidelines, these are the set of response codes that our endpoints can return.
    @ApiResponse(
            responseCode = "200",
            description = "A helpful message if you did the right thing"
    )
    @ApiResponse(
            responseCode = "400",
            description = "You made a very bad request. So bad. The worst."
    )
    @ApiResponse(
            responseCode = "401",
            description = "You did not have valid credentials"
    )
    @ApiResponse(
            responseCode = "500",
            description = "It all went horribly wrong (either here or in another service)."
    )
    @GetMapping(path = "/users/{userId}", produces = MediaTypes.DUMMY_USER)
    @RequireAuthenticatedStaffUser
    public SuccessResponse<Map<String, String>> getUser(
            @Parameter(description = "ID of the user to retrieve information for", example = "123", required = true)
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal AuthenticationMetadata authenticationMetadata
    ) {
        Optional<String> staffOpt = authenticationMetadata.getStaffUserId();
        if (staffOpt.isEmpty()) {
            // Only here to avoid the warning on getting the staff user without checking its existence
            throw new RuntimeException("This will never happen");
        }
        getLogger().info("Staff user [{}] attempting to access data for user [{}]", staffOpt.get(), userId);

        String link = selfLinkOf(methodOn(this.getClass()).getUser(userId, authenticationMetadata));
        return Response.success(Map.of("info", "You're staff, so of course you can access " + userId), link);
    }

}