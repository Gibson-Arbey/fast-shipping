package co.fastshipping.api.routeassignment.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateRouteAssignmentRequest(
        @NotNull(message = "routeId is required") @Positive(message = "routeId must be greater than zero") Long routeId,
        @NotNull(message = "driverId is required") @Positive(message = "driverId must be greater than zero") Long driverId,
        @NotNull(message = "vehicleId is required") @Positive(message = "vehicleId must be greater than zero") Long vehicleId
) {
}
