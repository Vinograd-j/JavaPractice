package net.vinograd.newlookatjava.api.exception;

import jakarta.validation.ConstraintViolationException;
import net.vinograd.newlookatjava.api.exception.errors.AccountNotFoundException;
import net.vinograd.newlookatjava.api.exception.errors.NotEnoughMoney;
import net.vinograd.newlookatjava.api.exception.errors.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map.Entry<String, String>> userNotFound(UserNotFoundException exception){
        Map.Entry<String, String> error = new AbstractMap.SimpleEntry<>("message", exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map.Entry<String, String>> accountNotFound(AccountNotFoundException exception){
        Map.Entry<String, String> error = new AbstractMap.SimpleEntry<>("message", exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughMoney.class)
    public ResponseEntity<Map.Entry<String, String>> notEnoughMoney(NotEnoughMoney exception){
        Map.Entry<String, String> error = new AbstractMap.SimpleEntry<>("message", exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolations(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

}