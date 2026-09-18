package co.fastshipping.model.driver.gateways;

import co.fastshipping.model.driver.Driver;
import co.fastshipping.model.driver.DriverStatus;

import java.util.List;

public interface DriverRepository {

    Driver save(Driver driver);

    List<Driver> findAllByFilters(Long userId, DriverStatus status, String licenseNumber, String licenseCategoryCode);

    Driver findById(Long id);

    void updateStatus(Long id, DriverStatus status);

    boolean existsByUserId(Long userId);

    boolean existsByLicenseNumber(String licenseNumber);
}
