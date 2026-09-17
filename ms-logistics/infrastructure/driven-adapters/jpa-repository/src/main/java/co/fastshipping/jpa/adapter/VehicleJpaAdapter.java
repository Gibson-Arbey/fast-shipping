package co.fastshipping.jpa.adapter;

import co.fastshipping.jpa.mapper.VehicleJpaMapper;
import co.fastshipping.jpa.repository.VehicleJpaRepository;
import co.fastshipping.model.vehicle.Vehicle;
import co.fastshipping.model.vehicle.VehicleStatus;
import co.fastshipping.model.vehicle.VehicleType;
import co.fastshipping.model.vehicle.gateways.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class VehicleJpaAdapter implements VehicleRepository {

    private final VehicleJpaRepository vehicleJpaRepository;


    @Override
    @Transactional
    public Vehicle save(Vehicle vehicle) {
        return VehicleJpaMapper.toDomain(vehicleJpaRepository.save(VehicleJpaMapper.toEntity(vehicle)));
    }

    @Override
    public List<Vehicle> findAllByFilters(VehicleStatus status, VehicleType type, BigDecimal minWeightCapacity, BigDecimal maxWeightCapacity, BigDecimal minVolumeCapacity, BigDecimal maxVolumeCapacity, String plate) {
        return vehicleJpaRepository.findAllByFilters(
                status, type, minWeightCapacity, maxWeightCapacity, minVolumeCapacity, maxVolumeCapacity, plate
        ).stream().map(VehicleJpaMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateStatus(Long id, VehicleStatus status) {
        vehicleJpaRepository.updateStatus(id, status);
    }

    @Override
    public boolean existsById(Long id) {
        return vehicleJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByPlate(String plate) {
        return vehicleJpaRepository.existsByPlateIgnoreCase(plate);
    }
}
