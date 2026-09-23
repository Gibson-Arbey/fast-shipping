package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.IncidentJpaEntity;
import co.fastshipping.model.incident.IncidentStatus;
import co.fastshipping.model.incident.IncidentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IncidentJpaRepository extends JpaRepository<IncidentJpaEntity, Long> {
    @Query("""
            SELECT i FROM IncidentJpaEntity i
            WHERE (:routeAssignmentId IS NULL OR i.routeAssignmentId = :routeAssignmentId)
              AND (:deliveryId IS NULL OR i.deliveryId = :deliveryId)
              AND (:type IS NULL OR i.type = :type)
              AND (:status IS NULL OR i.status = :status)
            ORDER BY i.id
            """)
    List<IncidentJpaEntity> findAllByFilters(
            @Param("routeAssignmentId") Long routeAssignmentId,
            @Param("deliveryId") Long deliveryId,
            @Param("type") IncidentType type,
            @Param("status") IncidentStatus status
    );
}
