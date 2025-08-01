package net.vinograd.newlookatjava.console.handlers;

import net.vinograd.newlookatjava.console.CommandType;
import net.vinograd.newlookatjava.console.handlers.abstr.CommandExecutor;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class ShowAllUsersCommand implements CommandExecutor {

    private final UserService userService;

    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        this.userService.getAllUsers().forEach(user -> {
            System.out.printf("id: %d, login: %s, count of accounts: %d%n", user.getId(), user.getLogin(), user.getAccounts().size());

            user.getAccounts().forEach(account -> System.out.printf("Account: %d Balance: %f%n", account.getId(), account.getMoneyAmount()));
        });
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.SHOW_ALL_USERS;
    }

}
