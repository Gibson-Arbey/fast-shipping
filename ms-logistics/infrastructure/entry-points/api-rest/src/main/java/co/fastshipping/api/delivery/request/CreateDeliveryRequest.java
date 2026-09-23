package co.fastshipping.api.delivery.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateDeliveryRequest(
        @NotNull(message = "parcelId is required") @Positive(message = "parcelId must be greater than zero") Long parcelId,
        @NotNull(message = "routeAssignmentId is required") @Positive(message = "routeAssignmentId must be greater than zero") Long routeAssignmentId,
        @NotNull(message = "routeStopId is required") @Positive(message = "routeStopId must be greater than zero") Long routeStopId
) {
}
