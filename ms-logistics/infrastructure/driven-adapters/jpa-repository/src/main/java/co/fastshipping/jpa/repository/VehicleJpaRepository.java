package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.VehicleJpaEntity;
import co.fastshipping.model.vehicle.VehicleStatus;
import co.fastshipping.model.vehicle.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface VehicleJpaRepository extends JpaRepository<VehicleJpaEntity, Long> {

    @Query("""
        SELECT v
        FROM VehicleJpaEntity v
        WHERE (:status IS NULL OR v.status = :status)
          AND (:type IS NULL OR v.type = :type)
          AND (:minWeightCapacity IS NULL OR v.maxWeight >= :minWeightCapacity)
          AND (:maxWeightCapacity IS NULL OR v.maxWeight <= :maxWeightCapacity)
          AND (:minVolumeCapacity IS NULL OR v.maxVolume >= :minVolumeCapacity)
          AND (:maxVolumeCapacity IS NULL OR v.maxVolume <= :maxVolumeCapacity)
          AND (
                :plate IS NULL
                OR :plate = ''
                OR UPPER(v.plate) LIKE UPPER(CONCAT('%', :plate, '%'))
              )
        """)
    List<VehicleJpaEntity> findAllByFilters(
        @Param("status") VehicleStatus status,
        @Param("type") VehicleType type,
        @Param("minWeightCapacity") BigDecimal minWeightCapacity,
        @Param("maxWeightCapacity") BigDecimal maxWeightCapacity,
        @Param("minVolumeCapacity") BigDecimal minVolumeCapacity,
        @Param("maxVolumeCapacity") BigDecimal maxVolumeCapacity,
        @Param("plate") String plate
    );

    @Modifying
    @Query("UPDATE VehicleJpaEntity SET status = :status WHERE id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") VehicleStatus status);

    boolean existsByPlateIgnoreCase(String plate);
}
