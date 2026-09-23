package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.IncidentJpaEntity;
import co.fastshipping.model.incident.Incident;

public final class IncidentJpaMapper {
    private IncidentJpaMapper() { }

    public static IncidentJpaEntity toEntity(Incident incident) {
        if (incident == null) return null;
        return IncidentJpaEntity.builder()
                .id(incident.getId())
                .routeAssignmentId(incident.getRouteAssignmentId())
                .deliveryId(incident.getDeliveryId())
                .type(incident.getType())
                .status(incident.getStatus())
                .description(incident.getDescription())
                .createdAt(incident.getCreatedAt())
                .resolvedAt(incident.getResolvedAt())
                .reportedBy(incident.getReportedBy())
                .build();
    }

    public static Incident toDomain(IncidentJpaEntity entity) {
        if (entity == null) return null;
        return Incident.restore(
                entity.getId(), entity.getRouteAssignmentId(), entity.getDeliveryId(), entity.getType(),
                entity.getStatus(), entity.getDescription(), entity.getCreatedAt(), entity.getResolvedAt(), entity.getReportedBy()
        );
    }
}
