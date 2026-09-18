package co.fastshipping.api.driver.mapper;

import co.fastshipping.api.driver.response.DriverResponse;
import co.fastshipping.api.licensecategory.mapper.LicenseCategoryResponseMapper;
import co.fastshipping.api.licensecategory.response.LicenseCategoryResponse;
import co.fastshipping.model.driver.Driver;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class DriverResponseMapper {

    private DriverResponseMapper() {
    }

    public static DriverResponse toResponse(Driver driver) {
        if (driver == null) {
            return null;
        }

        Set<LicenseCategoryResponse> categories = driver
                .getLicenseCategories()
                .stream()
                .map(LicenseCategoryResponseMapper::toResponse)
                .collect(Collectors.toCollection(java.util.LinkedHashSet::new));

        return new DriverResponse(
                driver.getId(),
                driver.getUserId(),
                driver.getLicenseNumber(),
                categories,
                driver.getStatus().name()
        );
    }

    public static List<DriverResponse> toResponse(List<Driver> drivers) {
        return drivers.stream()
                .map(DriverResponseMapper::toResponse)
                .collect(Collectors.toList());
    }
}
