package co.fastshipping.api.incident.mapper;

import co.fastshipping.api.incident.response.IncidentResponse;
import co.fastshipping.model.incident.Incident;

import java.util.List;

public final class IncidentResponseMapper {
    private IncidentResponseMapper() { }

    public static IncidentResponse toResponse(Incident incident) {
        if (incident == null) return null;
        return new IncidentResponse(
                incident.getId(), incident.getRouteAssignmentId(), incident.getDeliveryId(),
                incident.getType().name(), incident.getStatus().name(), incident.getDescription(),
                incident.getCreatedAt(), incident.getResolvedAt(), incident.getReportedBy()
        );
    }

    public static List<IncidentResponse> toResponse(List<Incident> incidents) {
        return incidents.stream().map(IncidentResponseMapper::toResponse).toList();
    }
}
