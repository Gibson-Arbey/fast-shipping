package co.fastshipping.model.routeassignment.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class RouteAssignmentNotFoundException extends DomainException {
    public RouteAssignmentNotFoundException(String message) { super(message); }
    @Override public String getCode() { return "ROUTE_ASSIGNMENT_NOT_FOUND"; }
    @Override public ErrorTypeEnum getErrorType() { return ErrorTypeEnum.NOT_FOUND; }
}
