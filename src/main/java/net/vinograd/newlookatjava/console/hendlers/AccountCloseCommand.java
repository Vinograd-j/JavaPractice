package net.vinograd.newlookatjava.console.hendlers;

import net.vinograd.newlookatjava.console.CommandType;
import net.vinograd.newlookatjava.console.hendlers.abstr.CommandExecutor;
import net.vinograd.newlookatjava.model.Account;
import net.vinograd.newlookatjava.model.User;
import net.vinograd.newlookatjava.service.AccountService;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountCloseCommand implements CommandExecutor {

    private final Scanner scanner;

    private final AccountService accountService;
    private final UserService userService;

    public AccountCloseCommand(Scanner scanner, AccountService accountService, UserService userService) {
        this.scanner = scanner;
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void execute() {
        System.out.println("Input account id you want to close");
        int id = scanner.nextInt();

        Account account = accountService.findAccountById(id).orElseThrow(() -> new IllegalArgumentException("This account does not exists"));

        User user = userService.findUserById(account.getUserId()).orElseThrow(() -> new IllegalArgumentException("This account is not kept by any user!"));

        user.removeAccount(account);
        accountService.closeAccount(account.getId());
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.ACCOUNT_CLOSE;
    }
}
