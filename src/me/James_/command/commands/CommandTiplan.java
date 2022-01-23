package me.James_.command.commands;

import me.James_.Tiplan;
import me.James_.command.Command;
import me.James_.util.ChatType;
import me.James_.util.ClientUtil;

public class CommandTiplan extends Command {

	public CommandTiplan(String[] commands) {
		super(commands);
	}

	@Override
	public void onCmd(String[] args) {
		ClientUtil.sendChatMessage("Changing tiplan...", ChatType.INFO);
		new Tiplan();
	}
}
