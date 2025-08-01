package net.vinograd.newlookatjava.console.handlers.abstr;

import net.vinograd.newlookatjava.console.CommandType;

public interface CommandExecutor {

    void execute();

    CommandType getCommandType();

}
