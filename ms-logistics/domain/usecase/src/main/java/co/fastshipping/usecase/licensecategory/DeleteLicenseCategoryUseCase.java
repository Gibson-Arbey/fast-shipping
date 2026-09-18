package co.fastshipping.usecase.licensecategory;

import co.fastshipping.model.licensecategory.exception.LicenseCategoryNotFoundException;
import co.fastshipping.model.licensecategory.gateways.LicenseCategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteLicenseCategoryUseCase {

    private final LicenseCategoryRepository licenseCategoryRepository;

    public void execute(String code) {
        var category = licenseCategoryRepository.findByCode(code);
        if (category == null) {
            throw new LicenseCategoryNotFoundException("License category not found: " + code);
        }

        licenseCategoryRepository.delete(category);
    }
}
