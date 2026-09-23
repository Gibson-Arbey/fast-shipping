package co.fastshipping.model.delivery.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class DeliveryNotFoundException extends DomainException {
    public DeliveryNotFoundException(String message) { super(message); }
    @Override public String getCode() { return "DELIVERY_NOT_FOUND"; }
    @Override public ErrorTypeEnum getErrorType() { return ErrorTypeEnum.NOT_FOUND; }
}
