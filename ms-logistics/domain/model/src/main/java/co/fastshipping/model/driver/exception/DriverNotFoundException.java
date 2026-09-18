package co.fastshipping.model.driver.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class DriverNotFoundException extends DomainException {

    public DriverNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "DRIVER_NOT_FOUND";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.NOT_FOUND;
    }
}
