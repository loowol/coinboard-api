package dev.coinboard.api.error;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import dev.coinboard.api.coin.CoinNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory
            .getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(CoinNotFoundException.class)
    public ProblemDetail handleCoinNotFound(CoinNotFoundException ex) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Coin not found");
        problem.setProperty("coinId", ex.coinId());
        return problem;
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ParameterValidationResult result : ex
                .getParameterValidationResults()) {
            for (MessageSourceResolvable error : result.getResolvableErrors()) {
                errors.add(result.getMethodParameter().getParameterName() + ": "
                        + error.getDefaultMessage());
            }
        }
        ex.getBody().setProperty("errors", errors);
        return super.handleHandlerMethodValidationException(ex, headers, status,
                request);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception ex) {
        log.error("Unhandled Exception", ex);
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong.");
    }
}