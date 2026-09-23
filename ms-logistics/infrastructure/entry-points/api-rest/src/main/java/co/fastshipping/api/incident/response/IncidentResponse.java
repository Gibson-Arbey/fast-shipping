package co.fastshipping.api.incident.response;

import java.time.LocalDateTime;

public record IncidentResponse(
        Long id,
        Long routeAssignmentId,
        Long deliveryId,
        String type,
        String status,
        String description,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt,
        Long reportedBy
) {
}
