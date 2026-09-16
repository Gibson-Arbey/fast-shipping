package co.fastshipping.jpa.adapter;

import co.fastshipping.model.vehicle.gateways.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VehicleJpaAdapter implements VehicleRepository {
}
