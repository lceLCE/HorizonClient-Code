package me.James_.mod.mods.COMBAT;

import me.James_.Value;
import me.James_.mod.Category;
import me.James_.mod.Mod;

public class Reach extends Mod {
	
	public static Value<Double> range = new Value("Reach_Range", 4.5d, 3d, 10.0d, 0.1d);
	
	public Reach() {
		super("Reach", Category.COMBAT);
	}

}
