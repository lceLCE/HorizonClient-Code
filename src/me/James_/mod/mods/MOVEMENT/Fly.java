package me.James_.mod.mods.MOVEMENT;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventMove;
import me.James_.events.EventPostMotion;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.util.PlayerUtil;
import me.James_.util.Vec3Util;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Fly extends Mod {
	
	public int counter2 = 0;
	public Value<String> mode = new Value("Fly", "Mode", 0);
	
	public Fly() {
		super("Fly", Category.MOVEMENT);
		this.mode.mode.add("Hypixel");
		this.mode.mode.add("DAC");
	}
	
	@Override
	public void onEnable() {
		if(this.mode.isCurrentMode("Hypixel")) {
			mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.2, mc.thePlayer.posZ);
		}
		super.onEnable();
	}
	
	@EventTarget
	public void onPre(EventPostMotion event) {
		if(this.mode.isCurrentMode("DAC")) {
			this.setDisplayName("DAC");
			if(mc.thePlayer.fallDistance > 3) {
				mc.timer.timerSpeed = 0.1f;
				mc.thePlayer.motionY *= 0.6f;
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
				Vec3 pos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
				Vec3Util vec = new Vec3Util(pos, -mc.thePlayer.rotationYaw, 0.0F, 7);
				mc.thePlayer.setPosition(vec.getEndVector().xCoord, vec.getEndVector().yCoord, vec.getEndVector().zCoord);
				mc.thePlayer.fallDistance = 0;
			} else {
				mc.timer.timerSpeed = 1.0f;
			}
		}
	}
	
	@EventTarget
	public void onMove(EventMove event) {
		if(this.mode.isCurrentMode("Hypixel")) {
			this.setDisplayName("Hypixel " + mc.thePlayer.posY);
			mc.thePlayer.onGround = false;
			mc.thePlayer.capabilities.isFlying = false;
			
			if(mc.gameSettings.keyBindSneak.isKeyDown()) {
				mc.thePlayer.motionY *= 0.0d;
			}else if(mc.gameSettings.keyBindJump.isKeyDown()) {
				mc.thePlayer.motionY *= 0.0d;
			}
			
			if(PlayerUtil.MovementInput()) {
				this.setSpeed(PlayerUtil.getBaseMoveSpeed() - 0.05d);
			} else {
				this.setSpeed(0.0d);
			}
			
			this.counter2 += 1;
			
			if (PlayerUtil.MovementInput()) {
				this.setSpeed(PlayerUtil.getBaseMoveSpeed());
			} else {
				mc.thePlayer.motionX *= 0.0D;
			    mc.thePlayer.motionZ *= 0.0D;
			    mc.timer.timerSpeed = 1.0F;
			}
			mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
			switch (counter2) {
			case 1:
				break;
			case 2:
				mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-5, mc.thePlayer.posZ);
                counter2 = 0;
                break;
			case 3:
				mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 1.0E-5, mc.thePlayer.posZ);
                counter2 = 0;
                break;
			}
			event.y = mc.thePlayer.motionY = 0f;
		}
	}
	
	public void setSpeed(double speed) {
		mc.thePlayer.motionX = (-MathHelper.sin(PlayerUtil.getDirection()) * speed);
	    mc.thePlayer.motionZ = (MathHelper.cos(PlayerUtil.getDirection()) * speed);
	}
	
	@Override
	public void onDisable() {
		if(mc.thePlayer.capabilities.isCreativeMode == false) {
			mc.thePlayer.capabilities.isFlying = false;
    		mc.thePlayer.onGround = false;
        	mc.thePlayer.capabilities.allowFlying = false;
		}
		mc.timer.timerSpeed = 1f;
		mc.thePlayer.speedInAir = 0.02f;
		super.onDisable();
	}

}
