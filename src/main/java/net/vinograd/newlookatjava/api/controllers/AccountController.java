package net.vinograd.newlookatjava.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.vinograd.newlookatjava.api.dtos.DepositDTO;
import net.vinograd.newlookatjava.api.dtos.TransferDTO;
import net.vinograd.newlookatjava.api.dtos.WithdrawDTO;
import net.vinograd.newlookatjava.api.exception.errors.AccountNotFoundException;
import net.vinograd.newlookatjava.api.exception.errors.UserNotFoundException;
import net.vinograd.newlookatjava.api.security.dtos.UserDetails;
import net.vinograd.newlookatjava.model.Account;
import net.vinograd.newlookatjava.model.Transaction;
import net.vinograd.newlookatjava.service.AccountService;
import net.vinograd.newlookatjava.service.TransactionService;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    private final UserService userService;

    private final TransactionService transactionService;

    @PostMapping("/create/{userId}")
    public Account createAccount(@PathVariable Integer userId){
        return this.accountService.createNewAccount(userService.findUserById(userId).orElseThrow(() -> new UserNotFoundException(userId)));
    }

    @PostMapping("/close/{accountId}")
    public void closeAccount(@PathVariable Integer accountId){
        Account account = this.accountService.findAccountById(accountId).
                orElseThrow(() -> new AccountNotFoundException(accountId));

        this.userService.removeAccount(account);
    }

    @PostMapping("/deposit")
    public void depositMoney(@Valid @RequestBody DepositDTO depositDTO){
        Account account = this.accountService.findAccountById(depositDTO.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(depositDTO.getAccountId()));

        this.accountService.addMoney(account, depositDTO.getAmount());
    }

    @PostMapping("/transfer")
    public void transferMoney(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody TransferDTO transferDTO) throws IllegalAccessException {
        Account sender = this.accountService.findAccountById(transferDTO.getSenderAccountId())
                .orElseThrow(() -> new AccountNotFoundException(transferDTO.getSenderAccountId()));

        if (sender.getUser().getId() != userDetails.getId())
            throw new IllegalAccessException("You dont own account with provided sender id!");

        Account receiver = this.accountService.findAccountById(transferDTO.getReceiverAccountId())
                .orElseThrow(() -> new AccountNotFoundException(transferDTO.getReceiverAccountId()));

        this.accountService.transferMoney(sender, receiver, transferDTO.getAmount());
    }

    @PostMapping("/withdraw")
    public void withdrawMoney(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody WithdrawDTO withdrawDTO) throws IllegalAccessException {
        Account account = this.accountService.findAccountById(withdrawDTO.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(withdrawDTO.getAccountId()));

        if (account.getUser().getId() == userDetails.getId())
            this.accountService.withdrawMoney(account, withdrawDTO.getAmount());
        else
            throw new IllegalAccessException("You cant withdraw money because this is not your account");
    }

    @GetMapping("/operations/{accountId}")
    public List<Transaction> allTransactions(
             @AuthenticationPrincipal UserDetails userDetails,
             @PathVariable Integer accountId) throws IllegalAccessException {
        Account account = this.accountService.findAccountById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));

        if (account.getUser().getId() != userDetails.getId())
            throw new IllegalAccessException("You cant see someone else's account!");

        return this.transactionService.getAllAccountTransactions(accountId);
    }

    @GetMapping("/operations/period/{accountId}")
    public List<Transaction> allTransactionsPeriod(
             @AuthenticationPrincipal UserDetails userDetails,
             @PathVariable Integer accountId,
             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) throws IllegalAccessException {

        Account account = this.accountService.findAccountById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));

        if (account.getUser().getId() != userDetails.getId())
            throw new IllegalAccessException("You cant see someone else's account!");

        return this.transactionService.getAllAccountTransactionsPeriod(accountId, from, to);
    }

}