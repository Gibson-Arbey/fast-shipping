package co.fastshipping.model.parcel.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class ParcelNotFoundException extends DomainException {
    public ParcelNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "PARCEL_NOT_FOUND";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.NOT_FOUND;
    }
}
