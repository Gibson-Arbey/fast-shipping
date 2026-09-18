package co.fastshipping.model.licensecategory.gateways;

import co.fastshipping.model.licensecategory.LicenseCategory;

import java.util.List;

public interface LicenseCategoryRepository {

    LicenseCategory save(LicenseCategory licenseCategory);

    List<LicenseCategory> findAll();

    LicenseCategory findByCode(String code);

    void delete(LicenseCategory licenseCategory);
}
