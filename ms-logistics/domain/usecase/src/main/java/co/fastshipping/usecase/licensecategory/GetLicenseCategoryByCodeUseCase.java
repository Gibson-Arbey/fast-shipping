package co.fastshipping.usecase.licensecategory;

import co.fastshipping.model.licensecategory.LicenseCategory;
import co.fastshipping.model.licensecategory.exception.LicenseCategoryNotFoundException;
import co.fastshipping.model.licensecategory.gateways.LicenseCategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetLicenseCategoryByCodeUseCase {

    private final LicenseCategoryRepository licenseCategoryRepository;

    public LicenseCategory execute(String code) {
        LicenseCategory category = licenseCategoryRepository.findByCode(code);

        if (category == null) {
            throw new LicenseCategoryNotFoundException(
                    "License category not found: " + code
            );
        }

        return category;
    }
}
