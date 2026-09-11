package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.ParcelHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelHistoryJpaRepository extends JpaRepository<ParcelHistoryJpaEntity, Long> {
}
