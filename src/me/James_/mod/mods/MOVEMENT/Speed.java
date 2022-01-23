package me.James_.mod.mods.MOVEMENT;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventMove;
import me.James_.events.EventPostMotion;
import me.James_.events.EventPreMotion;
import me.James_.events.EventUpdate;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.mod.mods.COMBAT.Killaura;
import me.James_.util.ClientUtil;
import me.James_.util.PlayerUtil;
import me.James_.util.timeUtils.TimeHelper;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

public class Speed extends Mod {
	   TimeHelper ticks = new TimeHelper();
	   TimeHelper other = new TimeHelper();
	   public static Value mode = new Value("Speed", "Mode", 0);
	   private double posY;
	   private int tick;
	   private double moveSpeed;
	   private int speedTick;
	   private boolean firstjump;
	   private boolean legitHop = false;
	private double basespeed;
	private int stage;
	private double lastDist;
	private double distance;


	   public Speed() {
	      super("Speed", Category.MOVEMENT);
	      mode.mode.add("AAC");
	      mode.mode.add("AAC198");
	      mode.mode.add("AACLowhop");
	      mode.mode.add("QiuYue");
	      mode.mode.add("GudHop");
	      mode.mode.add("Hypixel");
	      mode.mode.add("Mineplex");
	      this.showValue = mode;
	   }

	   @EventTarget
	   public void onPost(EventPostMotion event) {
	      this.mc.thePlayer.cameraPitch = 0.0F;
	   }

	   @EventTarget
	   public void onUpdate(EventUpdate event) {
	      if(mode.isCurrentMode("GudHop")) {
	    	 this.setDisplayName("GudHop");
	         if(this.mc.thePlayer.onGround && PlayerUtil.MovementInput() && !this.mc.thePlayer.isInWater()) {
	            this.mc.timer.timerSpeed = 1.0F;
	            this.mc.thePlayer.jump();
	         } else if(PlayerUtil.MovementInput() && !this.mc.thePlayer.isInWater()) {
	            PlayerUtil.setSpeed(0.8D);
	         }

	         if(!PlayerUtil.MovementInput()) {
	            this.mc.thePlayer.motionX = this.mc.thePlayer.motionZ = 0.0D;
	         }
	      } else if(mode.isCurrentMode("AAC")) {
	    	  this.setDisplayName("AAC");
	         if(PlayerUtil.MovementInput()) {
	            if(this.mc.thePlayer.hurtTime < 1) {
	               if(this.mc.thePlayer.onGround) {
	                  if(!this.firstjump) {
	                     this.firstjump = true;
	                  }

	                  this.mc.thePlayer.jump();
	                  this.mc.thePlayer.motionY = 0.405D;
	                  
	               } else {
	                  this.firstjump = false;
	                  this.mc.thePlayer.motionY -= 0.0149D;
	               }
	            }
	         } else {
	            this.mc.thePlayer.motionX = this.mc.thePlayer.motionZ = (double)(this.tick = 0);
	         }
	         PlayerUtil.setSpeed(PlayerUtil.getSpeed());
	      }else if(mode.isCurrentMode("AACLowhop")) {
	    	  this.setDisplayName("AACLowhop");
	          if(PlayerUtil.MovementInput()) {
	              if(this.mc.thePlayer.hurtTime < 1) {
	                  if(this.mc.thePlayer.onGround) {
	                     if(!this.firstjump) {
	                        this.firstjump = true;
	                     }

	                     this.mc.thePlayer.jump();
	                     this.mc.thePlayer.motionY = 0.15D;
	                     
	                  } else {
	                     this.firstjump = false;
	                     this.mc.thePlayer.motionY -= 0.8;
	                  }
	               }
	          }
	          PlayerUtil.setSpeed(PlayerUtil.getSpeed());
	       }else if(mode.isCurrentMode("Mineplex")) {
			   this.setDisplayName("Mineplex");
	           this.mc.timer.timerSpeed = 1.1F;
	           if(PlayerUtil.MovementInput()) {
	               if(Minecraft.getMinecraft().thePlayer.onGround) {
	                   Minecraft.getMinecraft().thePlayer.jump();
	                   if(Killaura.curTarget == null) {
	                       this.mc.thePlayer.motionY = 0.4229;
	                       PlayerUtil.setSpeed(0.475);
	                   }else {
	                       PlayerUtil.setSpeed(0.41);
	                   }
	                }else {
	             	   this.mc.thePlayer.motionY -= 0.001D;
	                }
	           }
	           PlayerUtil.setSpeed(PlayerUtil.getSpeed());
	       }else if(mode.isCurrentMode("AAC198")) {
	    	  this.setDisplayName("AAC198");
	          if(PlayerUtil.MovementInput()) {
	              if(this.mc.thePlayer.hurtTime < 1) {
	                 if(this.mc.thePlayer.onGround) {
	                    if(!this.firstjump) {
	                       this.firstjump = true;
	                    }

	                    this.mc.thePlayer.jump();
	                    this.mc.thePlayer.motionY = 0.42D;
	                    
	                 } else {
	                    this.firstjump = false;
	                 }
	              }
	           } else {
	              this.mc.thePlayer.motionX = this.mc.thePlayer.motionZ = (double)(this.tick = 0);
	           }

	          PlayerUtil.setSpeed(PlayerUtil.getSpeed());
	       }else if(mode.isCurrentMode("QiuYue")) {
	    	  this.setDisplayName("QiuYue");
	          if(PlayerUtil.MovementInput()) {
	              if(this.mc.thePlayer.hurtTime < 1) {
	                 if(this.mc.thePlayer.onGround) {
	                    if(!this.firstjump) {
	                       this.firstjump = true;
	                    }

	                    this.mc.thePlayer.jump();
	                    this.mc.thePlayer.motionY = 0.405D;
	                    
	                 } else {
	                    this.firstjump = false;
	                    this.mc.thePlayer.motionY -= 0.0135D;
	                 }
	              }
	           } else {
	              this.mc.thePlayer.motionX = this.mc.thePlayer.motionZ = (double)(this.tick = 0);
	           }

	          PlayerUtil.setSpeed(PlayerUtil.getSpeed());
	       }

	   }

