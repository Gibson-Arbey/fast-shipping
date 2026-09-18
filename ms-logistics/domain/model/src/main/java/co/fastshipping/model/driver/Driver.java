package co.fastshipping.model.driver;

import co.fastshipping.model.licensecategory.LicenseCategory;
import co.fastshipping.model.exception.InvalidFieldException;
import lombok.Getter;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class Driver {

    private final Long id;

    private final Long userId;

    private final String licenseNumber;

    private final Set<LicenseCategory> licenseCategories;

    private final DriverStatus status;

    private Driver(Long id, Long userId, String licenseNumber, Set<LicenseCategory> licenseCategories, DriverStatus status) {
        if (userId == null || userId <= 0) throw new InvalidFieldException("userId must be greater than zero");
        if (licenseNumber == null || licenseNumber.isBlank()) throw new InvalidFieldException("licenseNumber cannot be blank");
        if (licenseCategories == null || licenseCategories.isEmpty() || licenseCategories.contains(null)) {
            throw new InvalidFieldException("licenseCategories cannot be empty");
        }
        if (status == null) throw new InvalidFieldException("status cannot be null");

        this.id = id;
        this.userId = userId;
        this.licenseNumber = licenseNumber;
        this.licenseCategories = Collections.unmodifiableSet(new LinkedHashSet<>(licenseCategories));
        this.status = status;
    }

    public static Driver create(Long userId, String licenseNumber, Set<LicenseCategory> licenseCategories) {
        return new Driver(null, userId, licenseNumber, licenseCategories, DriverStatus.AVAILABLE);
    }

    public static Driver create(Long userId, String licenseNumber, Set<LicenseCategory> licenseCategories, DriverStatus status) {
        return new Driver(null, userId, licenseNumber, licenseCategories, status);
    }

    public static Driver restore(Long id, Long userId, String licenseNumber, Set<LicenseCategory> licenseCategories, DriverStatus status) {
        return new Driver(id, userId, licenseNumber, licenseCategories, status);
    }

    /**
     * Kept as an alias for callers that used the initial model API.
     */
    public static Driver create(Long id, Long userId, String licenseNumber, Set<LicenseCategory> licenseCategories, DriverStatus status) {
        return restore(id, userId, licenseNumber, licenseCategories, status);
    }
}
