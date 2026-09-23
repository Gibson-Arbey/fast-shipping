package co.fastshipping.jpa.mapper;

import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.DeliveryStatus;
import co.fastshipping.model.incident.Incident;
import co.fastshipping.model.incident.IncidentStatus;
import co.fastshipping.model.incident.IncidentType;
import co.fastshipping.model.routeassignment.RouteAssignment;
import co.fastshipping.model.routeassignment.RouteAssignmentStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogisticsJpaMapperTest {
    @Test
    void routeAssignmentMappingPreservesFields() {
        LocalDateTime started = LocalDateTime.of(2026, 1, 1, 8, 0);
        RouteAssignment source = RouteAssignment.restore(1L, 2L, 3L, 4L,
                RouteAssignmentStatus.IN_PROGRESS, started, null);

        var entity = RouteAssignmentJpaMapper.toEntity(source);
        var restored = RouteAssignmentJpaMapper.toDomain(entity);

        assertEquals(source.getRouteId(), restored.getRouteId());
        assertEquals(source.getStatus(), restored.getStatus());
        assertEquals(source.getStartedAt(), restored.getStartedAt());
    }

    @Test
    void deliveryMappingPreservesExternalAndInternalIds() {
        Delivery source = Delivery.restore(1L, 10L, 20L, 30L, DeliveryStatus.IN_TRANSIT);

        var restored = DeliveryJpaMapper.toDomain(DeliveryJpaMapper.toEntity(source));

        assertEquals(source.getParcelId(), restored.getParcelId());
        assertEquals(source.getRouteAssignmentId(), restored.getRouteAssignmentId());
        assertEquals(source.getStatus(), restored.getStatus());
    }

    @Test
    void incidentMappingPreservesDatesAndStatus() {
        LocalDateTime created = LocalDateTime.of(2026, 1, 1, 8, 0);
        LocalDateTime resolved = created.plusHours(1);
        Incident source = Incident.restore(1L, 2L, 3L, IncidentType.OTHER,
                IncidentStatus.RESOLVED, "Issue", created, resolved, 4L);

        var restored = IncidentJpaMapper.toDomain(IncidentJpaMapper.toEntity(source));

        assertEquals(source.getStatus(), restored.getStatus());
        assertEquals(source.getCreatedAt(), restored.getCreatedAt());
        assertEquals(source.getResolvedAt(), restored.getResolvedAt());
    }
}
