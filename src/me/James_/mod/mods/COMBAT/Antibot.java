package me.James_.mod.mods.COMBAT;

import java.util.ArrayList;
import java.util.List;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventPacket;
import me.James_.events.EventUpdate;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.ui.ClientNotification;
import me.James_.util.ChatType;
import me.James_.util.ClientUtil;
import me.James_.util.timeUtils.TimeHelper;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;

public class Antibot extends Mod {
	public static ArrayList<Entity> invalid = new ArrayList();
	public TimeHelper timer = new TimeHelper();
	
	public Value<Boolean> remove = new Value("Antibot_Remove", false);
	public Value<Double> delay = new Value("Antibot_Delay", 20d, 10d, 60d, 1d);
	public Value<String> atb_mode = new Value("Antibot", "Mode", 1);
	
	public Antibot() {
		super("Antibot", Category.COMBAT);
		this.atb_mode.mode.add("Basic");
		this.atb_mode.mode.add("Watchdog");
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(atb_mode.isCurrentMode("Basic")) {
			this.setDisplayName("Basic");
			for (Object entity : this.mc.theWorld.loadedEntityList) {
                Entity ent = (Entity)entity;
                if (ent != this.mc.thePlayer && ent.isInvisible() && ent.ticksExisted >= 105) {
                	ent.ticksExisted = -1;
                    ent.isDead = true;
                    ent.setInvisible(true);
                }
            }
		}
		if(atb_mode.isCurrentMode("Watchdog")) {
			this.setDisplayName("Watchdog");
			for(Object obj : mc.theWorld.loadedEntityList) {
				if(obj instanceof EntityPlayer) {
					EntityPlayer ent = (EntityPlayer)obj;
					String str = ent.getDisplayName().getFormattedText();
					if(ent.getDistanceToEntity(mc.thePlayer) <= 10 && ent != mc.thePlayer && !invalid.contains(ent) && !ent.isDead) {
						if(!getTabPlayerList().contains(ent)) {
							if(this.remove.getValueState().booleanValue()) {
								mc.theWorld.removeEntity(ent);
								timer.reset();
							} else {
								this.invalid.add(ent);
								timer.reset();
							}
						}
					}
				}
			}
			if(!invalid.isEmpty() && timer.delay(this.delay.getValueState().doubleValue() * 1000)) {
				ClientUtil.INSTANCE.sendClientMessage("Antibot AUTO CLEAR!", ClientNotification.Type.INFO);
				invalid.clear();
				timer.reset();
			}
		}
	}
	
	@EventTarget
	public void onReceivePacket(EventPacket event) {
		if(this.atb_mode.isCurrentMode("Watchdog")) {
			S0CPacketSpawnPlayer lol;
			double posX;
			double difX;
			double posY;
			double difY;
			double posZ;
			double difZ;
			double dist;
			if (event.getPacket() instanceof S0CPacketSpawnPlayer && (dist = Math.sqrt((difX = mc.thePlayer.posX - (posX = (lol = (S0CPacketSpawnPlayer)event.getPacket()).getX() / 32.0)) * difX + (difY = mc.thePlayer.posY - (posY = lol.getY() / 32.0)) * difY + (difZ = mc.thePlayer.posZ - (posZ = lol.getZ() / 32.0)) * difZ)) <= 17.0 && posX != mc.thePlayer.posX && posY != mc.thePlayer.posY && posZ != mc.thePlayer.posZ) {
				ClientUtil.sendChatMessage("Hypixel bot Detected " + mc.getNetHandler().getPlayerInfo((lol = (S0CPacketSpawnPlayer)event.getPacket()).getPlayer()).getDisplayName().getUnformattedText(), ChatType.WARN);
				event.setCancelled(true);
			}
		}
	}
	
	@Override
	public void onDisable() {
		if(!this.invalid.isEmpty()) {
			this.invalid.clear();
		}
		super.onDisable();
	}
	
	public List<EntityPlayer> getTabPlayerList() {
        NetHandlerPlayClient nhpc = mc.thePlayer.sendQueue;
        List<EntityPlayer> list = new ArrayList<>();
        List players = new GuiPlayerTabOverlay(mc, mc.ingameGUI).field_175252_a.sortedCopy(nhpc.getPlayerInfoMap());
        for (final Object o : players) {
            final NetworkPlayerInfo info = (NetworkPlayerInfo) o;
            if (info == null) {continue;}
            list.add(mc.theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
        }
        return list;
    }
	
}
