package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.ShipmentJpaMapper;
import co.fastshipping.jpa.repository.ShipmentJpaRepository;
import co.fastshipping.model.shipment.Shipment;
import co.fastshipping.model.shipment.gateways.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ShipmentJpaAdapter implements ShipmentRepository {

    private final ShipmentJpaRepository shipmentJpaRepository;

    @Override
    @Transactional
    public Shipment save(Shipment shipment) {
        return ShipmentJpaMapper.toDomain(
                shipmentJpaRepository.save(ShipmentJpaMapper.toJpaEntity(shipment))
        );
    }
}
