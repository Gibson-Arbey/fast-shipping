package co.fastshipping.usecase.incident.query;

import co.fastshipping.model.exception.InvalidFieldException;
import co.fastshipping.model.incident.IncidentStatus;
import co.fastshipping.model.incident.IncidentType;

public record GetIncidentQuery(
        Long routeAssignmentId,
        Long deliveryId,
        IncidentType type,
        IncidentStatus status
) {
    public GetIncidentQuery(Long routeAssignmentId, Long deliveryId, String type, String status) {
        this(routeAssignmentId, deliveryId, IncidentType.fromString(type), IncidentStatus.fromString(status));
        validatePositive(routeAssignmentId, "routeAssignmentId");
        validatePositive(deliveryId, "deliveryId");
    }

    private static void validatePositive(Long value, String field) {
        if (value != null && value <= 0) {
            throw new InvalidFieldException(field + " must be greater than zero");
        }
    }
}
