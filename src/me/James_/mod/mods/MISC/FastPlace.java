package me.James_.mod.mods.MISC;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.events.EventUpdate;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import net.minecraft.item.ItemBlock;

public class FastPlace extends Mod {
	
	public FastPlace() {
		super("FastPlace", Category.MISC);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(mc.thePlayer.inventory.getCurrentItem() != null) {
			if(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
				mc.rightClickDelayTimer = 0;
			} else {
				mc.rightClickDelayTimer = 4;
			}
		}
	}
	
	@Override
	public void onDisable() {
		mc.rightClickDelayTimer = 4;
		super.onDisable();
	}
}
