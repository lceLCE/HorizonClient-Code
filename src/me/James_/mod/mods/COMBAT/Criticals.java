package me.James_.mod.mods.COMBAT;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Client;
import me.James_.Value;
import me.James_.events.EventMove;
import me.James_.events.EventPacket;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.util.PlayerUtil;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.network.Packet;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;	

public class Criticals extends Mod
{
    private Value<String> mode = new Value("Criticals", "Mode", 0);
    public Criticals() {
        super("Criticals", Category.COMBAT);
        mode.mode.add("Packet");
        mode.mode.add("Hypixel");
    }
	@EventTarget
	public void onMove(EventMove event) {
		 final C02PacketUseEntity packet = (C02PacketUseEntity)event.getPacket();
		 if(this.mode.isCurrentMode("Packet")) {
			    this.setDisplayName("Packet");
                if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                    if (!this.canCrit()) return;
                    double[] offsets = new double[]{0.0625, 0.0, 1.0E-4, 0.0};
                    int i = 0;
                    while (i < offsets.length) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + offsets[i], this.mc.thePlayer.posZ, false));
                        ++i;
                    }
            }
		 }
		if(this.mode.isCurrentMode("Hypixel")) {
			this.setDisplayName("Hypixel");
	        if (!this.canCrit()) return;
	        double[] arrd = new double[]{0.06142999976873398, 0.0, 0.012511000037193298, 0.0};
	        int n = arrd.length;
	        int n2 = 0;
	        while (n2 < n) {
	            double offset = arrd[n2];
	            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + offset, this.mc.thePlayer.posZ, true));
	            ++n2;
	        }
            }
		}
 
    public void onCriticalHit2() {
        if(mc.thePlayer.onGround) {
            mc.thePlayer.motionY = 0.0622D;
        }

    }
    void offsetCrit() {
        if (!this.canCrit()) return;
        if (this.mc.getCurrentServerData().serverIP.contains("hypixel")) return;
        double[] offsets = new double[]{0.0624, 0.0, 1.0E-4, 0.0};
        int i = 0;
        while (i < offsets.length) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + offsets[i], this.mc.thePlayer.posZ, false));
            ++i;
        }
    }
    
    private boolean canCrit() {
        if (!this.mc.thePlayer.onGround) return false;
        if (this.mc.thePlayer.isInWater()) return false;
        return true;
    }


}