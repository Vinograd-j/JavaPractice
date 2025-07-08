package net.vinograd.newlookatjava.console.hendlers;

import net.vinograd.newlookatjava.console.CommandType;
import net.vinograd.newlookatjava.console.hendlers.abstr.CommandExecutor;
import net.vinograd.newlookatjava.service.AccountService;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountCreateCommand implements CommandExecutor {

    private final Scanner scanner;

    private final UserService userService;

    private final AccountService accountService;

    public AccountCreateCommand(UserService userService, Scanner scanner, AccountService accountService) {
        this.userService = userService;
        this.scanner = scanner;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        System.out.println("Input userId");
        int id = scanner.nextInt();

        userService.findUserById(id).ifPresentOrElse(user -> {
            user.addAccount(accountService.createNewAccount(user));
            System.out.printf("Account with id = %d has been created! %n", user.getId());
        }, () -> {
            throw new IllegalArgumentException("There is no user with this id");
        });
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ACCOUNT_CREATE;
    }

}