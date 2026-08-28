package co.fastshipping.model.user.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class InvalidMailException extends DomainException {

    public InvalidMailException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "INVALID_MAIL_EXCEPTION";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.VALIDATION;
    }
}
