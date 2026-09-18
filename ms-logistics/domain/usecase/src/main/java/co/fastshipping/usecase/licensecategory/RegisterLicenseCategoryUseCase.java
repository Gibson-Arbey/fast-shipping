package co.fastshipping.usecase.licensecategory;

import co.fastshipping.model.licensecategory.LicenseCategory;
import co.fastshipping.model.licensecategory.exception.LicenseCategoryAlreadyExistsException;
import co.fastshipping.model.licensecategory.gateways.LicenseCategoryRepository;
import co.fastshipping.usecase.licensecategory.command.RegisterLicenseCategoryCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterLicenseCategoryUseCase {

    private final LicenseCategoryRepository licenseCategoryRepository;

    public LicenseCategory execute(RegisterLicenseCategoryCommand command) {
        if (licenseCategoryRepository.findByCode(command.code()) != null) {
            throw new LicenseCategoryAlreadyExistsException(
                    "A license category with code " + command.code() + " already exists"
            );
        }

        return licenseCategoryRepository.save(
                LicenseCategory.create(command.code(), command.name(), command.description())
        );
    }
}
