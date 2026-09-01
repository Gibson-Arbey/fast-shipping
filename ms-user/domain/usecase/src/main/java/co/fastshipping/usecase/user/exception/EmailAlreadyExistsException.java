package co.fastshipping.usecase.user.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class EmailAlreadyExistsException extends DomainException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "EMAIL_ALREADY_EXISTS";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.VALIDATION;
    }
}
