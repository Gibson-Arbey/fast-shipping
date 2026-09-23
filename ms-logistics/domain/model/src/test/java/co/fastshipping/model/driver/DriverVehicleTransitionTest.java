package co.fastshipping.model.driver;

import co.fastshipping.model.exception.InvalidFieldException;
import co.fastshipping.model.licensecategory.LicenseCategory;
import co.fastshipping.model.vehicle.Vehicle;
import co.fastshipping.model.vehicle.VehicleStatus;
import co.fastshipping.model.vehicle.VehicleType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DriverVehicleTransitionTest {
    @Test
    void driverLifecycleIsControlledByDomainMethods() {
        Driver driver = Driver.create(1L, "LIC-1", Set.of(LicenseCategory.create("B", "B", null)))
                .assign()
                .startDriving()
                .release();

        assertEquals(DriverStatus.AVAILABLE, driver.getStatus());
    }

    @Test
    void vehicleCannotStartTransitWithoutAssignment() {
        Vehicle vehicle = Vehicle.create(VehicleType.VAN, BigDecimal.TEN, BigDecimal.TEN, "ABC");

        assertEquals(BigDecimal.TEN, vehicle.getMaxWeight());
        assertThrows(InvalidFieldException.class, vehicle::startTransit);
    }

    @Test
    void vehicleLifecycleIsControlledByDomainMethods() {
        Vehicle vehicle = Vehicle.create(VehicleType.VAN, BigDecimal.TEN, BigDecimal.TEN, "ABC")
                .assign()
                .startTransit()
                .release();

        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
    }
}
