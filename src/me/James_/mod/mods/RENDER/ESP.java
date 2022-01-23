package me.James_.mod.mods.RENDER;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Value;
import me.James_.events.EventRender;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.mod.mods.MISC.Teams;
import me.James_.util.Colors;
import me.James_.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ESP extends Mod {
	
	public Value<String> mode = new Value("ESP", "Mode", 0);

	public ESP() {
		super("ESP", Category.RENDER);
		ArrayList<String> settings = new ArrayList();
		this.mode.mode.add("Box");
		this.mode.mode.add("2D");
		this.mode.mode.add("Outline");
	}
	
	public static boolean isOutline = false;
	
	@Override
	public void onDisable() {
		isOutline = false;
		super.onDisable();
	}
	
	@EventTarget
	public void onRender(EventRender event) {
		if(this.mode.isCurrentMode("Box")) {
			this.setDisplayName("Box");
			this.doBoxESP(event);
		}
		if(this.mode.isCurrentMode("2D")) {
			this.setDisplayName("2D");
			this.doOther2DESP();
		}
		if(this.mode.isCurrentMode("Outline")) {
			this.setDisplayName("Outline");
			isOutline = true;
		} else {
			isOutline = false;
		}
	}
	
	private void doBoxESP(EventRender event) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        for (Object o : this.mc.theWorld.loadedEntityList) {
            if(o instanceof EntityPlayer && o != mc.thePlayer) {
            	EntityPlayer ent = (EntityPlayer)o;
            	if(Teams.isOnSameTeam(ent)) {
            		RenderUtil.entityESPBox(ent, new Color(0,255,0), event);
            	} else if(ent.hurtTime > 0) {
            		RenderUtil.entityESPBox(ent, new Color(255,0,0), event);
            	} else if(ent.isInvisible()) {
            		RenderUtil.entityESPBox(ent, new Color(255,255,0), event);
            	} else {
            		RenderUtil.entityESPBox(ent, new Color(255,255,255), event);
            	}
            }
        }
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
    }
	
	private boolean isValid(EntityLivingBase entity) {
		if(entity instanceof EntityPlayer && entity.getHealth() >= 0.0f && entity != mc.thePlayer) {
			return true;
		}
		return false;
	}
	
	private void doOther2DESP() {
        for (EntityPlayer entity : this.mc.theWorld.playerEntities) {
            if (isValid(entity)) {
                GL11.glPushMatrix();
                GL11.glEnable(3042);
                GL11.glDisable(2929);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                GlStateManager.enableBlend();
                GL11.glBlendFunc(770, 771);
                GL11.glDisable(3553);
                float partialTicks = mc.timer.renderPartialTicks;
                this.mc.getRenderManager();
                double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - RenderManager.renderPosX;
                this.mc.getRenderManager();
                double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - RenderManager.renderPosY;
                this.mc.getRenderManager();
                double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - RenderManager.renderPosZ;
                float DISTANCE = this.mc.thePlayer.getDistanceToEntity(entity);
                float DISTANCE_SCALE = Math.min(DISTANCE * 0.15f, 0.15f);
                float SCALE = 0.035f;
                SCALE /= 2.0f;
                float xMid = (float)x;
                float yMid = (float)y + entity.height + 0.5f - (entity.isChild() ? (entity.height / 2.0f) : 0.0f);
                float zMid = (float)z;
                GlStateManager.translate((float)x, (float)y + entity.height + 0.5f - (entity.isChild() ? (entity.height / 2.0f) : 0.0f), (float)z);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
                GL11.glScalef(-SCALE, -SCALE, -SCALE);
                Tessellator tesselator = Tessellator.getInstance();
                WorldRenderer worldRenderer = tesselator.getWorldRenderer();
                float HEALTH = entity.getHealth();
                int COLOR = -1;
                if (HEALTH > 20.0) {
                    COLOR = -65292;
                }
                else if (HEALTH >= 10.0) {
                    COLOR = -16711936;
                }
                else if (HEALTH >= 3.0) {
                    COLOR = -23296;
                }
                else {
                    COLOR = -65536;
                }
                Color gray = new Color(0, 0, 0);
                double thickness = 1.5f + DISTANCE * 0.01f;
                double xLeft = -20.0;
                double xRight = 20.0;
                double yUp = 27.0;
                double yDown = 130.0;
                double size = 10.0;
                Color color = new Color(255, 255, 255);
                if (entity.hurtTime > 0) {
                    color = new Color(255, 0, 0);
                } else if(Teams.isOnSameTeam(entity)) {
                	color = new Color(0, 255, 0);
                } else if(entity.isInvisible()) {
                	color = new Color(255,255,0);
                }
                drawBorderedRect((float)xLeft, (float)yUp, (float)xRight, (float)yDown, (float)thickness + 0.5f, Colors.BLACK.c, 0);
                drawBorderedRect((float)xLeft, (float)yUp, (float)xRight, (float)yDown, (float)thickness, color.getRGB(), 0);
                drawBorderedRect((float)xLeft - 3.0f - DISTANCE * 0.2f, (float)yDown - (float)(yDown - yUp), (float)xLeft - 2.0f, (float)yDown, 0.15f, Colors.BLACK.c, new Color(100, 100, 100).getRGB());
                drawBorderedRect((float)xLeft - 3.0f - DISTANCE * 0.2f, (float)yDown - (float)(yDown - yUp) * Math.min(1.0f, entity.getHealth() / 20.0f), (float)xLeft - 2.0f, (float)yDown, 0.15f, Colors.BLACK.c, COLOR);
                GL11.glEnable(3553);
                GL11.glEnable(2929);
                GlStateManager.disableBlend();
                GL11.glDisable(3042);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glNormal3f(1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            }
        }
    }
	
	public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
	      drawRect(x, y, x2, y2, col2);
	      float f = (float)(col1 >> 24 & 255) / 255.0F;
	      float f1 = (float)(col1 >> 16 & 255) / 255.0F;
	      float f2 = (float)(col1 >> 8 & 255) / 255.0F;
	      float f3 = (float)(col1 & 255) / 255.0F;
	      GL11.glEnable(3042);
	      GL11.glDisable(3553);
	      GL11.glBlendFunc(770, 771);
	      GL11.glEnable(2848);
	      GL11.glPushMatrix();
	      GL11.glColor4f(f1, f2, f3, f);
	      GL11.glLineWidth(l1);
	      GL11.glBegin(1);
	      GL11.glVertex2d((double)x, (double)y);
	      GL11.glVertex2d((double)x, (double)y2);
	      GL11.glVertex2d((double)x2, (double)y2);
	      GL11.glVertex2d((double)x2, (double)y);
	      GL11.glVertex2d((double)x, (double)y);
	      GL11.glVertex2d((double)x2, (double)y);
	      GL11.glVertex2d((double)x, (double)y2);
	      GL11.glVertex2d((double)x2, (double)y2);
	      GL11.glEnd();
	      GL11.glPopMatrix();
	      GL11.glEnable(3553);
	      GL11.glDisable(3042);
	      GL11.glDisable(2848);
	   }
	
	public static void drawRect(float g, float h, float i, float j, int col1) {
	      float f = (float)(col1 >> 24 & 255) / 255.0F;
	      float f1 = (float)(col1 >> 16 & 255) / 255.0F;
	      float f2 = (float)(col1 >> 8 & 255) / 255.0F;
	      float f3 = (float)(col1 & 255) / 255.0F;
	      GL11.glEnable(3042);
	      GL11.glDisable(3553);
	      GL11.glBlendFunc(770, 771);
	      GL11.glEnable(2848);
	      GL11.glPushMatrix();
	      GL11.glColor4f(f1, f2, f3, f);
	      GL11.glBegin(7);
	      GL11.glVertex2d((double)i, (double)h);
	      GL11.glVertex2d((double)g, (double)h);
	      GL11.glVertex2d((double)g, (double)j);
	      GL11.glVertex2d((double)i, (double)j);
	      GL11.glEnd();
	      GL11.glPopMatrix();
	      GL11.glEnable(3553);
	      GL11.glDisable(3042);
	      GL11.glDisable(2848);
	}
	
	public void pre() {
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
    }

    public void post() {
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glColor3d((double)1.0, (double)1.0, (double)1.0);
    }
	
}