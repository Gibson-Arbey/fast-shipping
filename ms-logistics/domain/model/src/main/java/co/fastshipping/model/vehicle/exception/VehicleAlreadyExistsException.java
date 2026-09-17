package co.fastshipping.model.vehicle.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class VehicleAlreadyExistsException extends DomainException {
    public VehicleAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "VEHICLE_ALREADY_EXISTS";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.CONFLICT;
    }
}
