package me.James_.mod.mods.RENDER;

import java.util.ArrayList;

import me.James_.Value;
import me.James_.mod.Category;
import me.James_.mod.Mod;

public class Animation extends Mod {
	
	public static Value<String> mode = new Value("Animation", "Mode", 0);
	public static Value<Boolean> sanimation = new Value("Animation_SwingAnimation", true);
	
	public Animation() {
		super("Animation", Category.RENDER);
		this.mode.mode.add("Push");
		this.mode.mode.add("Sigma");
	}

}
