package co.fastshipping.model.exception;

public abstract class InfrastructureException extends RuntimeException {

    protected InfrastructureException(String message) {
        super(message);
    }

    public abstract String getCode();

    public abstract ErrorTypeEnum getErrorType();
}