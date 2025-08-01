package net.vinograd.newlookatjava.console.handlers;

import net.vinograd.newlookatjava.console.CommandType;
import net.vinograd.newlookatjava.console.handlers.abstr.CommandExecutor;
import net.vinograd.newlookatjava.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountDepositCommand implements CommandExecutor {

    private final Scanner scanner;

    private final AccountService accountService;

    public AccountDepositCommand(Scanner scanner, AccountService accountService) {
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        System.out.println("Input account id");
        int accountId = scanner.nextInt();

        System.out.println("Input amount of money");
        int amount = scanner.nextInt();

        scanner.nextLine();

        this.accountService.findAccountById(accountId).ifPresentOrElse(account -> {
            accountService.addMoney(account, amount);
            System.out.println("Money has been credited");
        }, () -> {
            throw new IllegalArgumentException("There is no user with this id");
        });
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ACCOUNT_DEPOSIT;
    }

}
