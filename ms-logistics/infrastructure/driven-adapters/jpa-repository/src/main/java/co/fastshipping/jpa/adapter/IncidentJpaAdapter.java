package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.IncidentJpaMapper;
import co.fastshipping.jpa.repository.IncidentJpaRepository;
import co.fastshipping.model.incident.Incident;
import co.fastshipping.model.incident.IncidentStatus;
import co.fastshipping.model.incident.IncidentType;
import co.fastshipping.model.incident.gateways.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IncidentJpaAdapter implements IncidentRepository {
    private final IncidentJpaRepository repository;

    @Override
    @Transactional
    public Incident save(Incident incident) {
        return IncidentJpaMapper.toDomain(repository.save(IncidentJpaMapper.toEntity(incident)));
    }

    @Override
    @Transactional(readOnly = true)
    public Incident findById(Long id) {
        return repository.findById(id).map(IncidentJpaMapper::toDomain).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Incident> findAllByFilters(Long routeAssignmentId, Long deliveryId, IncidentType type, IncidentStatus status) {
        return repository.findAllByFilters(routeAssignmentId, deliveryId, type, status).stream()
                .map(IncidentJpaMapper::toDomain)
                .toList();
    }
}
