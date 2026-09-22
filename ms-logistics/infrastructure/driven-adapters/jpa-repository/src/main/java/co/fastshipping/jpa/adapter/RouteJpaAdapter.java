package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.RouteJpaMapper;
import co.fastshipping.jpa.repository.RouteJpaRepository;
import co.fastshipping.model.route.Route;
import co.fastshipping.model.route.RouteStatus;
import co.fastshipping.model.route.gateways.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RouteJpaAdapter implements RouteRepository {

    private final RouteJpaRepository routeJpaRepository;

    @Override
    @Transactional
    public Route save(Route route) {
        return RouteJpaMapper.toDomain(routeJpaRepository.save(RouteJpaMapper.toEntity(route)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Route> findAllByFilters(RouteStatus status, String name) {
        return routeJpaRepository.findAllByFilters(status, name)
                .stream()
                .map(RouteJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Route findById(Long id) {
        return routeJpaRepository.findById(id)
                .map(RouteJpaMapper::toDomain)
                .orElse(null);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, RouteStatus status) {
        routeJpaRepository.updateStatus(id, status);
    }

    @Override
    public boolean existsById(Long id) {
        return routeJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return routeJpaRepository.existsByNameIgnoreCase(name);
    }
}
