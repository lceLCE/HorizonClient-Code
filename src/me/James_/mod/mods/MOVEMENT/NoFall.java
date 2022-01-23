package me.James_.mod.mods.MOVEMENT;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.Event;
import me.James_.events.EventMotion;
import me.James_.events.EventMove;
import me.James_.events.EventPacket;
import me.James_.events.EventUpdate;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.util.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

	public class NoFall extends Mod {
		public Value<String> mode = new Value("NoFall", "Mode", 0);
		public NoFall() {
			super("NoFall", Category.MOVEMENT);
			this.mode.mode.add("Hypixel");
			this.mode.mode.add("ChinaHypixel");
		}
		@EventTarget
		public void onMove(EventMove event) {
			if(this.mode.isCurrentMode("Hypixel")) {
				this.setDisplayName("Hypixel");
				   if (this.mc.thePlayer.fallDistance > 2.0F) {
					   this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
					   this.mc.thePlayer.fallDistance = 0.0F;
				   }
			}
		}
		   @EventTarget
		   public void onPacket(EventPacket e) {
			   if(mode.isCurrentMode("ChinaHypixel")) {
				   if(e.getPacket() instanceof C03PacketPlayer) {
						if(mc.thePlayer.fallDistance > 3.0F) {
							((C03PacketPlayer)e.packet).onGround = true;
						}
				   }
			   }
		   }
		public void setSpeed(double speed) {
			mc.thePlayer.motionX = (-MathHelper.sin(PlayerUtil.getDirection()) * speed);
		    mc.thePlayer.motionZ = (MathHelper.cos(PlayerUtil.getDirection()) * speed);
		}
		
	}
