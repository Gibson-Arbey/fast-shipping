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
        if (licenseCategories == null || licenseCategories.isEmpty() || licenseCategories.stream().anyMatch(java.util.Objects::isNull)) {
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

    public Driver assign() {
        requireStatus(DriverStatus.AVAILABLE, "Driver can only be assigned when available");
        return withStatus(DriverStatus.ASSIGNED);
    }

    public Driver startDriving() {
        requireStatus(DriverStatus.ASSIGNED, "Driver can only start driving after being assigned");
        return withStatus(DriverStatus.DRIVING);
    }

    public Driver release() {
        if (status != DriverStatus.ASSIGNED && status != DriverStatus.DRIVING) {
            throw new InvalidFieldException("Driver can only be released when assigned or driving");
        }
        return withStatus(DriverStatus.AVAILABLE);
    }

    public Driver changeStatus(DriverStatus newStatus) {
        if (newStatus == null) {
            throw new InvalidFieldException("status cannot be null");
        }
        return withStatus(newStatus);
    }

    private Driver withStatus(DriverStatus newStatus) {
        return new Driver(id, userId, licenseNumber, licenseCategories, newStatus);
    }

    private void requireStatus(DriverStatus expected, String message) {
        if (status != expected) {
            throw new InvalidFieldException(message);
        }
    }
}
