package co.fastshipping.usecase.driver;

import co.fastshipping.model.driver.DriverStatus;
import co.fastshipping.model.driver.exception.DriverNotFoundException;
import co.fastshipping.model.driver.gateways.DriverRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateStatusDriverUseCase {

    private final DriverRepository driverRepository;

    public void execute(Long id, String status) {
        if (driverRepository.findById(id) == null) {
            throw new DriverNotFoundException("Driver not found: " + id);
        }

        driverRepository.updateStatus(id, DriverStatus.fromString(status));
    }
}
