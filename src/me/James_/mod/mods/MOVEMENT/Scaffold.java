package me.James_.mod.mods.MOVEMENT;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventMove;
import me.James_.events.EventPlayerUpdate;
import me.James_.events.EventPostMotion;
import me.James_.events.EventPreMotion;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.util.ClientUtil;
import me.James_.util.PlayerUtil;
import me.James_.util.RotationUtils;
import me.James_.util.timeUtils.TimeHelper;
import me.James_.util.timeUtils.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class Scaffold extends Mod {
	private TimeHelper timer = new TimeHelper();
	private TimeHelper timer2 = new TimeHelper();
	private Value<Double> delay = new Value("Scaffold_Delay", 250.0D, 0.0D, 1000.0D, 1.0D);
	
    private Value<Boolean> tower = new Value<Boolean>("Scaffold_Tower", true);
    private Value<Boolean> noswing = new Value<Boolean>("Scaffold_NoSwing", true);
    private Value<Boolean> silent = new Value<Boolean>("Scaffold_Silent", true);
    private Value<Boolean> nosprint = new Value<Boolean>("Scaffold_NoSprint", false);
    private List<Block> invalid = Arrays.asList(Blocks.air, Blocks.water, Blocks.fire, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.chest, Blocks.enchanting_table, Blocks.tnt);
    private BlockCache blockCache;
    private int currentItem;

    public Scaffold() {
        super("Scaffold", Category.MOVEMENT);
        currentItem = 0;
    }
    
    public Value<Boolean> lagback = new Value("Scaffold_LagBackChecks",false);

    @Override
    public void onEnable() {
        currentItem = mc.thePlayer.inventory.currentItem;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.thePlayer.inventory.currentItem = currentItem;
        super.onDisable();
    }

    @EventTarget
    public void onPre(EventPreMotion event) {
    	PlayerUtil.setSpeed(PlayerUtil.getSpeed() * 0.89975);
        if (nosprint.getValueState()) {
            mc.thePlayer.setSprinting(false);
        }
        
        if (grabBlockSlot() == -1) {
            return;
        }
        
        blockCache = grab();
        
        if (blockCache == null) {
            return;
        }
        
        float[] rotations = RotationUtils.grabBlockRotations(blockCache.getPosition());
        event.setYaw(rotations[0] - 1 + new Random().nextFloat() * 2);
        event.pitch = 81F + new Random().nextFloat() * 2;
        
        if(Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) event.pitch = 86F + new Random().nextFloat() * 3;
        
    }

    @EventTarget
    private void onPost(EventPostMotion event) {
    	if(!timer.isDelayComplete(delay.getValueState().longValue())) return;
    	
        int slot;
        
        if (blockCache == null) {
            return;
        }
        
        int currentSlot = mc.thePlayer.inventory.currentItem;
        
        mc.thePlayer.inventory.currentItem = slot = grabBlockSlot();
        
        
        
        if (placeBlock(blockCache.position, blockCache.facing)) {
        	timer.reset();
        	
            if (silent.getValueState()) {
                mc.thePlayer.inventory.currentItem = currentSlot;
                mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(currentSlot));
            }
            blockCache = null;
            
        	if(tower.getValueState()) {
          		if(Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) {
              		Minecraft.getMinecraft().thePlayer.jump();
          			PlayerUtil.setSpeed(0);
          		}
              	if(timer2.hasReached(1000) && Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) {
              		Minecraft.getMinecraft().thePlayer.motionY = -0.28D;
              		timer2.reset();
              	} 
              	if(!Minecraft.getMinecraft().gameSettings.keyBindJump.pressed) {
              		timer2.reset();
              	}
            }
        }
    }

    private boolean placeBlock(BlockPos pos, EnumFacing facing) {
        Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        
        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), 
        		pos, facing, new Vec3(blockCache.position).addVector(
        				0.1 + new Random().nextFloat() / 6, 
        				0.1 + new Random().nextFloat() / 60, 
        				0.1 + new Random().nextFloat() / 6).add(new Vec3(blockCache.facing.getDirectionVec()).scale(0.4 + new Random().nextFloat() / 6)))) {

            if(noswing.getValueState()) {
            	mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            }else {
            	mc.thePlayer.swingItem();
            }
            
            return true;
        }
        return false;
    }

    private Vec3 grabPosition(BlockPos position, EnumFacing facing) {
        Vec3 offset = new Vec3(
        		facing.getDirectionVec().getX() / 2.0 + new Random().nextFloat() / 6,
        		facing.getDirectionVec().getY() / 2.0 + new Random().nextFloat() / 60, 
        		facing.getDirectionVec().getZ() / 2.0 + new Random().nextFloat() / 6);
        
        Vec3 point = new Vec3(
        		position.getX() + 0.5 + new Random().nextFloat() / 6, 
        		position.getY() + 0.5 + new Random().nextFloat() / 60, 
        		position.getZ() + 0.5 + new Random().nextFloat() / 6);
        return point.add(offset);
    }

    private BlockCache grab() {
        EnumFacing[] invert = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST};
        
        BlockPos position = (new BlockPos(this.mc.thePlayer.getPositionVector())).offset(EnumFacing.DOWN);
        
        if(!(mc.theWorld.getBlockState(position).getBlock() instanceof BlockAir)) {
           return null;
        } else {
           EnumFacing[] var6;
           int var5 = (var6 = EnumFacing.values()).length;

           for(int offset = 0; offset < var5; ++offset) {
              EnumFacing offsets = var6[offset];
              BlockPos offset1 = position.offset(offsets);
              this.mc.theWorld.getBlockState(offset1);
              
              if(!(mc.theWorld.getBlockState(offset1).getBlock() instanceof BlockAir)) {
                 return new BlockCache(offset1, invert[offsets.ordinal()]);
              }
           }

           BlockPos[] var16 = new BlockPos[]{
        		   new BlockPos(-1 + new Random().nextFloat() / 6, new Random().nextFloat() / 6, new Random().nextFloat() / 6), 
        		   new BlockPos(1 + new Random().nextFloat() / 6, new Random().nextFloat() / 6, new Random().nextFloat() / 6), 
        		   new BlockPos(new Random().nextFloat() / 6, new Random().nextFloat() / 6, -1 + new Random().nextFloat() / 6), 
        		   new BlockPos(new Random().nextFloat() / 6, new Random().nextFloat() / 6, 1 + new Random().nextFloat() / 6)};
           
           if(mc.thePlayer.onGround) {
              BlockPos[] var19 = var16;
              int var18 = var16.length;

              for(var5 = 0; var5 < var18; ++var5) {
                 BlockPos var17 = var19[var5];
                 BlockPos offsetPos = position.add(var17.getX(), 0, var17.getZ());
                 mc.theWorld.getBlockState(offsetPos);
                 
                 if(mc.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
                    EnumFacing[] var13;
                    int var12 = (var13 = EnumFacing.values()).length;

                    for(int var11 = 0; var11 < var12; ++var11) {
                       EnumFacing facing2 = var13[var11];
                       BlockPos offset2 = offsetPos.offset(facing2);
                       mc.theWorld.getBlockState(offset2);
                       
                       if(!(mc.theWorld.getBlockState(offset2).getBlock() instanceof BlockAir)) {
                          return new BlockCache(offset2, invert[facing2.ordinal()]);
                       }
                       
                    }
                 }
              }
           }
           return null;
        }
     }


    private int grabBlockSlot() {
        int i = 0;
        while (i < 9) {
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
                return i;
            }
            ++i;
        }
        return -1;
    }

}

class BlockCache {
    BlockPos position;
    EnumFacing facing;

    BlockCache(BlockPos position1, EnumFacing facing1) {
        position = position1;
        facing = facing1;
    }

    BlockPos getPosition() {
        return this.position;
    }

    EnumFacing getFacing() {
        return this.facing;
    }

    static BlockPos access$0(BlockCache blockCache) {
        return blockCache.getPosition();
    }

    static EnumFacing offset(BlockCache blockCache) {
        return blockCache.getFacing();
    }

    static BlockPos access$2(BlockCache blockCache) {
        return blockCache.position;
    }

    static EnumFacing access$3(BlockCache blockCache) {
        return blockCache.facing;
    }
}

