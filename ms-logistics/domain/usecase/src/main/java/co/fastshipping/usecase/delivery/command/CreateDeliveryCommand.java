package co.fastshipping.usecase.delivery.command;

public record CreateDeliveryCommand(Long parcelId, Long routeAssignmentId, Long routeStopId) {
}
