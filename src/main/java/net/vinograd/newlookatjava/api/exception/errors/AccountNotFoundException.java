package net.vinograd.newlookatjava.api.exception.errors;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(int accountId) {
        super("Account with id: " + accountId + " has not found");
    }

}
