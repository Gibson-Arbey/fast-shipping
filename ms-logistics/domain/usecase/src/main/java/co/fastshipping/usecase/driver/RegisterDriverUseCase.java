package co.fastshipping.usecase.driver;

import co.fastshipping.model.driver.Driver;
import co.fastshipping.model.driver.exception.DriverAlreadyExistsException;
import co.fastshipping.model.driver.gateways.DriverRepository;
import co.fastshipping.model.licensecategory.LicenseCategory;
import co.fastshipping.model.licensecategory.exception.LicenseCategoryNotFoundException;
import co.fastshipping.model.licensecategory.gateways.LicenseCategoryRepository;
import co.fastshipping.usecase.driver.command.RegisterDriverCommand;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

@RequiredArgsConstructor
public class RegisterDriverUseCase {

    private final DriverRepository driverRepository;
    private final LicenseCategoryRepository licenseCategoryRepository;

    public Driver execute(RegisterDriverCommand command) {
        if (driverRepository.existsByUserId(command.userId())) {
            throw new DriverAlreadyExistsException("A driver for user " + command.userId() + " already exists");
        }

        if (driverRepository.existsByLicenseNumber(command.licenseNumber())) {
            throw new DriverAlreadyExistsException("A driver with license number " + command.licenseNumber() + " already exists");
        }

        return driverRepository.save(
                Driver.create(
                        command.userId(),
                        command.licenseNumber(),
                        resolveLicenseCategories(command.licenseCategories())
                )
        );
    }

    private Set<LicenseCategory> resolveLicenseCategories(Set<String> codes) {
        var categoriesById = new LinkedHashMap<Long, LicenseCategory>();
        var categoriesWithoutId = new LinkedHashSet<LicenseCategory>();

        for (String code : codes) {
            LicenseCategory category = licenseCategoryRepository.findByCode(code);
            if (category == null) {
                throw new LicenseCategoryNotFoundException("License category not found: " + code);
            }

            if (category.getId() == null) {
                categoriesWithoutId.add(category);
            } else {
                categoriesById.put(category.getId(), category);
            }
        }

        var categories = new LinkedHashSet<>(categoriesById.values());
        categories.addAll(categoriesWithoutId);
        return categories;
    }
}
