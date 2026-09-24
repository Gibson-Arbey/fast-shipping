package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.ShipmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentJpaRepository extends JpaRepository<ShipmentJpaEntity, Long> {
    List<ShipmentJpaEntity> findAllByOrderByIdAsc();
}
