package net.vinograd.newlookatjava.api.exception.errors;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int userId) {
        super("User with id: " + userId + " has not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

}