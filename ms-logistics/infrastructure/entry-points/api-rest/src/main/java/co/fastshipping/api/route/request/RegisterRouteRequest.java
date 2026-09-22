package co.fastshipping.api.route.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterRouteRequest(
        @NotBlank(message = "name is required")
        @Size(max = 100, message = "name must not exceed 100 characters")
        String name,
        @NotEmpty(message = "stops are required")
        @Size(min = 2, message = "a route must have at least two stops")
        Set<@Valid RegisterRouteStopRequest> stops
) {
}
