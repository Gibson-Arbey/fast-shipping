package co.fastshipping.model.incident.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class IncidentNotFoundException extends DomainException {
    public IncidentNotFoundException(String message) { super(message); }
    @Override public String getCode() { return "INCIDENT_NOT_FOUND"; }
    @Override public ErrorTypeEnum getErrorType() { return ErrorTypeEnum.NOT_FOUND; }
}
