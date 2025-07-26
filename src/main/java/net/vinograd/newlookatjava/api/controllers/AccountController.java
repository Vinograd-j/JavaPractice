package net.vinograd.newlookatjava.api.controllers;

import net.vinograd.newlookatjava.api.exception.errors.AccountNotFoundException;
import net.vinograd.newlookatjava.api.exception.errors.UserNotFoundException;
import net.vinograd.newlookatjava.model.Account;
import net.vinograd.newlookatjava.model.User;
import net.vinograd.newlookatjava.service.AccountService;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private final AccountService accountService;

    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostMapping("/accounts/create/{userId}")
    public void createAccount(@PathVariable Integer userId){
        this.accountService.createNewAccount(userService.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId)));
    }

    @PostMapping("/accounts/close/{userId}/{accountId}")
    public void closeAccount(@PathVariable Integer userId, @PathVariable Integer accountId){
        User user = this.userService.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Account account = this.accountService.findAccountById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));

        this.userService.removeAccount(user, account);
    }

    @PostMapping("/accounts/deposit/{accountId}/{amount}")
    public void depositMoney(@PathVariable Integer accountId, @PathVariable Double amount){
        Account account = this.accountService.findAccountById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
        this.accountService.addMoney(account, amount);
    }

    @PostMapping("/accounts/transfer/{senderId}/{receiverId}/{amount}")
    public void transferMoney(@PathVariable Integer senderId, @PathVariable Integer receiverId, @PathVariable Double amount){
        Account sender = this.accountService.findAccountById(senderId).orElseThrow(() -> new AccountNotFoundException(senderId));
        Account receiver = this.accountService.findAccountById(receiverId).orElseThrow(() -> new AccountNotFoundException(receiverId));

        this.accountService.transferMoney(sender, receiver, amount);
    }

    @PostMapping("/accounts/withdraw/{accountId}/{amount}")
    public void withdrawMoney(@PathVariable Integer accountId, @PathVariable Double amount){
        Account account = this.accountService.findAccountById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));

        this.accountService.withDrawMoney(account, amount);
    }

}