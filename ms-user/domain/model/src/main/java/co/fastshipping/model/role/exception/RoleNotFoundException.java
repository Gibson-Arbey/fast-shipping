package co.fastshipping.model.role.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class RoleNotFoundException extends DomainException {
    public RoleNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "ROLE_NOT_FOUND";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.NOT_FOUND;
    }
}
