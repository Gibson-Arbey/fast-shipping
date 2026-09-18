package co.fastshipping.api.driver.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateDriverStatusRequest(
        @NotBlank(message = "status is required")
        String status
) {
}
