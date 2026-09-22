package co.fastshipping.api.route.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RegisterRouteStopRequest(
        @NotNull(message = "sequence is required")
        @Positive(message = "sequence must be greater than zero")
        Integer sequence,
        @NotBlank(message = "city is required")
        @Size(max = 100, message = "city must not exceed 100 characters")
        String city
) {
}
