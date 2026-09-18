package co.fastshipping.api.licensecategory.mapper;

import co.fastshipping.api.licensecategory.request.RegisterLicenseCategoryRequest;
import co.fastshipping.usecase.licensecategory.command.RegisterLicenseCategoryCommand;

public final class LicenseCategoryRequestMapper {

    private LicenseCategoryRequestMapper() {
    }

    public static RegisterLicenseCategoryCommand toCommand(RegisterLicenseCategoryRequest request) {
        if (request == null) {
            return null;
        }

        return new RegisterLicenseCategoryCommand(
                request.code(),
                request.name(),
                request.description()
        );
    }
}
