package me.James_.mod;

import java.util.ArrayList;

import me.James_.mod.mods.ClickGui;
import me.James_.mod.mods.COMBAT.Antibot;
import me.James_.mod.mods.COMBAT.Criticals;
import me.James_.mod.mods.COMBAT.Hitbox;
import me.James_.mod.mods.COMBAT.KeepSprint;
import me.James_.mod.mods.COMBAT.Killaura;
import me.James_.mod.mods.COMBAT.Reach;
import me.James_.mod.mods.COMBAT.Velocity;
import me.James_.mod.mods.MISC.AutoArmor;
import me.James_.mod.mods.MISC.ModCheck;
import me.James_.mod.mods.MISC.ChestStealer;
import me.James_.mod.mods.MISC.Eagle;
import me.James_.mod.mods.MISC.FastPlace;
import me.James_.mod.mods.MISC.InvCleaner;
import me.James_.mod.mods.MISC.NameProtect;
import me.James_.mod.mods.MISC.SpeedMine;
import me.James_.mod.mods.MISC.Teams;
import me.James_.mod.mods.MOVEMENT.Fly;
import me.James_.mod.mods.MOVEMENT.InvMove;
import me.James_.mod.mods.MOVEMENT.LongJump;
import me.James_.mod.mods.MOVEMENT.NoFall;
import me.James_.mod.mods.MOVEMENT.Safewalk;
import me.James_.mod.mods.MOVEMENT.Scaffold;
import me.James_.mod.mods.MOVEMENT.Scaffold2;
import me.James_.mod.mods.MOVEMENT.Speed;
import me.James_.mod.mods.MOVEMENT.Sprint;
import me.James_.mod.mods.RENDER.Animation;
import me.James_.mod.mods.RENDER.BlockESP;
import me.James_.mod.mods.RENDER.BlockOverlay;
import me.James_.mod.mods.RENDER.ChestESP;
import me.James_.mod.mods.RENDER.Dab;
import me.James_.mod.mods.RENDER.ESP;
import me.James_.mod.mods.RENDER.FullBright;
import me.James_.mod.mods.RENDER.HUD;
import me.James_.mod.mods.RENDER.ItemPhysic;
import me.James_.mod.mods.RENDER.NameTag;
import me.James_.mod.mods.RENDER.Projectiles;

public class ModManager {
	public static ArrayList<Mod> modList = new ArrayList();
    public static ArrayList<Mod> sortedModList = new ArrayList();
    
    public ModManager() {
    	//NOTHING
    	this.addMod(new ClickGui());
    	
    	//COMBAT
    	this.addMod(new Reach());
    	this.addMod(new Hitbox());
    	this.addMod(new Antibot());
    	this.addMod(new Velocity());
    	this.addMod(new Killaura());
    	this.addMod(new Criticals());
    	this.addMod(new KeepSprint());
    	
    	//MOVEMENT
    	this.addMod(new Fly());
    	this.addMod(new Speed());
    	this.addMod(new Sprint());
    	this.addMod(new InvMove());
    	this.addMod(new NoFall());
    	this.addMod(new Safewalk());
    	this.addMod(new Scaffold());
    	this.addMod(new LongJump());
    	this.addMod(new Scaffold2());
    	
    	//RENDER
    	this.addMod(new HUD());
    	this.addMod(new ESP());
    	this.addMod(new Dab());
    	this.addMod(new NameTag());
    	this.addMod(new BlockESP());
    	this.addMod(new ChestESP());
    	this.addMod(new Animation());
    	this.addMod(new FullBright());
    	this.addMod(new ItemPhysic());
    	this.addMod(new Projectiles());
    	this.addMod(new BlockOverlay());

    	//MISC
    	this.addMod(new Eagle());
    	this.addMod(new ModCheck());
    	this.addMod(new Teams());
    	this.addMod(new FastPlace());
    	this.addMod(new AutoArmor());
    	this.addMod(new SpeedMine());
    	this.addMod(new InvCleaner());
    	this.addMod(new NameProtect());
    	this.addMod(new ChestStealer());
    }
    
    public void addMod(Mod m) {
    	modList.add(m);
    }
    
    public ArrayList<Mod> getToggled() {
		ArrayList<Mod> toggled = new ArrayList();
		for(Mod m : this.modList) {
			if(m.isEnabled()) {
				toggled.add(m);
			}
		}
		return toggled;
	}
    
    public static Mod getModByName(String mod) {
        for (Mod m : modList) {
            if (!m.getName().equalsIgnoreCase(mod)) continue;
            return m;
        }
        return null;
    }

	public static ArrayList<Mod> getModList() {
		return modList;
	}
}
