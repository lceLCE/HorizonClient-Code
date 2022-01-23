package me.James_.mod.mods.COMBAT;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventPostMotion;
import me.James_.events.EventPreMotion;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.mod.mods.MISC.Teams;
import me.James_.util.PlayerUtil;
import me.James_.util.RotationUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;

public class Killaura extends Mod {
	
	public Value<String> priority = new Value("Killaura", "Priority", 0);
	
	public Value<Boolean> players = new Value("Killaura_Players", true);
	public Value<Boolean> others = new Value("Killaura_Others", false);
	public Value<Boolean> invis = new Value("Killaura_Invisible", false);
	public Value<Boolean> raycast = new Value("Killaura_Raycast", false);
	public static Value<Boolean> autoblock = new Value("Killaura_AutoBlock", false);
    private boolean doBlock = false;
    private boolean unBlock = false;
	public Value<Double> fov = new Value("Killaura_Fov",180d, 1d, 180d, 1d);
	public static Value<Double> range = new Value("Killaura_Range",4.2d, 3.5d, 7.0d, 0.1d);
	public Value<Double> randCPS = new Value("Killaura_RandCPS",2d, 0d, 6d, 1d);
	public Value<Double> cps = new Value("Killaura_CPS",11d, 1d, 20d, 1d);
	public Value<Double> crack = new Value("Killaura_CrackSize",2d, 0d, 10d, 1d);
	
	public Killaura() {
		super("Killaura", Category.COMBAT);
		this.priority.mode.add("Angle");
		this.priority.mode.add("Range");
		this.priority.mode.add("Fov");
		this.priority.mode.add("Health");
	}
	
	private static int randomNumber(final int max, final int min) {
        return -min + (int)(Math.random() * (max - -min + 1));
    }
	
	private List<EntityLivingBase> loaded = new CopyOnWriteArrayList<EntityLivingBase>();
	private int index;
	public EntityLivingBase target;
	public static EntityLivingBase curTarget;
	public timeHelper delay = new timeHelper();
	public timeHelper switchTimer = new timeHelper();
	
	private float currentYaw;
    private float currentPitch;
    
    public static boolean shouldAction = false;
	
	@Override
	public void onEnable() {
		target = null;
		this.loaded.clear();
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		shouldAction = false;
		this.loaded.clear();
		super.onDisable();
        this.mc.thePlayer.itemInUseCount = 0;
        this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        super.onDisable();
	}
	
