package co.fastshipping.usecase.routeassignment;

import co.fastshipping.model.driver.Driver;
import co.fastshipping.model.driver.DriverStatus;
import co.fastshipping.model.driver.gateways.DriverRepository;
import co.fastshipping.model.licensecategory.LicenseCategory;
import co.fastshipping.model.route.Route;
import co.fastshipping.model.route.RouteStatus;
import co.fastshipping.model.route.RouteStop;
import co.fastshipping.model.route.gateways.RouteRepository;
import co.fastshipping.model.routeassignment.RouteAssignment;
import co.fastshipping.model.routeassignment.RouteAssignmentStatus;
import co.fastshipping.model.routeassignment.gateways.RouteAssignmentRepository;
import co.fastshipping.model.vehicle.Vehicle;
import co.fastshipping.model.vehicle.VehicleStatus;
import co.fastshipping.model.vehicle.VehicleType;
import co.fastshipping.model.vehicle.gateways.VehicleRepository;
import co.fastshipping.usecase.routeassignment.command.CreateRouteAssignmentCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteAssignmentUseCaseTest {
    @Mock RouteRepository routeRepository;
    @Mock DriverRepository driverRepository;
    @Mock VehicleRepository vehicleRepository;
    @Mock RouteAssignmentRepository assignmentRepository;
    @InjectMocks CreateRouteAssignmentUseCase createUseCase;

    @Test
    void createsAssignmentAndReservesDriverAndVehicle() {
        Route route = Route.restore(1L, "Route", Set.of(
                RouteStop.restore(10L, 1L, 1, "A"),
                RouteStop.restore(11L, 1L, 2, "B")
        ), RouteStatus.ACTIVE);
        Driver driver = Driver.restore(2L, 20L, "LIC", Set.of(LicenseCategory.create("B", "B", null)), DriverStatus.AVAILABLE);
        Vehicle vehicle = Vehicle.restore(3L, VehicleStatus.AVAILABLE, VehicleType.VAN, BigDecimal.TEN, BigDecimal.TEN, "ABC");

        when(routeRepository.findById(1L)).thenReturn(route);
        when(driverRepository.findById(2L)).thenReturn(driver);
        when(vehicleRepository.findById(3L)).thenReturn(vehicle);
        when(assignmentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        RouteAssignment result = createUseCase.execute(new CreateRouteAssignmentCommand(1L, 2L, 3L));

        assertEquals(RouteAssignmentStatus.PLANNED, result.getStatus());
        ArgumentCaptor<Driver> driverCaptor = ArgumentCaptor.forClass(Driver.class);
        ArgumentCaptor<Vehicle> vehicleCaptor = ArgumentCaptor.forClass(Vehicle.class);
        verify(driverRepository).save(driverCaptor.capture());
        verify(vehicleRepository).save(vehicleCaptor.capture());
        assertEquals(DriverStatus.ASSIGNED, driverCaptor.getValue().getStatus());
        assertEquals(VehicleStatus.ASSIGNED, vehicleCaptor.getValue().getStatus());
    }
}
