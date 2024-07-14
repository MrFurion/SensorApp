package by.trubetcki.sensorapp.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public static final String DESCRIPTION = "description";

    private final Map<Class<? extends Exception>, Function<Exception, ProblemDetail>> exceptionHandlers = new HashMap<>();

    public GlobalExceptionHandler() {
        exceptionHandlers.put(BadCredentialsException.class, this::handleBadCredentialsException);
        exceptionHandlers.put(AccountStatusException.class, this::handleAccountStatusException);
        exceptionHandlers.put(AccessDeniedException.class, this::handleAccessDeniedException);
        exceptionHandlers.put(SignatureException.class, this::handleSignatureException);
        exceptionHandlers.put(ExpiredJwtException.class, this::handleExpiredJwtException);
        exceptionHandlers.put(SensorNotFoundException.class, this::handleSensorNotFoundException);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        exception.printStackTrace();
        return exceptionHandlers.getOrDefault(exception.getClass(), this::handleUnknownException).apply(exception);
    }

    private ProblemDetail handleBadCredentialsException(Exception exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
        errorDetail.setProperty(DESCRIPTION, "The username or password is incorrect");
        return errorDetail;
    }

    private ProblemDetail handleAccountStatusException(Exception exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        errorDetail.setProperty(DESCRIPTION, "The account is locked");
        return errorDetail;
    }

    private ProblemDetail handleAccessDeniedException(Exception exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        errorDetail.setProperty(DESCRIPTION, "You are not authorized to access this resource");
        return errorDetail;
    }

    private ProblemDetail handleSignatureException(Exception exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        errorDetail.setProperty(DESCRIPTION, "The JWT signature is invalid");
        return errorDetail;
    }

    private ProblemDetail handleExpiredJwtException(Exception exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        errorDetail.setProperty(DESCRIPTION, "The JWT token has expired");
        return errorDetail;
    }

    private ProblemDetail handleSensorNotFoundException(Exception exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        errorDetail.setProperty(DESCRIPTION, "Sensor not found");
        return errorDetail;
    }

    private ProblemDetail handleUnknownException(Exception exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        errorDetail.setProperty(DESCRIPTION, "Unknown internal server error.");
        return errorDetail;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed");
        errorDetail.setProperty("errors", errors);
        errorDetail.setProperty(DESCRIPTION, "Validation failed for one or more fields");

        return errorDetail;
    }
}
