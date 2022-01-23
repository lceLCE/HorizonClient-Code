package me.James_.mod.mods.MOVEMENT;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.events.EventUpdate;
import me.James_.mod.Category;
import me.James_.mod.Mod;

public class Sprint extends Mod {
	
	public Sprint() {
		super("Sprint", Category.MOVEMENT);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(mc.thePlayer.moveForward > 0.0f && mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
			mc.thePlayer.setSprinting(true);
		}
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.setSprinting(false);
		super.onDisable();
	}
}
