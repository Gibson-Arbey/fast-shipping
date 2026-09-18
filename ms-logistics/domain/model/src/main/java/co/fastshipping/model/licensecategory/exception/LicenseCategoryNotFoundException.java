package co.fastshipping.model.licensecategory.exception;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;

public class LicenseCategoryNotFoundException extends DomainException {

    public LicenseCategoryNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "LICENSE_CATEGORY_NOT_FOUND";
    }

    @Override
    public ErrorTypeEnum getErrorType() {
        return ErrorTypeEnum.NOT_FOUND;
    }
}
