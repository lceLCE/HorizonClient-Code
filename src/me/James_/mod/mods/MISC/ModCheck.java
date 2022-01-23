package me.James_.mod.mods.MISC;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventChat;
import me.James_.events.EventPreMotion;
import me.James_.events.EventRender2D;
import me.James_.events.EventUpdate;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.ui.ClientNotification;
import me.James_.util.ClientUtil;
import me.James_.util.PlayerUtil;
import me.James_.util.timeUtils.TimeHelper;
import me.James_.util.timeUtils.TimeUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class ModCheck extends Mod {

	private String[] modlist = new String[] {"mux", "zofia", "��������", "heav3ns", "higod", "chrisan", "С����", "chen_xixi", "allforu", "��ɪ", "tanker_01", "91m", "bingmo", "crazyforlove"};
	private String modname;
	private TimeHelper timer = new TimeHelper();
	private List<String> offlinemod = new ArrayList();
	private List<String> onlinemod = new ArrayList();
	private Value<Boolean> showOffline = new Value("ModCheck_ShowOffline", true);
	private Value<Boolean> showOnline = new Value("ModCheck_ShowOnline", true);
	
	private int counter;
	private boolean isFinished;
	
    public ModCheck() {
        super("ModCheck", Category.MISC);
    }
	
	@EventTarget
	public void onRender(EventRender2D e) {
		FontRenderer font = mc.fontRendererObj;
		List<String> listArray = Arrays.asList(modlist);
		listArray.sort((o1, o2) -> {
			return font.getStringWidth(o2) - font.getStringWidth(o1);
		});
		int counter2 = 0;
		for(String mods : listArray) {
			if(offlinemod.contains(mods) && showOffline.getValueState()) {
				font.drawStringWithShadow(mods, 5, 100 + counter2 * 10, Color.RED.getRGB());
				counter2++;
			}
			if(onlinemod.contains(mods) && showOnline.getValueState()) {
				font.drawStringWithShadow(mods, 5, 100 + counter2 * 10, Color.GREEN.getRGB());
				counter2++;
			}
			
		}
	}
	
	@EventTarget
	public void onChat(EventChat e) {
		if(e.getMessage().contains("������Ҳ����ߣ�") || e.getMessage().contains("That player is not online!")) {
			e.setCancelled(true);
			if(onlinemod.contains(modname)) {
				ClientUtil.sendClientMessage("\247d[Debug Client]\247c" + modname + "\247a�����ߣ�", ClientNotification.Type.INFO);
				onlinemod.remove(modname);
				offlinemod.add(modname);
				return;
			}
			if(!offlinemod.contains(modname)) {
				ClientUtil.sendClientMessage("\247d[Debug Client]\247c" + modname + "\247a�����ߣ�", ClientNotification.Type.INFO);
				offlinemod.add(modname);
			}
		}
		
		if(e.getMessage().contains("You cannot message this player.")) {
			e.setCancelled(true);
			if(offlinemod.contains(modname)) {
				ClientUtil.sendClientMessage("\247d[Debug Client]\247c" + modname + "\247a�����ߣ�", ClientNotification.Type.INFO);
				offlinemod.remove(modname);
				onlinemod.add(modname);
				return;
			}
			if(!onlinemod.contains(modname)) {
				ClientUtil.sendClientMessage("\247d[Debug Client]\247c" + modname + "\247a���ߣ�", ClientNotification.Type.INFO);
				onlinemod.add(modname);
			}
		}

		if(e.getMessage().contains("�Ҳ�����Ϊ \"" + modname + "\" �����")) {
			e.setCancelled(true);
		}
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e) {
		if(timer.isDelayComplete(isFinished ? 10000L : 2000L)) {
			if(counter >= modlist.length) {
				counter = -1;
				if(!isFinished) {
					isFinished = true;
				}
				
			}
			counter++;
			modname = modlist[counter];
			mc.thePlayer.sendChatMessage("/message " + modname + " hi");
			timer.reset();
		}
	}
}
