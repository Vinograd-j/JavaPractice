package net.vinograd.newlookatjava.console.hendlers.abstr;

import net.vinograd.newlookatjava.console.CommandType;

public interface CommandExecutor {

    void execute();

    CommandType getCommandType();

}
