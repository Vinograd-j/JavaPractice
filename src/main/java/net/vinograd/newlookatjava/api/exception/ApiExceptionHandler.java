package net.vinograd.newlookatjava.api.exception;

import net.vinograd.newlookatjava.api.exception.errors.AccountNotFoundException;
import net.vinograd.newlookatjava.api.exception.errors.NotEnoughMoney;
import net.vinograd.newlookatjava.api.exception.errors.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.AbstractMap;
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

}