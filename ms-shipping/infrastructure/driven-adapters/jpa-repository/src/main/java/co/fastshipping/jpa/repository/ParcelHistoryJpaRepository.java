package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.ParcelHistoryJpaEntity;
import co.fastshipping.model.parcel.ParcelStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ParcelHistoryJpaRepository extends JpaRepository<ParcelHistoryJpaEntity, Long> {

    @Query("SELECT p FROM ParcelHistoryJpaEntity p WHERE p.parcel.id = :parcelId AND p.createdAt BETWEEN :fromDate AND :toDate AND (:applyStatus = FALSE OR p.status = :status) ")
    List<ParcelHistoryJpaEntity> findAllByParcelId(
            @Param("parcelId") Long parcelId,
            @Param("fromDate")LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            @Param("applyStatus") Boolean applyStatus,
            @Param("status") ParcelStatus status);
}
