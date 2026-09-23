package co.fastshipping.model.delivery;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

@Getter
public class Delivery {

    private final Long id;
    private final Long parcelId;
    private final Long routeAssignmentId;
    private final Long routeStopId;
    private final DeliveryStatus status;

    private Delivery(Long id, Long parcelId, Long routeAssignmentId, Long routeStopId, DeliveryStatus status) {
        requirePositive(parcelId, "parcelId");
        requirePositive(routeAssignmentId, "routeAssignmentId");
        requirePositive(routeStopId, "routeStopId");
        if (status == null) {
            throw new InvalidFieldException("status cannot be null");
        }
        this.id = id;
        this.parcelId = parcelId;
        this.routeAssignmentId = routeAssignmentId;
        this.routeStopId = routeStopId;
        this.status = status;
    }

    public static Delivery create(Long parcelId, Long routeAssignmentId, Long routeStopId) {
        return new Delivery(null, parcelId, routeAssignmentId, routeStopId, DeliveryStatus.PENDING);
    }

    public static Delivery restore(Long id, Long parcelId, Long routeAssignmentId, Long routeStopId, DeliveryStatus status) {
        return new Delivery(id, parcelId, routeAssignmentId, routeStopId, status);
    }

    public Delivery start() {
        requireStatus(DeliveryStatus.PENDING, "Only pending deliveries can start");
        return withStatus(DeliveryStatus.IN_TRANSIT);
    }

    public Delivery complete() {
        requireStatus(DeliveryStatus.IN_TRANSIT, "Only in-transit deliveries can complete");
        return withStatus(DeliveryStatus.DELIVERED);
    }

    public Delivery fail() {
        requireStatus(DeliveryStatus.IN_TRANSIT, "Only in-transit deliveries can fail");
        return withStatus(DeliveryStatus.FAILED);
    }

    public Delivery cancel() {
        requireStatus(DeliveryStatus.PENDING, "Only pending deliveries can be cancelled");
        return withStatus(DeliveryStatus.CANCELLED);
    }

    private Delivery withStatus(DeliveryStatus newStatus) {
        return new Delivery(id, parcelId, routeAssignmentId, routeStopId, newStatus);
    }

    private void requireStatus(DeliveryStatus expected, String message) {
        if (status != expected) {
            throw new InvalidFieldException(message);
        }
    }

    private static void requirePositive(Long value, String field) {
        if (value == null || value <= 0) {
            throw new InvalidFieldException(field + " must be greater than zero");
        }
    }
}
