package net.vinograd.newlookatjava.console.handlers;

import net.vinograd.newlookatjava.console.CommandType;
import net.vinograd.newlookatjava.console.handlers.abstr.CommandExecutor;
import net.vinograd.newlookatjava.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountWithdrawCommand implements CommandExecutor {

    private final Scanner scanner;

    private final AccountService accountService;

    public AccountWithdrawCommand(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        System.out.println("Input account id");
        int accountId = scanner.nextInt();

        System.out.println("Input amount of money you want to withdraw");
        int amount = scanner.nextInt();

        this.accountService.findAccountById(accountId).ifPresentOrElse(account -> {
            accountService.withdrawMoney(account, amount);
            System.out.printf("Amount withdrawn. Current balance: %f%n", account.getMoneyAmount());
        }, () -> {
            throw new IllegalArgumentException("There is no user with this id");
        });
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ACCOUNT_WITHDRAW;
    }

}
