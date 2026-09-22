package co.fastshipping.api.route.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateRouteStatusRequest(
        @NotBlank(message = "status is required")
        String status
) {
}
