package edu.ithaca.goosewillis.icook.commands;

import java.util.List;

public abstract class Command {

    private String commandName;

    public Command(String commandName) {
        this.commandName = commandName;
    }

    public abstract boolean execute(String fullInput);

    public String getCommandName() {
        return commandName;
    }

}
