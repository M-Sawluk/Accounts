package com.michals.interview.controller.advice;

import com.michals.interview.exception.AccountNotFound;
import com.michals.interview.exception.CurrencyAccountNotFound;
import com.michals.interview.exception.InsufficientFundsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler({InsufficientFundsException.class})
    public ResponseEntity<Object> handleInsufficientFundsException(InsufficientFundsException exception) {
        return ResponseEntity.badRequest()
                .header("message", exception.getMessage())
                .build();
    }

    @ExceptionHandler({CurrencyAccountNotFound.class})
    public ResponseEntity<Object> handleCurrencyAccountNotFound(CurrencyAccountNotFound exception) {
        return ResponseEntity.badRequest()
                .header("message", exception.getMessage())
                .build();
    }

    @ExceptionHandler({AccountNotFound.class})
    public ResponseEntity<Object> handleAccountNotFound(AccountNotFound exception) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleAccountNotFound(ConstraintViolationException exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleAccountNotFound(MethodArgumentNotValidException exception) {
        String errors = exception.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body(errors);
    }

}
