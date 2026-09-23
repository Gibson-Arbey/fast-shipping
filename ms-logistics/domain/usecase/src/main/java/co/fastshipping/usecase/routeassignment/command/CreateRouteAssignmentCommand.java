package co.fastshipping.usecase.routeassignment.command;

public record CreateRouteAssignmentCommand(Long routeId, Long driverId, Long vehicleId) {
}
