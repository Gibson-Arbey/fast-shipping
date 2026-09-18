package co.fastshipping.usecase.driver.query;

import co.fastshipping.model.driver.DriverStatus;
import co.fastshipping.model.exception.InvalidFieldException;

public record GetDriverQuery(
        Long userId,
        DriverStatus status,
        String licenseNumber,
        String licenseCategoryCode
) {

    public GetDriverQuery(
            Long userId,
            String status,
            String licenseNumber,
            String licenseCategoryCode
    ) {
        this(
                userId,
                DriverStatus.fromString(status),
                normalize(licenseNumber),
                normalize(licenseCategoryCode)
        );
        validatePositive(userId, "userId");
    }

    public static GetDriverQuery all() {
        return new GetDriverQuery(null, (DriverStatus) null, null, null);
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private static void validatePositive(Long value, String field) {
        if (value != null && value <= 0) {
            throw new InvalidFieldException(field + " must be greater than zero");
        }
    }
}
