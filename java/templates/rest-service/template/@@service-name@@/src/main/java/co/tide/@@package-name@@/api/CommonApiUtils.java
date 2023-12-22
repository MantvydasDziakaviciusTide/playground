package co.tide.@@package-name@@.api;

import co.tide.v4.rest.response.Response;
import co.tide.v4.rest.response.SuccessResponse;
import java.net.URI;


import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class CommonApiUtils {

    private CommonApiUtils() {
    }

    public static String selfLinkOf(Response response) {
        URI uri = linkTo(response).toUri();
        return uri.getPath() + (isBlank(uri.getQuery()) ? "" : "?" + uri.getQuery());
    }

}
