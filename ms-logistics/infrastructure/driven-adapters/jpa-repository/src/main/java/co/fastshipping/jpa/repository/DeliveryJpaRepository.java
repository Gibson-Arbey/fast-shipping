package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.DeliveryJpaEntity;
import co.fastshipping.model.delivery.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryJpaRepository extends JpaRepository<DeliveryJpaEntity, Long> {
    @Query("""
            SELECT d FROM DeliveryJpaEntity d
            WHERE (:parcelId IS NULL OR d.parcelId = :parcelId)
              AND (:routeAssignmentId IS NULL OR d.routeAssignmentId = :routeAssignmentId)
              AND (:routeStopId IS NULL OR d.routeStopId = :routeStopId)
              AND (:status IS NULL OR d.status = :status)
            ORDER BY d.id
            """)
    List<DeliveryJpaEntity> findAllByFilters(
            @Param("parcelId") Long parcelId,
            @Param("routeAssignmentId") Long routeAssignmentId,
            @Param("routeStopId") Long routeStopId,
            @Param("status") DeliveryStatus status
    );
}
