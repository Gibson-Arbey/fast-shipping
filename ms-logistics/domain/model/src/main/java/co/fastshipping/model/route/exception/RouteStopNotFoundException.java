package co.fastshipping.model.route.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class RouteStopNotFoundException extends DomainException {
    public RouteStopNotFoundException(String message) { super(message); }
    @Override public String getCode() { return "ROUTE_STOP_NOT_FOUND"; }
    @Override public ErrorTypeEnum getErrorType() { return ErrorTypeEnum.NOT_FOUND; }
}
