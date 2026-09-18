package co.fastshipping.usecase.driver;

import co.fastshipping.model.driver.Driver;
import co.fastshipping.model.driver.exception.DriverNotFoundException;
import co.fastshipping.model.driver.gateways.DriverRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetDriverByIdUseCase {

    private final DriverRepository driverRepository;

    public Driver execute(Long id) {
        Driver driver = driverRepository.findById(id);
        if (driver == null) {
            throw new DriverNotFoundException("Driver not found: " + id);
        }
        return driver;
    }
}