		public int getRandom(int cap) {
	        Random rng = new Random();
	        return rng.nextInt(cap);
	    }
		
		public double round(double value, int places) {
	        if (places < 0) {
	            throw new IllegalArgumentException();
	        }
	        BigDecimal bd2 = new BigDecimal(value);
	        bd2 = bd2.setScale(places, RoundingMode.HALF_UP);
	        return bd2.doubleValue();
	    }
	   
	    public boolean isBlockUnder(Material blockMaterial) {
	        return this.mc.theWorld.getBlockState(this.underPlayer()).getBlock().getMaterial() == blockMaterial;
	    }
	    
	    public BlockPos underPlayer() {
	        return new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.getEntityBoundingBox().minY - 1.0, this.mc.thePlayer.posZ);
	    }
		
	    private boolean canZoom() {
	        if (PlayerUtil.MovementInput() && this.mc.thePlayer.onGround) {
	            return true;
	        }
	        return false;
	    }
	    
	    public Value<Boolean> lagback = new Value("Speed_LagBackChecks",false);

	    
	   @EventTarget
	   public void onMove(EventMove e) {
	   	if(this.mode.isCurrentMode("Hypixel")) {
	   		this.setDisplayName("Hypixel");
	        if (this.canZoom() && this.stage == 1) {
	            this.moveSpeed = 1.56 * PlayerUtil.getBaseMoveSpeed() - 0.01;
	            this.mc.timer.timerSpeed = 1.15f;
	        } else if (this.canZoom() && this.stage == 2) {
	            this.mc.thePlayer.motionY = 0.3999;
	            e.setY(0.3999);
	            this.moveSpeed *= 1.58;
	            this.mc.timer.timerSpeed = 1.2f;
	        } else if (this.stage == 3) {
	            double difference = 0.66 * (this.distance - PlayerUtil.getBaseMoveSpeed());
	            this.moveSpeed = this.distance - difference;
	            this.mc.timer.timerSpeed = 1.1f;
	        } else {
	            List collidingList = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0));
	            if (collidingList.size() > 0 || this.mc.thePlayer.isCollidedVertically && this.stage > 0) {
	                this.stage = PlayerUtil.MovementInput() ? 1 : 0;
	            }
	            this.moveSpeed = this.distance - this.distance / 159.0;
	        }
	        this.moveSpeed = Math.max(this.moveSpeed, PlayerUtil.getBaseMoveSpeed());
	        
	        if (PlayerUtil.MovementInput()) {
	        	PlayerUtil.setSpeed(moveSpeed);
	            ++this.stage;
	        }
	   	}
	   }
	   
	   public void goToGround()
	   {
	     double minY = mc.thePlayer.posY;
	     if (minY <= 0.0D) {
	       return;
	     }
	     for (double y = mc.thePlayer.posY; y > minY;)
	     {
	       y -= 8.0D;
	       if (y < minY) {
	         y = minY;
	       }
	       
	       C03PacketPlayer.C04PacketPlayerPosition packet = new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true);
	       
	       mc.thePlayer.sendQueue.addToSendQueue(packet);
	     }
	     for (double y = minY; y < mc.thePlayer.posY;)
	     {
	       y += 8.0D;
	       if (y > mc.thePlayer.posY) {
	         y = mc.thePlayer.posY;
	       }
	       
	       C03PacketPlayer.C04PacketPlayerPosition packet = new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true);
	       
	       mc.thePlayer.sendQueue.addToSendQueue(packet);
	     }
	   }
	   
	   @Override
	   public void onDisable() {
	       this.mc.thePlayer.motionX *= 1.0;
	       this.mc.thePlayer.motionY *= 1.0;
	       this.mc.thePlayer.motionZ *= 1.0;
	       mc.timer.timerSpeed = 1.0f;
	       mc.thePlayer.speedInAir = 0.02f;
	       super.onDisable();
	   } 

	   @Override
	   public void onEnable() {
	       this.mc.thePlayer.motionX *= 0.0;
	       this.mc.thePlayer.motionZ *= 0.0;
	       super.onEnable();
	   }

	   private double getBaseMoveSpeed() {
	      double baseSpeed = 0.2873D;
	      if(this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
	         int amplifier = this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
	         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
	      }

	      return baseSpeed;
	   }

	   private void strafe(float speed) {
	      boolean isMoving = this.mc.thePlayer.moveForward != 0.0F || this.mc.thePlayer.moveStrafing != 0.0F;
	      boolean isMovingForward = this.mc.thePlayer.moveForward > 0.0F;
	      boolean isMovingBackward = this.mc.thePlayer.moveForward < 0.0F;
	      boolean isMovingRight = this.mc.thePlayer.moveStrafing > 0.0F;
	      boolean isMovingLeft = this.mc.thePlayer.moveStrafing < 0.0F;
	      boolean isMovingSideways = isMovingLeft || isMovingRight;
	      boolean isMovingStraight = isMovingForward || isMovingBackward;
	      if(isMoving) {
	         double yaw = (double)this.mc.thePlayer.rotationYaw;
	         if(isMovingForward && !isMovingSideways) {
	            yaw += 0.0D;
	         } else if(isMovingBackward && !isMovingSideways) {
	            yaw += 180.0D;
	         } else if(isMovingForward && isMovingLeft) {
	            yaw += 45.0D;
	         } else if(isMovingForward) {
	            yaw -= 45.0D;
	         } else if(!isMovingStraight && isMovingLeft) {
	            yaw += 90.0D;
	         } else if(!isMovingStraight && isMovingRight) {
	            yaw -= 90.0D;
	         } else if(isMovingBackward && isMovingLeft) {
	            yaw += 135.0D;
	         } else if(isMovingBackward) {
	            yaw -= 135.0D;
	         }

	         yaw = Math.toRadians(yaw);
	         this.mc.thePlayer.motionX = -Math.sin(yaw) * (double)speed;
	         this.mc.thePlayer.motionZ = Math.cos(yaw) * (double)speed;
	      }

	   }
	}
