package co.fastshipping.usecase.licensecategory;

import co.fastshipping.model.licensecategory.LicenseCategory;
import co.fastshipping.model.licensecategory.exception.LicenseCategoryNotFoundException;
import co.fastshipping.model.licensecategory.gateways.LicenseCategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetLicenseCategoryUseCase {

    private final LicenseCategoryRepository licenseCategoryRepository;

    public List<LicenseCategory> execute() {
        return licenseCategoryRepository.findAll();
    }

}
