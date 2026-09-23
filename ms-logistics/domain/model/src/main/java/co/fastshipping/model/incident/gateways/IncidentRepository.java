package co.fastshipping.model.incident.gateways;

import co.fastshipping.model.incident.Incident;
import co.fastshipping.model.incident.IncidentStatus;
import co.fastshipping.model.incident.IncidentType;

import java.util.List;

public interface IncidentRepository {
    Incident save(Incident incident);
    Incident findById(Long id);
    List<Incident> findAllByFilters(Long routeAssignmentId, Long deliveryId, IncidentType type, IncidentStatus status);
}
