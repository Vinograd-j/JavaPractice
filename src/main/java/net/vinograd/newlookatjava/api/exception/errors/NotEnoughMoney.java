package net.vinograd.newlookatjava.api.exception.errors;

public class NotEnoughMoney extends RuntimeException {

    public NotEnoughMoney(Integer accountId) {
        super("Account with id " + accountId + " doesnt have enough money to transfer");
    }

}
