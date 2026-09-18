package co.fastshipping.usecase.driver;

import co.fastshipping.model.driver.Driver;
import co.fastshipping.model.driver.gateways.DriverRepository;
import co.fastshipping.usecase.driver.query.GetDriverQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetDriverUseCase {

    private final DriverRepository driverRepository;

    public List<Driver> execute(GetDriverQuery query) {
        return driverRepository.findAllByFilters(
                query.userId(),
                query.status(),
                query.licenseNumber(),
                query.licenseCategoryCode()
        );
    }

}
