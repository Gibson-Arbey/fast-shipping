package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.ParcelJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelJpaRepository extends JpaRepository<ParcelJpaEntity,Integer> {
}
