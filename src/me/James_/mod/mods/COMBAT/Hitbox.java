package me.James_.mod.mods.COMBAT;

import me.James_.Value;
import me.James_.mod.Category;
import me.James_.mod.Mod;

public class Hitbox extends Mod {

	public static Value<Double> size = new Value("Hitbox_Size",0.5d, 0.1d, 1.0d, 0.1d);
	
	public Hitbox() {
		super("Hitbox", Category.COMBAT);
	}

}
