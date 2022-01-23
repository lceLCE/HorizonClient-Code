package me.James_.mod.mods.MISC;

import me.James_.Value;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.mod.ModManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SpeedMine extends Mod {
	public static Value mode = new Value("SpeedMine", "Mode", 0);
	
	public SpeedMine() {
		super("SpeedMine", Category.MISC);
		mode.mode.add("Potion");
	}
	
	@Override
	public void onEnable() {
		if(this.mode.isCurrentMode("Potion")) {
			 boolean item = false;
			Minecraft.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 100, item ? 2 : 0));
		}
		super.onEnable();
	}
	   public void onDisable() {
		      super.onDisable();
		      mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
		   }
}

