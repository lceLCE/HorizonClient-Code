package me.James_.mod.mods.MOVEMENT;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventMove;
import me.James_.events.EventPostMotion;
import me.James_.events.EventPreMotion;
import me.James_.events.EventSafeWalk;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.util.CombatUtil;
import me.James_.util.PlayerUtil;
import me.James_.util.timeUtils.TimeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;

public class Scaffold2
extends Mod {
    private BlockData blockData;
    private timeHelper time = new timeHelper();
    private timeHelper delay = new timeHelper();
    private timeHelper timer2 = new timeHelper();
    private Value<Boolean> tower = new Value<Boolean>("Scaffold2_Tower", true);
    private Value<Boolean> noSwing = new Value<Boolean>("Scaffold2_NoSwing", false);
    private Value<Boolean> silent = new Value<Boolean>("Scaffold2_Silent", false);
    private Value<Double> delayValue = new Value<Double>("Scaffold2_Delay", 250.0, 40.0, 1000.0, 10.0);
    public Value<String> mode = new Value("Scaffold2", "Mode", 0);
    private double olddelay;
    public Value<Boolean> timer = new Value<Boolean>("Scaffold2_Timer", true);
    private BlockPos blockpos;
    private EnumFacing facing;
    private Value expand = new Value<Boolean>("Scaffold2_Expand", false);
    private boolean rotated = false;
    private boolean should = false;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;

    public Scaffold2() {
        super("Scaffold2", Category.MOVEMENT);
        mode.mode.add("WatchDoge");//�������в���
        mode.mode.add("AAC");
        mode.mode.add("CubeCraft");
        mode.addValue("Gomme");
        mode.addValue("Rewi");
        this.showValue = mode;
        this.setDisplayName(this.getValue());
    }

    private boolean couldBlockBePlaced() {
        double x = Minecraft.thePlayer.posX;
        double z = Minecraft.thePlayer.posZ;
        double d = getDoubleRandom(0.22, 0.25);
        switch (Scaffold2.$SWITCH_TABLE$net$minecraft$util$EnumFacing()[Minecraft.thePlayer.getHorizontalFacing().ordinal()]) {
            case 3: {
                if (Minecraft.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 0.1, z + d)).getBlock() != Blocks.air) break;
                return true;
            }
            case 4: {
                if (Minecraft.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 0.1, z - d)).getBlock() != Blocks.air) break;
                return true;
            }
            case 5: {
                if (Minecraft.theWorld.getBlockState(new BlockPos(x + d, Minecraft.thePlayer.posY - 0.1, Minecraft.thePlayer.posZ)).getBlock() != Blocks.air) break;
                return true;
            }
            case 6: {
                if (Minecraft.theWorld.getBlockState(new BlockPos(x - d, Minecraft.thePlayer.posY - 0.1, Minecraft.thePlayer.posZ)).getBlock() != Blocks.air) break;
                return true;
            }
        }
        return false;
    }

    @EventTarget
    public void onPre(EventPreMotion event) {
        if (mode.isCurrentMode("WatchDoge") && timer.getValueState().booleanValue()) {
        }
        if (Minecraft.thePlayer != null) {
            this.blockData = (Boolean)this.expand.getValueState() != false ? this.getBlockData(new BlockPos(Minecraft.thePlayer.posX - Math.sin(PlayerUtil.getDirection()) * 1.0, Minecraft.thePlayer.posY - 0.75, Minecraft.thePlayer.posZ + Math.cos(PlayerUtil.getDirection()) * 1.0), 1) : this.getBlockData(new BlockPos(Minecraft.thePlayer).add(0.0, -0.75, 0.0), 1);
            int block = this.getBlockItem();
            Item item = Minecraft.thePlayer.inventory.getStackInSlot(block).getItem();
            if (!mode.isCurrentMode("Gomme")) {
                if (block != -1 && item != null && item instanceof ItemBlock) {
                    if (this.silent.getValueState().booleanValue()) {
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(block));
                    }
                    if (mode.isCurrentMode("AAC") && mode.isCurrentMode("AAC") && this.mc.gameSettings.keyBindSprint.pressed && PlayerUtil.MovementInput()) {
                        PlayerUtil.setSpeed(0.11);
                    }
                    if (mode.isCurrentMode("CubeCraft") && PlayerUtil.MovementInput()) {
                        PlayerUtil.setSpeed(0.05);
                    }
                }
                if (mode.isCurrentMode("Rewi") && PlayerUtil.MovementInput()) {
                    PlayerUtil.setSpeed(0.14);
                }
                new java.util.Random();
                if (this.blockData != null && block != -1 && item != null && item instanceof ItemBlock) {
                    Vec3 rot2 = this.getBlockSide(this.blockData.pos, this.blockData.face);
                    float[] rot = CombatUtil.getRotationsNeededBlock(rot2.xCoord, rot2.yCoord, rot2.zCoord);
                    event.pitch = mode.isCurrentMode("Rewi") ? 82.500114f : rot[1];
                    if (!mode.isCurrentMode("CubeCraft") && !mode.isCurrentMode("Rewi")) {
                        event.yaw = rot[0];
                    } else if (this.mc.gameSettings.keyBindForward.pressed) {
                        event.yaw = Minecraft.thePlayer.rotationYaw >= 180.0f ? Minecraft.thePlayer.rotationYaw - 180.0f + (float)new Random().nextInt(5) : Minecraft.thePlayer.rotationYaw + 180.0f - (float)new Random().nextInt(5);
                    } else if (this.mc.gameSettings.keyBindBack.pressed) {
                        event.yaw = Minecraft.thePlayer.rotationYaw;
                    } else if (this.mc.gameSettings.keyBindLeft.pressed) {
                        event.yaw = Minecraft.thePlayer.rotationYaw + 90.0f;
                    } else if (this.mc.gameSettings.keyBindRight.pressed) {
                        event.yaw = Minecraft.thePlayer.rotationYaw - 90.0f;
                    }
                }
            } else {
                if (this.rotated) {
                    PlayerUtil.setSpeed(0.03877341815081586);
                } else {
                    PlayerUtil.setSpeed(0.08621806584246793);
                }
                if (PlayerUtil.MovementInput()) {
                    PlayerUtil.setSpeed(0.13);
                }
                this.rotated = false;
                this.blockpos = null;
                this.facing = null;
                if (block != -1 && item != null && item instanceof ItemBlock) {
                    if (this.silent.getValueState().booleanValue()) {
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(block));
                    }
                    BlockPos pos = new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 1.0, Minecraft.thePlayer.posZ);
                    this.setBlockAndFacing(pos);
                    if (Minecraft.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
                        this.rotated = true;
                    }
                    float[] rot21 = CombatUtil.getRotationsNeededBlock(this.blockpos.getX(), this.blockpos.getY(), this.blockpos.getZ());
                    float[] rot = getIntaveRots(this.blockpos, this.facing);
                    event.yaw = (float)((double)rot[0] + this.getDoubleRandom(-0.1, 0.1));
                    event.pitch = 82.500114f;
                }
            }
        }
    }


    public static float[] getIntaveRots(BlockPos bp, EnumFacing enumface) {
        double x = (double)bp.getX() + 0.5;
        double y = (double)bp.getY() + 0.5;
        double z = (double)bp.getZ() + 0.5;
        if (enumface != null) {
            if (EnumFacing.UP != null) {
                y += 0.5;
            } else if (EnumFacing.DOWN != null) {
                y -= 0.5;
            } else if (EnumFacing.WEST != null) {
                x += 0.5;
            } else if (EnumFacing.EAST != null) {
                x -= 0.5;
            } else if (EnumFacing.NORTH != null) {
                z += 0.5;
            } else if (EnumFacing.SOUTH != null) {
                z -= 0.5;
            }
        }
        double dX = x - Minecraft.thePlayer.posX;
        double dY = y - Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight();
        double dZ = z - Minecraft.thePlayer.posZ;
        float yaw = (float)(Math.atan2((double)dZ, (double)dX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-Math.atan2((double)dY, (double)Math.sqrt((double)(dX * dX + dZ * dZ))) * 180.0 / 3.141592653589793);
        yaw = MathHelper.wrapAngleTo180_float((float)yaw);
        pitch = MathHelper.wrapAngleTo180_float((float)pitch);
        return new float[]{yaw, pitch};
    }
    
    private double getDoubleRandom(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    @EventTarget
    public void onPost(EventPostMotion event) {
    }

    @EventTarget
    public void onSafe(EventPostMotion event) {
        if (Minecraft.thePlayer != null && this.blockData != null) {
            int block1 = this.getBlockItem();
            Random rand = new Random();
            Item item1 = Minecraft.thePlayer.inventory.getStackInSlot(block1).getItem();
            if (block1 != -1 && item1 != null && item1 instanceof ItemBlock) {
                if ((mode.isCurrentMode("Gomme") || mode.isCurrentMode("Rewi") || mode.isCurrentMode("CubeCraft")) && !this.couldBlockBePlaced()) {
                    return;
                }
                Vec3 hitVec = new Vec3(this.blockData.pos).addVector(0.5, 0.5, 0.5).add(new Vec3(this.blockData.face.getDirectionVec()).scale(0.5));
                if ((!mode.isCurrentMode("Rewi") && !mode.isCurrentMode("AAC") && !mode.isCurrentMode("Gomme") || this.delay.delay((long)(mode.isCurrentMode("Gomme") ? new Random().nextLong()*100 : (mode.isCurrentMode("Rewi") ? 0.0 : (double)this.delayValue.getValueState().intValue() + this.getDoubleRandom(30.0, 80.0))))) && Minecraft.playerController.onPlayerRightClick(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.inventory.getStackInSlot(block1), this.blockData.pos, this.blockData.face, hitVec)) {
                    this.delay.reset();
                    this.blockData = null;
                    this.time.reset();
                    
                	if(tower.getValueState()) {
                  		if(Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) {
                      		Minecraft.getMinecraft().thePlayer.jump();
                  			PlayerUtil.setSpeed(0);
                  		}
                      	if(timer2.delay(1150) && Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) {
                      		Minecraft.getMinecraft().thePlayer.motionY = -0.28D;
                      		timer2.reset();
                      	} 
                      	if(!Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) {
                      		timer2.reset();
                      	}
                    }
                    
                    if (this.noSwing.getValueState().booleanValue()) {
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                    } else {
                        Minecraft.thePlayer.swingItem();
                    }
                } else if (mode.isCurrentMode("CubeCraft")) {
                    if (this.delay.delay(this.delayValue.getValueState().intValue() + rand.nextInt(50)) && Minecraft.playerController.onPlayerRightClick(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.inventory.getStackInSlot(block1), this.blockData.pos, this.blockData.face, hitVec)) {
                        this.delay.reset();
                        this.blockData = null;
                        this.time.reset();
                        if (this.noSwing.getValueState().booleanValue()) {
                            Minecraft.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                        } else {
                            Minecraft.thePlayer.swingItem();
                        }
                    } else if (this.delay.delay(this.delayValue.getValueState().longValue()) && mode.isCurrentMode("Normal")) {
                        if (Minecraft.playerController.onPlayerRightClick(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.inventory.getStackInSlot(block1), this.blockData.pos, this.blockData.face, hitVec)) {
                            this.delay.reset();
                            this.blockData = null;
                            
                            if (this.noSwing.getValueState().booleanValue()) {
                                Minecraft.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
                            } else {
                                Minecraft.thePlayer.swingItem();
                            }
                        }
                        this.delay.reset();
                    }
                }
            }
        }
    }

    private boolean canPlace(EntityPlayerSP player, WorldClient worldIn, ItemStack heldStack, BlockPos hitPos, EnumFacing side, Vec3 vec3) {
        return heldStack.getItem() instanceof ItemBlock ? ((ItemBlock)heldStack.getItem()).canPlaceBlockOnSide(worldIn, hitPos, side, player, heldStack) : false;
    }

    private void setBlockAndFacing(BlockPos bp) {
        if (Minecraft.theWorld.getBlockState(bp.add(0, -1, 0)).getBlock() != Blocks.air) {
            this.blockpos = bp.add(0, -1, 0);
            this.facing = EnumFacing.UP;
        } else if (Minecraft.theWorld.getBlockState(bp.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.blockpos = bp.add(-1, 0, 0);
            this.facing = EnumFacing.EAST;
        } else if (Minecraft.theWorld.getBlockState(bp.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.blockpos = bp.add(1, 0, 0);
            this.facing = EnumFacing.WEST;
        } else if (Minecraft.theWorld.getBlockState(bp.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.blockpos = bp.add(0, 0, -1);
            this.facing = EnumFacing.SOUTH;
        } else if (Minecraft.theWorld.getBlockState(bp.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.blockpos = bp.add(0, 0, 1);
            this.facing = EnumFacing.NORTH;
        } else {
            bp = null;
            this.facing = null;
        }
    }

    private void sendCurrentItem() {
        Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(Minecraft.thePlayer.inventory.currentItem));
    }

    private int getBlockItem() {
        int block = -1;
        int i = 8;
        while (i >= 0) {
            if (Minecraft.thePlayer.inventory.getStackInSlot(i) != null && Minecraft.thePlayer.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && (Minecraft.thePlayer.getHeldItem() == Minecraft.thePlayer.inventory.getStackInSlot(i) || this.silent.getValueState().booleanValue())) {
                block = i;
            }
            --i;
        }
        return block;
    }

    public BlockData getBlockData(BlockPos pos, int i) {
        return Minecraft.theWorld.getBlockState(pos.add(0, 0, i)).getBlock() != Blocks.air ? new BlockData(pos.add(0, 0, i), EnumFacing.NORTH) : (Minecraft.theWorld.getBlockState(pos.add(0, 0, - i)).getBlock() != Blocks.air ? new BlockData(pos.add(0, 0, - i), EnumFacing.SOUTH) : (Minecraft.theWorld.getBlockState(pos.add(i, 0, 0)).getBlock() != Blocks.air ? new BlockData(pos.add(i, 0, 0), EnumFacing.WEST) : (Minecraft.theWorld.getBlockState(pos.add(- i, 0, 0)).getBlock() != Blocks.air ? new BlockData(pos.add(- i, 0, 0), EnumFacing.EAST) : (Minecraft.theWorld.getBlockState(pos.add(0, - i, 0)).getBlock() != Blocks.air ? new BlockData(pos.add(0, - i, 0), EnumFacing.UP) : null))));
    }

    public Vec3 getBlockSide(BlockPos pos, EnumFacing face) {
        return face == EnumFacing.NORTH ? new Vec3(pos.getX(), pos.getY(), (double)pos.getZ() - 0.5) : (face == EnumFacing.EAST ? new Vec3((double)pos.getX() + 0.5, pos.getY(), pos.getZ()) : (face == EnumFacing.SOUTH ? new Vec3(pos.getX(), pos.getY(), (double)pos.getZ() + 0.5) : (face == EnumFacing.WEST ? new Vec3((double)pos.getX() - 0.5, pos.getY(), pos.getZ()) : new Vec3(pos.getX(), pos.getY(), pos.getZ()))));
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.sendCurrentItem();
        this.mc.gameSettings.keyBindSneak.pressed = false;
        Timer.timerSpeed = 1.0f;
    }

    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        int[] var10000 = $SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($SWITCH_TABLE$net$minecraft$util$EnumFacing != null) {
            return var10000;
        }
        int[] var0 = new int[EnumFacing.values().length];
        try {
            var0[EnumFacing.DOWN.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            var0[EnumFacing.EAST.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            var0[EnumFacing.NORTH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            var0[EnumFacing.SOUTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            var0[EnumFacing.UP.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            var0[EnumFacing.WEST.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        $SWITCH_TABLE$net$minecraft$util$EnumFacing = var0;
        return var0;
    }

    public class BlockData {
        public BlockPos pos;
        public EnumFacing face;

        public BlockData(BlockPos position, EnumFacing face) {
            this.pos = position;
            this.face = face;
        }
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
