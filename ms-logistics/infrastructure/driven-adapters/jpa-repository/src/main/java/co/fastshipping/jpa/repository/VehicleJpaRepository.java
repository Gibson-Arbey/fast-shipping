package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.VehicleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleJpaRepository extends JpaRepository<VehicleJpaEntity, Long> {
}
