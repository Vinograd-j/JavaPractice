package net.vinograd.newlookatjava.console.handlers;

import net.vinograd.newlookatjava.console.CommandType;
import net.vinograd.newlookatjava.console.handlers.abstr.CommandExecutor;
import net.vinograd.newlookatjava.model.User;
import net.vinograd.newlookatjava.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UserCreateCommand implements CommandExecutor {

    private final Scanner scanner;

    private final UserService userService;

    public UserCreateCommand(Scanner scanner, UserService userService) {
        this.scanner = scanner;
        this.userService = userService;
    }

    @Override
    public void execute() {
        System.out.println("Provide a login for new user");
        String login = scanner.nextLine();

        if (userService.getAllUsers().stream().anyMatch(user -> user.getLogin().equals(login)))
            throw new IllegalArgumentException("Provided login is taken. Try another one");

        System.out.println("Enter a password");
        String password = scanner.nextLine();

        User newUser = userService.createNewUser(login, password);

        System.out.printf("User has been created %s%n", newUser.getLogin());
    }

    @Override
    public CommandType getCommandType() {
        return CommandType.USER_CREATE;
    }

}