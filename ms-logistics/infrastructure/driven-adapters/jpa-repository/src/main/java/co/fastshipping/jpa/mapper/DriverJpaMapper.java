package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.DriverJpaEntity;
import co.fastshipping.jpa.entity.DriverLicenseCategoryJpaEntity;
import co.fastshipping.model.driver.Driver;
import co.fastshipping.model.licensecategory.LicenseCategory;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class DriverJpaMapper {

    private DriverJpaMapper() {
    }

    public static DriverJpaEntity toEntity(Driver driver) {
        if (driver == null) {
            return null;
        }

        DriverJpaEntity entity = DriverJpaEntity.builder()
                .id(driver.getId())
                .userId(driver.getUserId())
                .licenseNumber(driver.getLicenseNumber())
                .status(driver.getStatus())
                .licenseCategories(new HashSet<>())
                .build();

        for (LicenseCategory category : driver.getLicenseCategories()) {
            entity.getLicenseCategories().add(
                    DriverLicenseCategoryJpaEntity.builder()
                            .driver(entity)
                            .licenseCategory(LicenseCategoryJpaMapper.toEntity(category))
                            .build()
            );
        }

        return entity;
    }

    public static Driver toDomain(DriverJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        Set<LicenseCategory> categories = entity.getLicenseCategories()
                .stream()
                .map(DriverLicenseCategoryJpaEntity::getLicenseCategory)
                .map(LicenseCategoryJpaMapper::toDomain)
                .collect(Collectors.toCollection(java.util.LinkedHashSet::new));

        return Driver.restore(
                entity.getId(),
                entity.getUserId(),
                entity.getLicenseNumber(),
                categories,
                entity.getStatus()
        );
    }
}
