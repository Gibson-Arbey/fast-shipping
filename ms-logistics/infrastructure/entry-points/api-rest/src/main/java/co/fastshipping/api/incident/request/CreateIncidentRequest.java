package co.fastshipping.api.incident.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateIncidentRequest(
        @NotNull(message = "routeAssignmentId is required") @Positive(message = "routeAssignmentId must be greater than zero") Long routeAssignmentId,
        @Positive(message = "deliveryId must be greater than zero") Long deliveryId,
        @NotBlank(message = "type is required") String type,
        @NotBlank(message = "description is required") @Size(max = 500, message = "description must not exceed 500 characters") String description
) {
}
