package me.James_.mod.mods.MOVEMENT;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventMove;
import me.James_.events.EventUpdate;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.util.PlayerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovementInput;

public class LongJump extends Mod {
	int jumps;
    private double speed;
    private int stage;
    private double moveSpeed;
    private double lastDist;
    private int airTicks;
    private int groundTicks;
	public Value<String> mode = new Value("LongJump", "Mode", 0);
	
	public LongJump() {
		super("LongJump", Category.MOVEMENT);
		this.mode.mode.add("Hypixel");
	}
	
	public void damagePlayer() {
        for (int index = 0; index < 60; ++index) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.06, this.mc.thePlayer.posZ, false));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
        }
        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.1, this.mc.thePlayer.posZ, false));
    }
	
	public double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
	
	@Override
    public void onEnable() {
       if(this.mode.isCurrentMode("Hypixel")) {
    	   this.moveSpeed = PlayerUtil.getBaseMoveSpeed();
           this.speed = 1.0;
           this.groundTicks = 8;
           this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.001, this.mc.thePlayer.posZ);
           this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.001, this.mc.thePlayer.posZ);
           this.stage = 0;
       }
       super.onEnable();
    }
    
    @Override
    public void onDisable() {
       super.onDisable();
    }
    
    @EventTarget
    public void onUpdate(EventUpdate event) {
    	final double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
        final double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }
    
    @EventTarget
    public void onMove(EventMove event) {
    	if(this.mode.isCurrentMode("Hypixel")) {
    		this.setDisplayName("Hypixel");
    		if (!this.mc.thePlayer.onGround) {
                this.moveSpeed = PlayerUtil.getBaseMoveSpeed() / 2.0;
            }
        	MovementInput movementInput = this.mc.thePlayer.movementInput;
        	float forward = mc.thePlayer.movementInput.moveForward;
            float strafe = mc.thePlayer.movementInput.moveStrafe;
            float yaw = this.mc.thePlayer.rotationYaw;
            if (this.stage == 1 && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
            	this.stage = 2;
                this.moveSpeed = (1.38 * PlayerUtil.getBaseMoveSpeed() - 0.01) / 1.6;
            } else if (this.stage == 2) {
            	this.stage = 3;
                event.x = 0.0;
                event.z = 0.0;
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.001, this.mc.thePlayer.posZ);
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.001, this.mc.thePlayer.posZ);
                this.mc.thePlayer.motionY = 0.423;
                event.y = 0.423;
                this.moveSpeed *= 2.149;
            } else if (this.stage == 3) {
                this.stage = 4;
                final double difference = 0.66 * (this.lastDist - PlayerUtil.getBaseMoveSpeed());
                this.moveSpeed = (this.lastDist - difference) * 1.95;
            } else {
            	 if (this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0)).size() > 0 || this.mc.thePlayer.isCollidedVertically) {
                     this.stage = 1;
                 }
                 this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                 if (this.mc.thePlayer.motionY < 0.1) {
                     mc.thePlayer.motionY -= 0.005;
                 }
            }
            this.moveSpeed = Math.max(this.moveSpeed, PlayerUtil.getBaseMoveSpeed());
            if (forward == 0.0f && strafe == 0.0f) {
                event.x = 0.0;
                event.z = 0.0;
            } else if (forward != 0.0f) {
            	if (strafe >= 1.0f) {
                    yaw += ((forward > 0.0f) ? -45 : 45);
                    strafe = 0.0f;
                } else if (strafe <= -1.0f) {
                    yaw += ((forward > 0.0f) ? 45 : -45);
                    strafe = 0.0f;
                }
                if (forward > 0.0f) {
                    forward = 1.0f;
                } else if (forward < 0.0f) {
                    forward = -1.0f;
                }
            }
            double mx = Math.cos(Math.toRadians(yaw + 90.0f));
            double mz = Math.sin(Math.toRadians(yaw + 90.0f));
            event.x = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
            event.z = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
            if (this.mc.thePlayer.fallDistance > 1.0) {
                event.x = 0.0;
                event.z = 0.0;
            }
    	}
    }

}
