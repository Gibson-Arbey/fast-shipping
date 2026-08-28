package co.fastshipping.model.user.valueobject;

import co.fastshipping.model.user.exception.InvalidMailException;

public class Email {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private final String value;

    public Email(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidMailException("Email must not be empty");
        }
        if (!value.matches(EMAIL_REGEX)) {
            throw new InvalidMailException("Invalid email format");
        }
        this.value = value;
    }

    public String value() {
        return value;
    }
}
