package co.fastshipping.model.shipment.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class ShipmentNotFoundException extends DomainException {
    public ShipmentNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "SHIPMENT_NOT_FOUND";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.NOT_FOUND;
    }
}
