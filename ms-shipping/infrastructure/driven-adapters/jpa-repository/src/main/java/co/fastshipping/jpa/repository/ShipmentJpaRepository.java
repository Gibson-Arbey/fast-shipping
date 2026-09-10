package co.fastshipping.jpa.repository;

import co.fastshipping.model.shipmenthistory.ShipmentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentJpaRepository extends JpaRepository<ShipmentHistory,Long> {
}
