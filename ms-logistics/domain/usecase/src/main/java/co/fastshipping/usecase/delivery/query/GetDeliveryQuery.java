package co.fastshipping.usecase.delivery.query;

import co.fastshipping.model.delivery.DeliveryStatus;
import co.fastshipping.model.exception.InvalidFieldException;

public record GetDeliveryQuery(
        Long parcelId,
        Long routeAssignmentId,
        Long routeStopId,
        DeliveryStatus status
) {
    public GetDeliveryQuery(Long parcelId, Long routeAssignmentId, Long routeStopId, String status) {
        this(parcelId, routeAssignmentId, routeStopId, DeliveryStatus.fromString(status));
        validatePositive(parcelId, "parcelId");
        validatePositive(routeAssignmentId, "routeAssignmentId");
        validatePositive(routeStopId, "routeStopId");
    }

    private static void validatePositive(Long value, String field) {
        if (value != null && value <= 0) {
            throw new InvalidFieldException(field + " must be greater than zero");
        }
    }
}
