package co.fastshipping.api.driver.mapper;

import co.fastshipping.api.driver.request.RegisterDriverRequest;
import co.fastshipping.usecase.driver.command.RegisterDriverCommand;
import co.fastshipping.usecase.driver.query.GetDriverQuery;

public final class DriverRequestMapper {

    private DriverRequestMapper() {
    }

    public static RegisterDriverCommand toCommand(RegisterDriverRequest request) {
        if (request == null) {
            return null;
        }

        return new RegisterDriverCommand(
                request.userId(),
                request.licenseNumber(),
                request.licenseCategories()
        );
    }

    public static GetDriverQuery toQuery(
            Long userId,
            String status,
            String licenseNumber,
            String licenseCategoryCode
    ) {
        return new GetDriverQuery(userId, status, licenseNumber, licenseCategoryCode);
    }
}
