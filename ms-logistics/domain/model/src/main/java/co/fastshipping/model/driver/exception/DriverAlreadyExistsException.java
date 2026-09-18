package co.fastshipping.model.driver.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class DriverAlreadyExistsException extends DomainException {

    public DriverAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "DRIVER_ALREADY_EXISTS";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.CONFLICT;
    }
}
