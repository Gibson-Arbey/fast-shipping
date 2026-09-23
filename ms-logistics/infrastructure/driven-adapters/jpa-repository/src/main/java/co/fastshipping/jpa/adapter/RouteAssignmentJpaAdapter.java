package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.RouteAssignmentJpaMapper;
import co.fastshipping.jpa.repository.RouteAssignmentJpaRepository;
import co.fastshipping.model.routeassignment.RouteAssignment;
import co.fastshipping.model.routeassignment.RouteAssignmentStatus;
import co.fastshipping.model.routeassignment.gateways.RouteAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RouteAssignmentJpaAdapter implements RouteAssignmentRepository {
    private final RouteAssignmentJpaRepository repository;

    @Override
    @Transactional
    public RouteAssignment save(RouteAssignment assignment) {
        return RouteAssignmentJpaMapper.toDomain(repository.save(RouteAssignmentJpaMapper.toEntity(assignment)));
    }

    @Override
    @Transactional(readOnly = true)
    public RouteAssignment findById(Long id) {
        return repository.findById(id).map(RouteAssignmentJpaMapper::toDomain).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RouteAssignment> findAllByFilters(Long routeId, Long driverId, Long vehicleId, RouteAssignmentStatus status) {
        return repository.findAllByFilters(routeId, driverId, vehicleId, status).stream()
                .map(RouteAssignmentJpaMapper::toDomain)
                .toList();
    }
}
