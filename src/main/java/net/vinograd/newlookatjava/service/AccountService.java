package net.vinograd.newlookatjava.service;

import jakarta.transaction.Transactional;
import net.vinograd.newlookatjava.api.exception.errors.NotEnoughMoney;
import net.vinograd.newlookatjava.model.Account;
import net.vinograd.newlookatjava.model.User;
import net.vinograd.newlookatjava.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final int defaultAccountAmount;
    private final double commission;

    public AccountService(@Value("${account.default-amount}") int defaultAccountAmount, @Value("${account.default.commission}") double commission, AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.defaultAccountAmount = defaultAccountAmount;
        this.commission = commission;
    }

    @Transactional
    public Account createNewAccount(User user){
        Account account = new Account(user, defaultAccountAmount);
        this.accountRepository.save(account);

        return account;
    }

    @Transactional
    public void withdrawMoney(Account account, double amount){
        if (account.getMoneyAmount() - (amount + amount * commission) < 0.0)
            throw new NotEnoughMoney(account.getId());

        account.withdrawMoney(amount + amount * commission);
        this.accountRepository.save(account);
    }

    @Transactional
    public void addMoney(Account account, double amount){
        account.addMoney(amount);
        this.accountRepository.save(account);
    }

    @Transactional
    public void transferMoney(Account sender, Account receiver, Double amount){
        withdrawMoney(sender, amount + amount * commission);
        receiver.addMoney(amount);
    }

    public Optional<Account> findAccountById(int id){
        return this.accountRepository.findById(id);
    }

    public List<Account> getAllAccounts(){
        return this.accountRepository.findAll();
    }

}