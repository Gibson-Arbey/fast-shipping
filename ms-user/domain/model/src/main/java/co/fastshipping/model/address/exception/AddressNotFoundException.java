package co.fastshipping.model.address.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class AddressNotFoundException extends DomainException {
    public AddressNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "ADDRESS_NOT_FOUND";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.NOT_FOUND;
    }
}
