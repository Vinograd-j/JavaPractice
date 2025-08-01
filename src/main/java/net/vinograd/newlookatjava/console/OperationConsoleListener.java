package net.vinograd.newlookatjava.console;

import net.vinograd.newlookatjava.console.handlers.abstr.CommandExecutor;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;


public class OperationConsoleListener {

    private final Scanner scanner;

    private final Map<CommandType, CommandExecutor> commands;

    public OperationConsoleListener(Scanner scanner, Map<CommandType, CommandExecutor> commands) {
        this.scanner = scanner;
        this.commands = commands;
    }

    public void listenToCommands(){
        System.out.println("Input a command. Available ones:");
        showCommandList();

        while (true){
            if (!scanner.hasNextLine())
                continue;

            String command = scanner.nextLine();
            if (command.isEmpty()) continue;

            try {
                executeCommand(CommandType.valueOf(command));
            }catch (IllegalArgumentException e){
                System.out.println("You cant input command with does not exists. Try again");
            }

            System.out.println("Input a command. Available ones:");
            showCommandList();
        }
    }

    private void executeCommand(CommandType commandType){
        CommandExecutor commandExecutor = commands.get(commandType);

        try {
            commandExecutor.execute();
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    private void showCommandList(){
        Arrays.stream(CommandType.values()).toList().forEach(System.out::println);
    }

}