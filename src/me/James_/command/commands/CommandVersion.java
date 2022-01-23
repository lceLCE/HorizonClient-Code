package me.James_.command.commands;

import me.James_.Client;
import me.James_.command.Command;
import me.James_.ui.ClientNotification;
import me.James_.util.ClientUtil;

public class CommandVersion extends Command {

	public CommandVersion(String[] commands) {
		super(commands);
	}

	@Override
	public void onCmd(String[] args) {
		ClientUtil.sendClientMessage(Client.CLIENT_name + " " + Client.CLIENT_VER + " by James_", ClientNotification.Type.INFO);
	}
	
}
