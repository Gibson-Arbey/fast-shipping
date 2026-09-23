package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.ParcelJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParcelJpaRepository extends JpaRepository<ParcelJpaEntity, Long> {

    @Query("SELECT p FROM ParcelJpaEntity p WHERE p.shipment.id = :shipmentId")
    List<ParcelJpaEntity> findAllByShipment(@Param("shipmentId") Long shipmentId);
}
