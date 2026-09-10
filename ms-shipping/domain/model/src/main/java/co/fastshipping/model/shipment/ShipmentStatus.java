package co.fastshipping.model.shipment;

public enum ShipmentStatus {
    CREATED,
    CONFIRMED,
    ASSIGNED,
    PICKED_UP,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERED,
    DELIVERY_FAILED,
    CANCELLED
}