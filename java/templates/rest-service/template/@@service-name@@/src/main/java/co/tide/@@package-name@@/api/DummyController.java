package co.tide.@@package-name@@.api;

import co.tide.v4.rest.response.Response;
import co.tide.v4.rest.response.SuccessResponse;
import co.tide.logging.ExceptionLoggable;
import co.tide.security.auth.jwt.AuthenticationMetadata;
import co.tide.@@package-name@@.exception.TeapotException;
import co.tide.@@package-name@@.service.AuthorisationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static co.tide.@@package-name@@.api.CommonApiUtils.selfLinkOf;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * A sample REST controller. Either rename and add your endpoints here, or delete. See the
 * {@code AuthorisationApiTest} test class for an example of testing the endpoint in here.
 */
@RestController
@RequestMapping("/api/v4/demo")
@Tag(name = "Dummy Controller", description ="Demo API endpoints. Be worried if you see this in prod...")
public class DummyController implements ExceptionLoggable {

    private final AuthorisationService authorisationService;

    @Autowired
    public DummyController(AuthorisationService authorisationService) {
        this.authorisationService = authorisationService;
    }

    @Operation(
            summary = "Get a user",
            security = { @SecurityRequirement(name = "bearer-key") })
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
            responseCode = "403",
            description = "You do not have permission to access the user specified in the path"
    )
    @ApiResponse(
            responseCode = "500",
            description = "It all went horribly wrong (either here or in another service)."
    )
    // Not this one though. This is for example purposes only. Do not return a 418 in real code.
    @ApiResponse(
            responseCode = "418",
            description = "Only a teapot would ask for user ID 100."
    )
    @GetMapping(path = "/users/{userId}", produces = MediaTypes.DUMMY_USER)
    @PreAuthorize("isAuthenticatedWithAnyPermission()")
    public SuccessResponse<Map<String, String>> getUser(
            @Parameter(description = "ID of the user to retrieve information for", example = "123", required = true)
            @PathVariable("userId") String userId,
            @AuthenticationPrincipal AuthenticationMetadata authenticationMetadata
    ) {
        getLogger().info("User [{}] attempting to access data for user [{}]",
                authenticationMetadata.getPrincipalUserId(), userId);

        if (userId.equals("100")) {
            throw new TeapotException();
        } else if (authorisationService.canRequestAccessUser(authenticationMetadata, userId)) {
            String link = selfLinkOf(methodOn(this.getClass()).getUser(userId, authenticationMetadata));
            return Response.success(Map.of("info", "You can access " + userId), link);
        } else {
            // Logged in the authorisation service class
            throw new AccessDeniedException("You shall not pass");
        }
    }

}
