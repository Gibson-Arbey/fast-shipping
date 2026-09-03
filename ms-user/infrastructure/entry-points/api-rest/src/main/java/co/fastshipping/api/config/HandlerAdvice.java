package co.fastshipping.api.config;

import co.fastshipping.model.exception.DomainException;
import co.fastshipping.model.exception.ErrorTypeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class HandlerAdvice {

    @ExceptionHandler(DomainException.class)
    public ProblemDetail handleDomainException(
            DomainException ex,
            HttpServletRequest request
    ) {
        return buildProblemDetail(
                ex.getCode(),
                ex.getErrorType(),
                ex.getMessage(),
                request
        );
    }

    private ProblemDetail buildProblemDetail(
            String code,
            ErrorTypeEnum errorType,
            String message,
            HttpServletRequest request
    ) {

        HttpStatus status = getHttpStatus(errorType);

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(
                        status,
                        message
                );

        problemDetail.setTitle(errorType.name());
        problemDetail.setProperty("code", code);
        problemDetail.setProperty("path", request.getRequestURI());

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage() != null ? ex.getMessage() : "Unexpected internal server error"
        );

        problemDetail.setTitle("INTERNAL_SERVER_ERROR");
        problemDetail.setProperty("path", request.getRequestURI());

        log.error("Internal error", ex);

        return problemDetail;
    }


    private HttpStatus getHttpStatus(ErrorTypeEnum errorType) {
        return switch (errorType) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case VALIDATION -> HttpStatus.BAD_REQUEST;
            case CONFLICT -> HttpStatus.CONFLICT;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case INTERNAL_SERVER_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
