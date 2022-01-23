package me.James_.command.commands;

import me.James_.command.Command;
import me.James_.mod.Mod;
import me.James_.mod.ModManager;
import me.James_.ui.ClientNotification;
import me.James_.util.ClientUtil;

public class CommandToggle extends Command {

	public CommandToggle(String[] commands) {
		super(commands);
		this.setArgs("-toggle <module>");
	}

	@Override
	public void onCmd(String[] args) {
		if(args.length < 2) {
			ClientUtil.sendClientMessage(this.getArgs(), ClientNotification.Type.WARNING);
		} else {
			String mod = args[1];
			for (Mod m : ModManager.getModList()) {
				if(m.getName().equalsIgnoreCase(mod)) {
					m.set(m.isEnabled());
					ClientUtil.sendClientMessage("Module " + m.getName() + " toggled", ClientNotification.Type.SUCCESS);
					return;
				}
			}
		}
	}
}
