package net.vinograd.newlookatjava.service;

import net.vinograd.newlookatjava.model.Account;
import net.vinograd.newlookatjava.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService {

    private final Map<Integer, Account> accounts;

    private int idCounter;

    private final int defaultAccountAmount;
    private final double commission;

    public AccountService(@Value("${account.default-amount}") int defaultAccountAmount, @Value("${account.default.commission}") double commission) {
        this.accounts = new HashMap<>();
        this.idCounter = 0;
        this.defaultAccountAmount = defaultAccountAmount;
        this.commission = commission;
    }

    public Account createNewAccount(User user){
        Account account = new Account(++idCounter, user.getId(), defaultAccountAmount);
        this.accounts.put(idCounter, account);

        return account;
    }

    public void withDrawMoney(Account account, int amount){
        if (account.getMoneyAmount() - (amount + amount * commission) < 0.0)
            throw new IllegalArgumentException("Not enough money! Required: %f but account only has: %f".formatted(amount + amount * commission, account.getMoneyAmount()));

        account.withdrawMoney(amount + amount * commission);
    }

    public void addMoney(Account account, int amount){
        account.addMoney(amount);
    }

    public Optional<Account> findAccountById(int id){
        return Optional.ofNullable(this.accounts.get(id));
    }

    public List<Account> getAllAccounts(){
        return this.accounts.values().stream().toList();
    }

    public void closeAccount(int accountId){
        this.accounts.remove(accountId);
    }

}