	@EventTarget
	public void onPreMotion(EventPreMotion event) {
		DecimalFormat df = new DecimalFormat("0.00");
		this.setDisplayName(String.valueOf(df.format(this.range.getValueState().doubleValue())));
		if(this.switchTimer.delay(200.0f)) {
			this.loaded = this.getTargets();
		}
		if (this.index >= this.loaded.size()) {
            this.index = 0;
        }
		if (this.loaded.size() > 0) {
			if (this.switchTimer.delay(200.0f)) {
				this.incrementIndex();
                this.switchTimer.reset();
			}
			EntityLivingBase target = this.loaded.get(this.index);
			if (target != null) {
				if (this.autoblock.getValueState().booleanValue() && (mc.thePlayer.inventory.getCurrentItem() != null) && ((mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword))) {
                    mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), 71999);
                }
				float[] rotations = getEntityRotations(target);
				event.setYaw(rotations[0]);
				event.setPitch(rotations[1]);
			} else {
				shouldAction = false;
			}
		}
	}
	
	public float[] getEntityRotations(Entity target) {
        double xDiff = target.posX - mc.thePlayer.posX;
        double yDiff = target.posY - mc.thePlayer.posY;
        double zDiff = target.posZ - mc.thePlayer.posZ;
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)((- Math.atan2(target.posY + (double)target.getEyeHeight() / 0.0 - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff))) * 180.0 / 3.141592653589793);
        if (yDiff > -0.2 && yDiff < 0.2) {
            pitch = (float)((- Math.atan2(target.posY + (double)target.getEyeHeight() / HitLocation.CHEST.getOffset() - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff))) * 180.0 / 3.141592653589793);
        } else if (yDiff > -0.2) {
            pitch = (float)((- Math.atan2(target.posY + (double)target.getEyeHeight() / HitLocation.FEET.getOffset() - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff))) * 180.0 / 3.141592653589793);
        } else if (yDiff < 0.3) {
            pitch = (float)((- Math.atan2(target.posY + (double)target.getEyeHeight() / HitLocation.HEAD.getOffset() - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff))) * 180.0 / 3.141592653589793);
        }
        return new float[]{yaw, pitch};
    }
	
	private static enum HitLocation {
        AUTO(0.0),
        HEAD(1.0),
        CHEST(1.5),
        FEET(3.5);
        
        private double offset;

        private HitLocation(double offset) {
            this.offset = offset;
        }

        public double getOffset() {
            return this.offset;
        }
    }
    private void startAutoBlockHypixel() {
        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
        if (!this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem())) return;
        this.mc.getItemRenderer().resetEquippedProgress2();
    }

    private void stopAutoBlockHypixel() {
        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        this.mc.playerController.onStoppedUsingItem(this.mc.thePlayer);
    }
    
    @EventTarget
    public void onPost(EventPostMotion event) {
        if (curTarget != null && (this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && autoblock.getValueState().booleanValue() || this.mc.thePlayer.isBlocking()) && this.doBlock) {
            this.mc.thePlayer.itemInUseCount = this.mc.thePlayer.getHeldItem().getMaxItemUseDuration();
            this.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
            this.unBlock = true;
        }
    }
    
    
    
	@EventTarget
	public void onPostMotion(EventPostMotion event) {
		int aps = this.cps.getValueState().intValue();
		int randAps = this.randCPS.getValueState().intValue();
		int crackSize = this.crack.getValueState().intValue();
		int delayValue = ( 20 / aps + randomNumber(randAps , randAps)) * 50;
		if(this.delay.delay(delayValue) && this.loaded.size() > 0 && this.loaded.get(this.index) != null) {
			EntityLivingBase target = this.loaded.get(this.index);
			if(target != null && this.raycast.getValueState().booleanValue()) {
				shouldAction = true;
				Entity rayCast = PlayerUtil.raycast(target);
				if (rayCast != null) {
		            if (this.mc.thePlayer.isBlocking() || this.mc.thePlayer.getHeldItem() != null && this.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && autoblock.getValueState().booleanValue()) {
		                this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
		                this.unBlock = false;
		            }
		            if (!this.mc.thePlayer.isBlocking() && !autoblock.getValueState().booleanValue() && this.mc.thePlayer.itemInUseCount > 0) {
		                this.mc.thePlayer.itemInUseCount = 0;
		            }
					mc.thePlayer.swingItem();
					mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
					if (mc.thePlayer.isBlocking() && this.autoblock.getValueState().booleanValue()) {
mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                       
                    }

                    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
					this.delay.reset();
				}
			} else if(target != null && !this.raycast.getValueState().booleanValue()) {
				mc.thePlayer.swingItem();
				mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
				this.delay.reset();
			}
			for (int i = 0; (double) i < crackSize; ++i) {
				mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT);
                mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT_MAGIC);
			}
		}
	}
	
	private void incrementIndex() {
        ++this.index;
        if (this.index >= this.loaded.size()) {
            this.index = 0;
        }
    }
	
	private boolean validEntity(Entity entity) {
		if(mc.thePlayer.getDistanceToEntity(entity) <= this.range.getValueState().doubleValue() && entity.isEntityAlive()) {
			if(!this.isInFOV(entity)) {
				return false;
			}
			if(Antibot.invalid.contains(entity)) {
				return false;
			}
			if(entity instanceof EntityPlayer && this.players.getValueState().booleanValue()) {
				EntityPlayer lol = (EntityPlayer)entity;
				if(lol != mc.thePlayer) {
					return !Teams.isOnSameTeam(lol) && (!lol.isInvisible() || this.invis.getValueState().booleanValue());
				}
			}
			if((entity instanceof EntityMob || entity instanceof EntityVillager) && this.others.getValueState().booleanValue()) {
				return true;
			}
			if(entity instanceof EntityAnimal && this.others.getValueState().booleanValue()) {
				return true;
			}
		}
		return false;
	}

	private void sortList(final List<EntityLivingBase> weed) {
        if(this.priority.isCurrentMode("Range")) {
        	weed.sort((o1, o2) -> (int)(o1.getDistanceToEntity(mc.thePlayer) - o2.getDistanceToEntity(mc.thePlayer)));
        }
        if(this.priority.isCurrentMode("Fov")) {
        	weed.sort(Comparator.comparingDouble(o -> RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationPitch, RotationUtils.getRotations(o)[0])));
        }
        if(this.priority.isCurrentMode("Angle")) {
        	weed.sort((o1, o2) -> {
                float[] rot1 = RotationUtils.getRotations(o1);
                float[] rot2 = RotationUtils.getRotations(o2);
                return (int)(mc.thePlayer.rotationYaw - rot1[0] - (mc.thePlayer.rotationYaw - rot2[0]));
            });
        }
        if(this.priority.isCurrentMode("Health")) {
        	weed.sort((o1, o2) -> (int)(o1.getHealth() - o2.getHealth()));
        }
    }
    
    private EntityLivingBase getTarget(List<EntityLivingBase> list) {
        this.sortList(list);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    private List<EntityLivingBase> getTargets() {
    	List<EntityLivingBase> targets = new ArrayList<>();
        for (Object o : mc.theWorld.getLoadedEntityList()) {
            if (o instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) o;
                if (validEntity(entity)) {
                    targets.add(entity);
                }
            }
        }
        sortList(targets);
        return targets;
    }
    
    private boolean isInFOV(Entity entity) {
    	int fov = this.fov.getValueState().intValue();
        return Math.abs(RotationUtils.getYawChange(entity.posX, entity.posZ)) <= fov && Math.abs(RotationUtils.getPitchChange(entity, entity.posY)) <= fov;
    }
	
	public class timeHelper {
		private long prevMS;
	    
	    public timeHelper() {
	        this.prevMS = 0L;
	    }
	    
	    public boolean delay(final float milliSec) {
	        return this.getTime() - this.prevMS >= milliSec;
	    }
	    
	    public void reset() {
	        this.prevMS = this.getTime();
	    }
	    
	    public long getTime() {
	        return System.nanoTime() / 1000000L;
	    }
	    
	    public long getDifference() {
	        return this.getTime() - this.prevMS;
	    }
	    
	    public void setDifference(final long difference) {
	        this.prevMS = this.getTime() - difference;
	    }
	}
}