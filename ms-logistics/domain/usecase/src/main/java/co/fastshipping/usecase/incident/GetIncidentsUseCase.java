package co.fastshipping.usecase.incident;

import co.fastshipping.model.incident.Incident;
import co.fastshipping.model.incident.gateways.IncidentRepository;
import co.fastshipping.usecase.incident.query.GetIncidentQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetIncidentsUseCase {
    private final IncidentRepository repository;

    public List<Incident> execute(GetIncidentQuery query) {
        return repository.findAllByFilters(query.routeAssignmentId(), query.deliveryId(), query.type(), query.status());
    }
}
