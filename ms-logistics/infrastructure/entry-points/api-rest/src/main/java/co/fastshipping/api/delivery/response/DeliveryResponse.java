package co.fastshipping.api.delivery.response;

public record DeliveryResponse(
        Long id,
        Long parcelId,
        Long routeAssignmentId,
        Long routeStopId,
        String status
) {
}
