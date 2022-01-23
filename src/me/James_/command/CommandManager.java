package me.James_.command;

import java.util.ArrayList;

import me.James_.command.commands.CommandBind;
import me.James_.command.commands.CommandBlock;
import me.James_.command.commands.CommandTiplan;
import me.James_.command.commands.CommandToggle;
import me.James_.command.commands.CommandVersion;
import me.James_.command.commands.CommandWdr;

public class CommandManager {
	private static ArrayList<Command> commands = new ArrayList();
	
	public CommandManager() {
		add(new CommandBind(new String[] {"bind"}));
		add(new CommandVersion(new String[] {"version", "v"}));
		add(new CommandToggle(new String[] {"toggle", "t"}));
		add(new CommandWdr(new String[] {"wdr"}));
		add(new CommandBlock(new String[] {"block"}));
		add(new CommandTiplan(new String[] {"tiplan"}));
	}
	
	public void add(Command c) {
		this.commands.add(c);
	}
	
	public static ArrayList<Command> getCommands() {
        return commands;
    }
	
	public static String removeSpaces(String message) {
        String space = " ";
        String doubleSpace = "  ";
        while (message.contains(doubleSpace)) {
            message = message.replace(doubleSpace, space);
        }
        return message;
    }
}
