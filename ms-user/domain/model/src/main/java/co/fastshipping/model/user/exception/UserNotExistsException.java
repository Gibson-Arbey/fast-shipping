package co.fastshipping.model.user.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class UserNotExistsException extends DomainException {
    public UserNotExistsException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "USER_NOT_EXISTS";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.NOT_FOUND;
    }
}
