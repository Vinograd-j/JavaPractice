package net.vinograd.newlookatjava.api.controllers;

import jakarta.validation.Valid;
import net.vinograd.newlookatjava.api.dtos.DepositDTO;
import net.vinograd.newlookatjava.api.dtos.TransferDTO;
import net.vinograd.newlookatjava.api.dtos.WithdrawDTO;
import net.vinograd.newlookatjava.api.exception.errors.AccountNotFoundException;
import net.vinograd.newlookatjava.api.exception.errors.UserNotFoundException;
import net.vinograd.newlookatjava.model.Account;
import net.vinograd.newlookatjava.service.AccountService;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Account createAccount(@PathVariable Integer userId){
        return this.accountService.createNewAccount(userService.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId)));
    }

    @PostMapping("/accounts/close/{accountId}")
    public void closeAccount(@PathVariable Integer accountId){
        Account account = this.accountService.findAccountById(accountId).
                orElseThrow(() -> new AccountNotFoundException(accountId));

        this.userService.removeAccount(account);
    }

    @PostMapping("/accounts/deposit")
    public void depositMoney(@Valid @RequestBody DepositDTO depositDTO){
        Account account = this.accountService.findAccountById(depositDTO.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(depositDTO.getAccountId()));

        this.accountService.addMoney(account, depositDTO.getAmount());
    }

    @PostMapping("/accounts/transfer")
    public void transferMoney(@Valid @RequestBody TransferDTO transferDTO){
        Account sender = this.accountService.findAccountById(transferDTO.getSenderAccountId())
                .orElseThrow(() -> new AccountNotFoundException(transferDTO.getSenderAccountId()));

        Account receiver = this.accountService.findAccountById(transferDTO.getReceiverAccountId())
                .orElseThrow(() -> new AccountNotFoundException(transferDTO.getReceiverAccountId()));

        this.accountService.transferMoney(sender, receiver, transferDTO.getAmount());
    }

    @PostMapping("/accounts/withdraw")
    public void withdrawMoney(@Valid @RequestBody WithdrawDTO withdrawDTO){
        Account account = this.accountService.findAccountById(withdrawDTO.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(withdrawDTO.getAccountId()));

        this.accountService.withdrawMoney(account, withdrawDTO.getAmount());
    }

}