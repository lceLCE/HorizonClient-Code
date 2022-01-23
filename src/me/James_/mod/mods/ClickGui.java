package me.James_.mod.mods;

import org.lwjgl.input.Keyboard;

import me.James_.Client;
import me.James_.mod.Category;
import me.James_.mod.Mod;

public class ClickGui extends Mod {
	
	public ClickGui() {
		super("ClickGui", Category.RENDER);
		this.setKey(Keyboard.KEY_RSHIFT);
	}
	
	@Override
	public void onEnable() {
		this.mc.displayGuiScreen(Client.instance.showClickGui());
		this.set(false);
	}

}
