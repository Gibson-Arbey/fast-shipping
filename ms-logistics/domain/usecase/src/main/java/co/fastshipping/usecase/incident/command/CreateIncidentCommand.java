package co.fastshipping.usecase.incident.command;

public record CreateIncidentCommand(
        Long routeAssignmentId,
        Long deliveryId,
        String type,
        String description,
        Long reportedBy
) {
}
