package co.tide.@@package-name@@.api;

import co.tide.@@package-name@@.exception.TeapotException;
import co.tide.v4.rest.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static co.tide.v4.rest.response.ErrorResponse.ErrorResponseBuilder.anErrorResponse;

/**
 * A sample exception handler, that adds an additional exception mapping to the Tide defaults.
 * You'll probably want to remove the TeapotException.
 */
@ControllerAdvice
public class CustomExceptionAdvice extends co.tide.v4.spring.exception.ExceptionAdvice {

    @ExceptionHandler(TeapotException.class)
    public ResponseEntity<Response> teapotException(TeapotException exception) {

        getLogger().info("Apparently this is not a coffee pot", exception);

        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                .body(anErrorResponse().build());
    }
}