package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.DriverJpaEntity;
import co.fastshipping.model.driver.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriverJpaRepository extends JpaRepository<DriverJpaEntity, Long> {

    @Query("""
        SELECT DISTINCT d
        FROM DriverJpaEntity d
        LEFT JOIN d.licenseCategories driverLicenseCategory
        LEFT JOIN driverLicenseCategory.licenseCategory licenseCategory
        WHERE (:userId IS NULL OR d.userId = :userId)
          AND (:status IS NULL OR d.status = :status)
          AND (
                :licenseNumber IS NULL
                OR :licenseNumber = ''
                OR UPPER(d.licenseNumber) LIKE UPPER(CONCAT('%', :licenseNumber, '%'))
              )
          AND (
                :licenseCategoryCode IS NULL
                OR :licenseCategoryCode = ''
                OR UPPER(licenseCategory.code) = UPPER(:licenseCategoryCode)
              )
        ORDER BY d.id
        """)
    List<DriverJpaEntity> findAllByFilters(
            @Param("userId") Long userId,
            @Param("status") DriverStatus status,
            @Param("licenseNumber") String licenseNumber,
            @Param("licenseCategoryCode") String licenseCategoryCode
    );

    @Modifying
    @Query("UPDATE DriverJpaEntity SET status = :status WHERE id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") DriverStatus status);

    boolean existsByUserId(Long userId);

    boolean existsByLicenseNumberIgnoreCase(String licenseNumber);
}
