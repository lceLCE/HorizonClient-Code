package me.James_.mod.mods.MISC;

import java.util.Random;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventUpdate;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.util.timeUtils.TimeHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;

public class ChestStealer extends Mod {

	public Value<Double> delay = new Value("ChestStealer_Delay", 65d, 0d, 200d, 1d);
	public Value<Boolean> cchest = new Value("ChestStealer_CloseChest", true);
	TimeHelper time = new TimeHelper();
	
	public ChestStealer() {
		super("ChestStealer", Category.MISC);
	}
	
	@EventTarget
    public void onUpdate(EventUpdate event) {
		Container container = mc.thePlayer.openContainer;
		if (container != null && container instanceof ContainerChest) {
			ContainerChest containerchest = (ContainerChest)container;
			for (int i = 0; i < containerchest.getLowerChestInventory().getSizeInventory(); ++i) {
				if (containerchest.getLowerChestInventory().getStackInSlot(i) != null && this.time.delay((float)this.delay.getValueState().doubleValue())) {
					if(new Random().nextInt(100) > 80) {
	            		   continue;
	            	}
					mc.playerController.windowClick(containerchest.windowId, i, 0, 1, mc.thePlayer);
					this.time.reset();
				}
			}
			if (this.isContainerEmpty(container) && this.cchest.getValueState().booleanValue()) {
				mc.thePlayer.closeScreen();
			}
		}
	}
	
	private boolean isContainerEmpty(Container container) {
		boolean flag = true;
        int i = 0;
        for (int j = container.inventorySlots.size() == 90 ? 54 : 27; i < j; ++i) {
        	if (container.getSlot(i).getHasStack()) {
        		flag = false;
        	}
        }
        return flag;
	}

}
