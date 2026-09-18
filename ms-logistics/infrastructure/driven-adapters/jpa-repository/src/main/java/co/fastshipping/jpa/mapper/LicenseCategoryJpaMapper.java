package co.fastshipping.jpa.mapper;

import co.fastshipping.jpa.entity.LicenseCategoryJpaEntity;
import co.fastshipping.model.licensecategory.LicenseCategory;

public final class LicenseCategoryJpaMapper {

    private LicenseCategoryJpaMapper() {
    }

    public static LicenseCategoryJpaEntity toEntity(LicenseCategory category) {
        if (category == null) {
            return null;
        }

        return LicenseCategoryJpaEntity.builder()
                .id(category.getId())
                .code(category.getCode())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public static LicenseCategory toDomain(LicenseCategoryJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return LicenseCategory.restore(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription()
        );
    }
}
