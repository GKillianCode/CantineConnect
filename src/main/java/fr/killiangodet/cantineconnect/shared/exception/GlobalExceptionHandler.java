package fr.killiangodet.cantineconnect.shared.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import fr.killiangodet.cantineconnect.shared.application.exception.ApplicationException;
import fr.killiangodet.cantineconnect.shared.domain.exception.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApplicationException.class)
    public ProblemDetail handleApplicationException(ApplicationException ex, HandlerMethod handlerMethod) {
        String moduleName = extractModuleName(handlerMethod);
        log.warn("[MODULE: {}] Application/resource error : {}", moduleName, ex.getMessage());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.NOT_FOUND,
            ex.getMessage()
        );
        problem.setTitle("Resource Not Found");
        problem.setProperty("module", moduleName);
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    @ExceptionHandler(DomainException.class)
    public ProblemDetail handleDomainException(DomainException ex, HandlerMethod handlerMethod) {
        String moduleName = extractModuleName(handlerMethod);
        log.warn("[MODULE: {}] Business rule violation : {}", moduleName, ex.getMessage());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.UNPROCESSABLE_CONTENT,
            ex.getMessage()
        );
        problem.setTitle("Business Rule Violation");
        problem.setProperty("module", moduleName);
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HandlerMethod handlerMethod) {
        String moduleName = extractModuleName(handlerMethod);

        List<Map<String, String>> violations = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> Map.of(
                "field", error.getField(),
                "message", error.getDefaultMessage() != null ? error.getDefaultMessage() : "Invalide"
            ))
            .toList();

        log.warn("[MODULE: {}] Validation errors in request for {} field(s)", moduleName, violations.size());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "Request validation failed."
        );
        problem.setTitle("Validation Failed");
        problem.setProperty("module", moduleName);
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("violations", violations);
        return problem;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HandlerMethod handlerMethod) {
        String moduleName = extractModuleName(handlerMethod);
        String detailMessage = "Malformed JSON request or invalid field format.";

        if (ex.getCause() instanceof InvalidFormatException invalidFormatEx) {
            Class<?> targetType = invalidFormatEx.getTargetType();

            if (targetType != null && targetType.isEnum()) {
                String fieldName = invalidFormatEx.getPath().isEmpty() ? "unknown" : invalidFormatEx.getPath().getLast().getFieldName();
                String invalidValue = invalidFormatEx.getValue().toString();
                String acceptedValues = Arrays.toString(targetType.getEnumConstants());

                detailMessage = String.format("Invalid value '%s' for field '%s'. Accepted values : %s",
                    invalidValue, fieldName, acceptedValues);
            }
        }

        log.warn("[MODULE: {}] HTTP / JSON reading error : {}", moduleName, detailMessage);

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            detailMessage
        );
        problem.setTitle("Invalid Request Payload");
        problem.setProperty("module", moduleName);
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnhandledException(Exception ex, HandlerMethod handlerMethod) {
        String moduleName = extractModuleName(handlerMethod);
        log.error("[MODULE: {}] Unhandled exception : {}", moduleName, ex.getMessage(), ex);

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected internal error occurred."
        );
        problem.setTitle("Internal Server Error");
        problem.setProperty("module", moduleName);
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    private String extractModuleName(HandlerMethod handlerMethod) {
        if (handlerMethod == null) {
            return "shared";
        }
        String packageName = handlerMethod.getBeanType().getPackageName();
        String[] parts = packageName.split("\\.");
        return (parts.length > 3) ? parts[3] : "shared";
    }
}
