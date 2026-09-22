package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.RouteJpaEntity;
import co.fastshipping.model.route.RouteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteJpaRepository extends JpaRepository<RouteJpaEntity, Long> {

    @Query("""
        SELECT DISTINCT r
        FROM RouteJpaEntity r
        LEFT JOIN FETCH r.stops
        WHERE (:status IS NULL OR r.status = :status)
          AND (
                :name IS NULL
                OR :name = ''
                OR UPPER(r.name) LIKE UPPER(CONCAT('%', :name, '%'))
              )
        ORDER BY r.id
        """)
    List<RouteJpaEntity> findAllByFilters(
            @Param("status") RouteStatus status,
            @Param("name") String name
    );

    @Modifying
    @Query("UPDATE RouteJpaEntity SET status = :status WHERE id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") RouteStatus status);

    boolean existsByNameIgnoreCase(String name);
}
