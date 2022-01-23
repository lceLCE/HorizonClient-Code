package me.James_.mod.mods.RENDER;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.events.EventUpdate;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FullBright extends Mod {
	
	public FullBright() {
		super("FullBright", Category.RENDER);
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 5200, 1));
	}
	
	@Override
	public void onDisable() {
		this.mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
		super.onDisable();
	}
}
