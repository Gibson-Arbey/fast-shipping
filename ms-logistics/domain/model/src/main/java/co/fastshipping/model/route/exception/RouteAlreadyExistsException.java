package co.fastshipping.model.route.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class RouteAlreadyExistsException extends DomainException {

    public RouteAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "ROUTE_ALREADY_EXISTS";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.CONFLICT;
    }
}
