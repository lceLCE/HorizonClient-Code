package me.James_.command.commands;

import org.lwjgl.input.Keyboard;

import me.James_.Client;
import me.James_.command.Command;
import me.James_.mod.Mod;
import me.James_.mod.ModManager;
import me.James_.ui.ClientNotification;
import me.James_.util.ClientUtil;

public class CommandBind extends Command {
	public CommandBind(String[] command) {
        super(command);
        this.setArgs("-bind <mod> <key>");
    }
	
	@Override
    public void onCmd(String[] args) {
		if(args.length < 3) {
			ClientUtil.sendClientMessage(this.getArgs(), ClientNotification.Type.WARNING);
		} else {
			String mod = args[1];
			int key = Keyboard.getKeyIndex((String)args[2].toUpperCase());
			for (Mod m : ModManager.modList) {
				if (!m.getName().equalsIgnoreCase(mod)) continue;
				m.setKey(key);
                ClientUtil.sendClientMessage(String.valueOf(m.getName()) + " was bound to " + Keyboard.getKeyName((int)key), Keyboard.getKeyName((int)key).equals("NONE") ? ClientNotification.Type.ERROR : ClientNotification.Type.SUCCESS);
                Client.instance.fileMgr.saveKeys();
                return;
			}
			ClientUtil.sendClientMessage("Cannot find Module : " + mod, ClientNotification.Type.ERROR);
		}
	}
}
