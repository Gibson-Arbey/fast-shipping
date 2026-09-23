package co.fastshipping.model.vehicle.gateways;

import co.fastshipping.model.vehicle.Vehicle;
import co.fastshipping.model.vehicle.VehicleStatus;
import co.fastshipping.model.vehicle.VehicleType;

import java.math.BigDecimal;
import java.util.List;

public interface VehicleRepository {

    Vehicle save(Vehicle vehicle);

    Vehicle findById(Long id);

    List<Vehicle> findAllByFilters(VehicleStatus status, VehicleType type, BigDecimal minWeightCapacity, BigDecimal maxWeightCapacity, BigDecimal minVolumeCapacity, BigDecimal maxVolumeCapacity, String plate);

    void updateStatus(Long id, VehicleStatus status);

    boolean existsById(Long id);

    boolean existsByPlate(String plate);


}
