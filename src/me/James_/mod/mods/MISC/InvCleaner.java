package me.James_.mod.mods.MISC;

import java.util.Iterator;
import java.util.Random;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventUpdate;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class InvCleaner extends Mod {
	TimeUtil timer = new TimeUtil();
	private int slots;
    private double numberIdkWillfigureout;
    private boolean bol;
    
    public Value<Double> clspeed = new Value("InvCleaner_CleanSpeed", 50d, 0d, 500d, 1d);
    public Value<Boolean> onInv = new Value("InvCleaner_OpenInv", true);
    
	public InvCleaner() {
		super("InvCleaner", Category.MISC);
	}
	
	public int getCleanSpeed() {
		return this.clspeed.getValueState().intValue();
	}
	
	public boolean getOpenInv() {
		return this.onInv.getValueState().booleanValue();
	}
	
	@Override
    public void onEnable() {
        this.slots = 9;
        this.numberIdkWillfigureout = InvCleaner.getEnchantmentOnSword(mc.thePlayer.getHeldItem());
        super.onEnable();
    }
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		if(this.getOpenInv() && mc.currentScreen == null) {
			return;
		}
		
		if (this.slots >= 45 && !this.bol) {
            this.slots = 9;
            return;
        }
		
		if (this.bol) {
			if (this.timer.hasTimeElapsed((long)this.getCleanSpeed() + new Random().nextInt(150), true) || mc.thePlayer.inventoryContainer.getSlot(this.slots).getStack() == null) {
                mc.playerController.windowClick(0, -999, 0, 0, mc.thePlayer);
                mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                mc.playerController.syncCurrentPlayItem();
                this.bol = false;
            }
            return;
		}
		this.numberIdkWillfigureout = InvCleaner.getEnchantmentOnSword(mc.thePlayer.getHeldItem());
        ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(this.slots).getStack();
        if (isItemBad(stack) && InvCleaner.getEnchantmentOnSword(stack) <= this.numberIdkWillfigureout && stack != mc.thePlayer.getHeldItem()) {
            mc.playerController.windowClick(0, this.slots, 0, 0, mc.thePlayer);
            this.bol = true;
        }
        ++this.slots;
		
	}
	
	public static boolean isItemBad(ItemStack item) {
        return item != null && (item.getItem().getUnlocalizedName().contains("TNT") || 
        		item.getItem().getUnlocalizedName().contains("stick") || 
        		item.getItem().getUnlocalizedName().contains("egg") || 
        		item.getItem().getUnlocalizedName().contains("string") || 
        		item.getItem().getUnlocalizedName().contains("flint") || 
        		item.getItem().getUnlocalizedName().contains("compass") || 
        		item.getItem().getUnlocalizedName().contains("feather") || 
        		item.getItem().getUnlocalizedName().contains("map") || 
        		item.getItem().getUnlocalizedName().contains("bucket") || 
        		item.getItem().getUnlocalizedName().contains("chest") || 
        		item.getItem().getUnlocalizedName().contains("snowball") || 
        		item.getItem().getUnlocalizedName().contains("dye") || 
        		item.getItem().getUnlocalizedName().contains("web") || 
        		item.getItem().getUnlocalizedName().contains("gold_ingot") || 
        		item.getItem().getUnlocalizedName().contains("arrow") || 
        		item.getItem().getUnlocalizedName().contains("leather") || 
        		item.getItem().getUnlocalizedName().contains("wheat") || 
        		item.getItem().getUnlocalizedName().contains("fish") || 
        		item.getItem().getUnlocalizedName().contains("enchant") || 
        		item.getItem().getUnlocalizedName().contains("exp") || 
        		item.getItem() instanceof ItemPickaxe || 
        		item.getItem() instanceof ItemTool || 
        		item.getItem() instanceof ItemArmor || 
        		item.getItem() instanceof ItemSword || 
        		item.getItem() instanceof ItemBow || 
        		item.getItem().getUnlocalizedName().contains("potion") && 
        		isBadPotion(item));
    }
	
	private static double getEnchantmentOnSword(ItemStack itemStack) {
        if (itemStack == null) {
            return 0.0;
        }
        if (!(itemStack.getItem() instanceof ItemSword)) {
            return 0.0;
        }
        ItemSword itemSword = (ItemSword)itemStack.getItem();
        return (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) + itemSword.getDamageVsEntity();
    }
	
	public static boolean isBadPotion(ItemStack itemStack) {
        PotionEffect potionEffect;
        if (itemStack == null) {
            return false;
        }
        if (!(itemStack.getItem() instanceof ItemPotion)) {
            return false;
        }
        ItemPotion itemPotion = (ItemPotion)itemStack.getItem();
        Iterator<PotionEffect> iterator = itemPotion.getEffects(itemStack).iterator();
        do {
            if (!iterator.hasNext()) {
                return false;
            }
            PotionEffect pObj = iterator.next();
            potionEffect = pObj;
            if (potionEffect.getPotionID() == Potion.poison.getId()) {
                return true;
            }
            if (potionEffect.getPotionID() == Potion.moveSlowdown.getId()) {
                return true;
            }
            if (potionEffect.getEffectName() != null) continue;
            return true;
        } while (potionEffect.getPotionID() != Potion.harm.getId());
        return true;
    }
	
	public class TimeUtil {
		public double time = System.nanoTime() / 1000000L;

	    public boolean hasTimeElapsed(double time, boolean reset) {
	        if (this.getTime() >= time) {
	            if (reset) {
	                this.reset();
	            }
	            return true;
	        }
	        return false;
	    }

	    public boolean hasTimeElapsed(double time) {
	        if (this.getTime() >= time) {
	            return true;
	        }
	        return false;
	    }

	    public double getTime() {
	        return (double)(System.nanoTime() / 1000000L) - this.time;
	    }

	    public void reset() {
	        this.time = System.nanoTime() / 1000000L;
	    }
	}

}
