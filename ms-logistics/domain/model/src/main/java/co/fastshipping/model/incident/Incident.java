package co.fastshipping.model.incident;

import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Incident {

    private final Long id;
    private final Long routeAssignmentId;
    private final Long deliveryId;
    private final IncidentType type;
    private final IncidentStatus status;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime resolvedAt;
    private final Long reportedBy;

    private Incident(
            Long id,
            Long routeAssignmentId,
            Long deliveryId,
            IncidentType type,
            IncidentStatus status,
            String description,
            LocalDateTime createdAt,
            LocalDateTime resolvedAt,
            Long reportedBy
    ) {
        requirePositive(routeAssignmentId, "routeAssignmentId");
        if (deliveryId != null && deliveryId <= 0) {
            throw new InvalidFieldException("deliveryId must be greater than zero");
        }
        if (type == null) {
            throw new InvalidFieldException("type cannot be null");
        }
        if (status == null) {
            throw new InvalidFieldException("status cannot be null");
        }
        if (description == null || description.isBlank()) {
            throw new InvalidFieldException("description cannot be blank");
        }
        if (createdAt == null) {
            throw new InvalidFieldException("createdAt cannot be null");
        }
        if (resolvedAt != null && resolvedAt.isBefore(createdAt)) {
            throw new InvalidFieldException("resolvedAt cannot be before createdAt");
        }
        if (status == IncidentStatus.RESOLVED && resolvedAt == null) {
            throw new InvalidFieldException("resolved incidents must have resolvedAt");
        }
        if (status != IncidentStatus.RESOLVED && resolvedAt != null) {
            throw new InvalidFieldException("only resolved incidents can have resolvedAt");
        }
        requirePositive(reportedBy, "reportedBy");

        this.id = id;
        this.routeAssignmentId = routeAssignmentId;
        this.deliveryId = deliveryId;
        this.type = type;
        this.status = status;
        this.description = description.trim();
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
        this.reportedBy = reportedBy;
    }

    public static Incident create(Long routeAssignmentId, Long deliveryId, IncidentType type, String description, Long reportedBy) {
        return new Incident(null, routeAssignmentId, deliveryId, type, IncidentStatus.OPEN, description, LocalDateTime.now(), null, reportedBy);
    }

    public static Incident restore(
            Long id,
            Long routeAssignmentId,
            Long deliveryId,
            IncidentType type,
            IncidentStatus status,
            String description,
            LocalDateTime createdAt,
            LocalDateTime resolvedAt,
            Long reportedBy
    ) {
        return new Incident(id, routeAssignmentId, deliveryId, type, status, description, createdAt, resolvedAt, reportedBy);
    }

    public Incident start() {
        requireStatus(IncidentStatus.OPEN, "Only open incidents can start");
        return withStatus(IncidentStatus.IN_PROGRESS, null);
    }

    public Incident resolve(LocalDateTime at) {
        if (status != IncidentStatus.OPEN && status != IncidentStatus.IN_PROGRESS) {
            throw new InvalidFieldException("Only open or in-progress incidents can be resolved");
        }
        if (at == null || at.isBefore(createdAt)) {
            throw new InvalidFieldException("resolvedAt must be on or after createdAt");
        }
        return withStatus(IncidentStatus.RESOLVED, at);
    }

    public Incident cancel() {
        if (status != IncidentStatus.OPEN && status != IncidentStatus.IN_PROGRESS) {
            throw new InvalidFieldException("Only open or in-progress incidents can be cancelled");
        }
        return withStatus(IncidentStatus.CANCELLED, null);
    }

    private Incident withStatus(IncidentStatus newStatus, LocalDateTime newResolvedAt) {
        return new Incident(id, routeAssignmentId, deliveryId, type, newStatus, description, createdAt, newResolvedAt, reportedBy);
    }

    private void requireStatus(IncidentStatus expected, String message) {
        if (status != expected) {
            throw new InvalidFieldException(message);
        }
    }

    private static void requirePositive(Long value, String field) {
        if (value == null || value <= 0) {
            throw new InvalidFieldException(field + " must be greater than zero");
        }
    }
}
