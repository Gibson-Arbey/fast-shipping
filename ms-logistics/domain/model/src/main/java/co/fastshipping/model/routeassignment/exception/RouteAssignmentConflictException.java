package co.fastshipping.model.routeassignment.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class RouteAssignmentConflictException extends DomainException {
    public RouteAssignmentConflictException(String message) { super(message); }
    @Override public String getCode() { return "ROUTE_ASSIGNMENT_CONFLICT"; }
    @Override public ErrorTypeEnum getErrorType() { return ErrorTypeEnum.CONFLICT; }
}
