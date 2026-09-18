package co.fastshipping.api.driver.response;

import co.fastshipping.api.licensecategory.response.LicenseCategoryResponse;

import java.util.Set;

public record DriverResponse(
        Long id,
        Long userId,
        String licenseNumber,
        Set<LicenseCategoryResponse> licenseCategories,
        String status
) {
}
