package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.RouteAssignmentJpaEntity;
import co.fastshipping.model.routeassignment.RouteAssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteAssignmentJpaRepository extends JpaRepository<RouteAssignmentJpaEntity, Long> {
    @Query("""
            SELECT a FROM RouteAssignmentJpaEntity a
            WHERE (:routeId IS NULL OR a.routeId = :routeId)
              AND (:driverId IS NULL OR a.driverId = :driverId)
              AND (:vehicleId IS NULL OR a.vehicleId = :vehicleId)
              AND (:status IS NULL OR a.status = :status)
            ORDER BY a.id
            """)
    List<RouteAssignmentJpaEntity> findAllByFilters(
            @Param("routeId") Long routeId,
            @Param("driverId") Long driverId,
            @Param("vehicleId") Long vehicleId,
            @Param("status") RouteAssignmentStatus status
    );
}
