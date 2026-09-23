package co.fastshipping.usecase.incident;

import co.fastshipping.model.incident.Incident;
import co.fastshipping.model.incident.exception.IncidentNotFoundException;
import co.fastshipping.model.incident.gateways.IncidentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetIncidentUseCase {
    private final IncidentRepository repository;

    public Incident execute(Long id) {
        Incident incident = repository.findById(id);
        if (incident == null) {
            throw new IncidentNotFoundException("Incident not found: " + id);
        }
        return incident;
    }
}
