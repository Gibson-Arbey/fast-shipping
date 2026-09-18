package co.fastshipping.model.licensecategory.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class LicenseCategoryAlreadyExistsException extends DomainException {

    public LicenseCategoryAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "LICENSE_CATEGORY_ALREADY_EXISTS";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.CONFLICT;
    }
}
