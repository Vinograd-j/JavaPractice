package net.vinograd.newlookatjava.console.hendlers;

import net.vinograd.newlookatjava.console.CommandType;
import net.vinograd.newlookatjava.console.hendlers.abstr.CommandExecutor;
import net.vinograd.newlookatjava.service.AccountService;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountDepositCommand implements CommandExecutor {

    private final Scanner scanner;

    private final AccountService accountService;

    private final UserService userService;

    public AccountDepositCommand(Scanner scanner, AccountService accountService, UserService userService) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.userService = userService;
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
