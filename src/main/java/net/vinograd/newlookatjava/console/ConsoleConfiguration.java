package net.vinograd.newlookatjava.console;

import net.vinograd.newlookatjava.console.handlers.abstr.CommandExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Configuration
public class ConsoleConfiguration {

    @Bean
    public OperationConsoleListener operationConsoleListener(Scanner scanner, List<CommandExecutor> commands) {
        Map<CommandType, CommandExecutor> commandsMap = commands
                        .stream()
                        .collect(Collectors.toMap(
                                CommandExecutor::getCommandType,
                                executor -> executor
                        ));

        return new OperationConsoleListener(scanner, commandsMap);
    }

}