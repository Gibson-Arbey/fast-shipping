package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.DeliveryJpaMapper;
import co.fastshipping.jpa.repository.DeliveryJpaRepository;
import co.fastshipping.model.delivery.Delivery;
import co.fastshipping.model.delivery.DeliveryStatus;
import co.fastshipping.model.delivery.gateways.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeliveryJpaAdapter implements DeliveryRepository {
    private final DeliveryJpaRepository repository;

    @Override
    @Transactional
    public Delivery save(Delivery delivery) {
        return DeliveryJpaMapper.toDomain(repository.save(DeliveryJpaMapper.toEntity(delivery)));
    }

    @Override
    @Transactional(readOnly = true)
    public Delivery findById(Long id) {
        return repository.findById(id).map(DeliveryJpaMapper::toDomain).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Delivery> findAllByFilters(Long parcelId, Long routeAssignmentId, Long routeStopId, DeliveryStatus status) {
        return repository.findAllByFilters(parcelId, routeAssignmentId, routeStopId, status).stream()
                .map(DeliveryJpaMapper::toDomain)
                .toList();
    }
}
