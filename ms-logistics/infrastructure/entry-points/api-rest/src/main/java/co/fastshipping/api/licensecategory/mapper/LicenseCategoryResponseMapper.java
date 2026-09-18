package co.fastshipping.api.licensecategory.mapper;

import co.fastshipping.api.licensecategory.response.LicenseCategoryResponse;
import co.fastshipping.model.licensecategory.LicenseCategory;

import java.util.List;
import java.util.stream.Collectors;

public final class LicenseCategoryResponseMapper {

    private LicenseCategoryResponseMapper() {
    }

    public static LicenseCategoryResponse toResponse(LicenseCategory category) {
        if (category == null) {
            return null;
        }

        return new LicenseCategoryResponse(
                category.getId(),
                category.getCode(),
                category.getName(),
                category.getDescription()
        );
    }

    public static List<LicenseCategoryResponse> toResponse(List<LicenseCategory> categories) {
        return categories.stream()
                .map(LicenseCategoryResponseMapper::toResponse)
                .collect(Collectors.toList());
    }
}
