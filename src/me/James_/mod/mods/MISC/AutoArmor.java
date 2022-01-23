package me.James_.mod.mods.MISC;

import java.util.Random;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventPreMotion;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.util.timeUtils.TimeUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class AutoArmor extends Mod {

	public Value<Double> delay = new Value("AutoArmor_Delay", 100d, 0d, 500d, 1d);
	
	public AutoArmor() {
		super("AutoArmor", Category.MISC);
	}
	
	public TimeUtil timer = new TimeUtil();
	double maxValue = -1.0;
    double mv;
    private int num = 5;
    int item = -1;
	private final int[] boots = new int[]{313, 309, 317, 305, 301};
    private final int[] chestplate = new int[]{311, 307, 315, 303, 299};
    private final int[] helmet = new int[]{310, 306, 314, 302, 298};
    private final int[] leggings = new int[]{312, 308, 316, 304, 300};
	
	@EventTarget
	public void onPreMotion(EventPreMotion event) {
		if(mc.thePlayer.capabilities.isCreativeMode || (mc.thePlayer.openContainer != null && mc.thePlayer.openContainer.windowId != 0)) {
			return;
		}
		if(timer.delay(this.delay.getValueState().intValue() + new Random().nextInt(4))) {
			this.maxValue = -1.0;
            this.item = -1;
            for (int i = 9; i < 45; ++i) {
            	if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null && this.canEquip(mc.thePlayer.inventoryContainer.getSlot(i).getStack()) != -1 && this.canEquip(mc.thePlayer.inventoryContainer.getSlot(i).getStack()) == this.num) {
            		this.change(this.num, i);
            	}
            }
            if (this.item != -1) {
            	if (mc.thePlayer.inventoryContainer.getSlot(this.item).getStack() != null) {
            		mc.playerController.windowClick(0, this.num, 0, 1, mc.thePlayer);
                }
            	mc.playerController.windowClick(0, this.item, 0, 1, mc.thePlayer);
            }
            this.num = ((this.num == 8) ? 5 : (++this.num));
            timer.reset();
		}
	}
	
	private int canEquip(final ItemStack stack) {
        for (final int id : this.boots) {
            stack.getItem();
            if (Item.getIdFromItem(stack.getItem()) == id) {
                return 8;
            }
        }
        for (final int id : this.leggings) {
            stack.getItem();
            if (Item.getIdFromItem(stack.getItem()) == id) {
                return 7;
            }
        }
        for (final int id : this.chestplate) {
            stack.getItem();
            if (Item.getIdFromItem(stack.getItem()) == id) {
                return 6;
            }
        }
        for (final int id : this.helmet) {
            stack.getItem();
            if (Item.getIdFromItem(stack.getItem()) == id) {
                return 5;
            }
        }
        return -1;
    }
    
    private void change(final int numy, final int i) {
        this.mv = ((this.maxValue == -1.0) ? ((mc.thePlayer.inventoryContainer.getSlot(numy).getStack() != null) ? this.getProtValue(mc.thePlayer.inventoryContainer.getSlot(numy).getStack()) : this.maxValue) : this.maxValue);
        if (this.mv <= this.getProtValue(mc.thePlayer.inventoryContainer.getSlot(i).getStack())) {
            if (this.mv == this.getProtValue(mc.thePlayer.inventoryContainer.getSlot(i).getStack())) {
                final int currentD = (mc.thePlayer.inventoryContainer.getSlot(numy).getStack() != null) ? mc.thePlayer.inventoryContainer.getSlot(numy).getStack().getItemDamage() : 999;
                final int n;
                final int newD = n = ((mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null) ? mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItemDamage() : 500);
                if (newD <= currentD && newD != currentD) {
                    this.item = i;
                    this.maxValue = this.getProtValue(mc.thePlayer.inventoryContainer.getSlot(i).getStack());
                }
            }
            else {
                this.item = i;
                this.maxValue = this.getProtValue(mc.thePlayer.inventoryContainer.getSlot(i).getStack());
            }
        }
    }
    
    private double getProtValue(final ItemStack stack) {
        if (stack != null) {
            return ((ItemArmor)stack.getItem()).damageReduceAmount + (100 - ((ItemArmor)stack.getItem()).damageReduceAmount * 4) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 4 * 0.0075;
        }
        return 0.0;
    }

}
