package me.James_.mod.mods.COMBAT;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventPacket;
import me.James_.events.EventUpdate;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Mod {
	
	public Value<String> mode = new Value("Velocity", "Mode", 0);
	public Value<Double> verti = new Value("Velocity_Vertical", 1d, 0d, 1d, 0.01d);
	public Value<Double> hori = new Value("Velocity_Horizontal", 1d, 0d, 1d, 0.01d);
	
	public Velocity() {
		super("Velocity", Category.COMBAT);
		this.mode.mode.add("Normal");
		this.mode.mode.add("AACNormal");
		this.mode.mode.add("AACReverse");
	}
	
	@Override
	public void onDisable() {
        mc.thePlayer.motionX *= 1.0;
        mc.thePlayer.motionZ *= 1.0;
        mc.thePlayer.speedInAir = 0.02f;
        super.onDisable();
	}
	
	@EventTarget
	public void onReceivePacket(EventPacket event) {
		if(this.mode.isCurrentMode("Normal")) {
			this.setDisplayName("Normal");
			if(event.packet instanceof S12PacketEntityVelocity) {
				S12PacketEntityVelocity packet = (S12PacketEntityVelocity)event.packet;
				if(this.verti.getValueState().intValue() == 0 && this.hori.getValueState().intValue() == 0) {
					event.setCancelled(true);
				} else {
					packet.setMotionX(packet.getMotionX() * this.hori.getValueState().intValue());
					packet.setMotionY(packet.getMotionY() * this.verti.getValueState().intValue());
					packet.setMotionZ(packet.getMotionZ() * this.hori.getValueState().intValue());
				}
			}
			if(event.packet instanceof S27PacketExplosion) {
				S27PacketExplosion exp = (S27PacketExplosion)event.packet;
				if(this.verti.getValueState().intValue() == 0 && this.hori.getValueState().intValue() == 0) {
					event.setCancelled(true);
				} else {
					exp.setX(exp.getPosX() * (float)this.hori.getValueState().intValue());
					exp.setY(exp.getPosY() * (float)this.verti.getValueState().intValue());
					exp.setZ(exp.getPosZ() * (float)this.hori.getValueState().intValue());
				}
			}
		}
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(this.mode.isCurrentMode("AACNormal")) {
			this.setDisplayName("AAC Normal");
			if (this.mc.thePlayer.hurtTime > 0) {
				mc.thePlayer.motionX *= 0.6;
                mc.thePlayer.motionZ *= 0.6;
            } else {
            	mc.thePlayer.motionX *= 1.0;
                mc.thePlayer.motionZ *= 1.0;
            }
		}
		if(this.mode.isCurrentMode("AACReverse")) {
			this.setDisplayName("AAC Reverse");
			if (this.mc.thePlayer.hurtTime > 0) {
				mc.thePlayer.motionX *= 0.9;
				mc.thePlayer.motionZ *= 0.9;
                mc.thePlayer.speedInAir = 0.05f;
            } else {
                mc.thePlayer.motionX *= 1.0;
                mc.thePlayer.motionZ *= 1.0;
                mc.thePlayer.motionY *= 1.0;
                mc.thePlayer.speedInAir = 0.02f;
            }
		}
	}

}
