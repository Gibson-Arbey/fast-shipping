package co.fastshipping.jpa.repository;

import co.fastshipping.jpa.entity.LicenseCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LicenseCategoryJpaRepository extends JpaRepository<LicenseCategoryJpaEntity, Long> {

    List<LicenseCategoryJpaEntity> findAllByOrderByIdAsc();

    Optional<LicenseCategoryJpaEntity> findByCodeIgnoreCase(String code);
}
