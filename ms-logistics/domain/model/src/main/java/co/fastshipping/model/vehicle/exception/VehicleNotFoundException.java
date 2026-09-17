package co.fastshipping.model.vehicle.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class VehicleNotFoundException extends DomainException {
    public VehicleNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "VEHICLE_NOT_FOUND";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.NOT_FOUND;
    }
}
