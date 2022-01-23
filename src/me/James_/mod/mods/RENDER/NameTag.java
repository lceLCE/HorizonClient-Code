package me.James_.mod.mods.RENDER;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.events.EventRender;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.mod.mods.COMBAT.Antibot;
import me.James_.mod.mods.MISC.Teams;
import me.James_.util.GLUtil;
import me.James_.util.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class NameTag extends Mod {
	
	public NameTag() {
		super("NameTag", Category.RENDER);
	}
	
	@EventTarget
	public void onRender(EventRender event) {
		for (Object o : mc.theWorld.playerEntities) {
			EntityPlayer p = (EntityPlayer) o;
			if(p != mc.thePlayer && p.isEntityAlive()) {
				double pX = p.lastTickPosX + (p.posX - p.lastTickPosX) * mc.timer.renderPartialTicks
                        - RenderManager.renderPosX;
                double pY = p.lastTickPosY + (p.posY - p.lastTickPosY) * mc.timer.renderPartialTicks
                        - RenderManager.renderPosY;
                double pZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * mc.timer.renderPartialTicks
                        - RenderManager.renderPosZ;
                renderNameTag(p, p.getName(), pX, pY, pZ);
			}
		}
	}
	
	private void renderNameTag(EntityPlayer entity, String tag, double pX, double pY, double pZ) {
        FontRenderer fr = mc.fontRendererObj;
        float size = mc.thePlayer.getDistanceToEntity(entity) / 6.0f;
		if(size < 1.1f) {
			size = 1.1f;
		}
		pY += (entity.isSneaking() ? 0.5D : 0.7D);
		float scale = size * 2.0f;
		scale /= 100f;
        tag = entity.getName();
        
        String bot = "";
		if(Antibot.invalid.contains(entity)) {
			bot = "\2479";
		} else {
			bot = "";
		}
		
		String team = "";
		if(Teams.isOnSameTeam(entity)) {
			team = "\247a";
		} else {
			team = "";
		}
		String lol = team + bot + tag;
		double plyHeal = entity.getHealth();
		String hp = "Health:" + (int)plyHeal + " Dist:" + (int)entity.getDistanceToEntity(mc.thePlayer) + "m";
        GL11.glPushMatrix();
        GL11.glTranslatef((float) pX, (float) pY + 1.4F, (float) pZ);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-scale, -scale, scale);
        GLUtil.setGLCap(2896, false);
        GLUtil.setGLCap(2929, false);
        int width = 31;
        GLUtil.setGLCap(3042, true);
        GL11.glBlendFunc(770, 771);

        drawBorderedRectNameTag(-width - 16, -17.5f, width + 10, -0f, 1.0F, 0x55000000, Integer.MIN_VALUE);
        GL11.glColor3f(1,1,1);
		fr.drawString(lol, -width - 15, (int)(-17f), -1);
		GL11.glScaled(0.6f, 0.6f, 0.6f);
		fr.drawString(hp, -width - 46, (int)(-19f - fr.FONT_HEIGHT + 15), -1);
		GL11.glScaled(1, 1, 1);
		int COLOR = new Color(188,0,0).getRGB();
		if(entity.getHealth() > 20) {
			COLOR = -65292;
		}
		int xLeft = (-width / 2 - 66);
		RenderUtil.drawRect(-width - 50.0f + 149.2f * Math.min(1.0f, entity.getHealth() / 20.0f), +0f, -width / 2 - 63.2f, -3f, COLOR);
        GL11.glPushMatrix();
        GL11.glScaled(1.5d, 1.5d, 1.5d);
		if (true) {
			int xOffset = 0;
			for (ItemStack armourStack : entity.inventory.armorInventory) {
				if (armourStack != null)
					xOffset -= 11;
			}
			Object renderStack;
			if (entity.getHeldItem() != null) {
				xOffset -= 8;
				renderStack = entity.getHeldItem().copy();
				if ((((ItemStack) renderStack).hasEffect())
						&& (((((ItemStack) renderStack).getItem() instanceof ItemTool))
								|| ((((ItemStack) renderStack).getItem() instanceof ItemArmor))))
					((ItemStack) renderStack).stackSize = 1;
				renderItemStack((ItemStack) renderStack, xOffset, -37);
				xOffset += 20;
			}
			for (ItemStack armourStack : entity.inventory.armorInventory)
				if (armourStack != null) {
					ItemStack renderStack1 = armourStack.copy();
					if ((renderStack1.hasEffect()) && (((renderStack1.getItem() instanceof ItemTool))
							|| ((renderStack1.getItem() instanceof ItemArmor))))
						renderStack1.stackSize = 1;
					renderItemStack(renderStack1, xOffset, -36);
					xOffset += 20;
				}
		}
        GL11.glPopMatrix();
        GLUtil.revertAllCaps();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }
	
	public void renderItemStack(ItemStack stack, int x, int y) {
		GL11.glPushMatrix();
		GL11.glDepthMask(true);
		GlStateManager.clear(256);
		net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
		this.mc.getRenderItem().zLevel = -150.0F;
		whatTheFuckOpenGLThisFixesItemGlint();
		this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
		this.mc.getRenderItem().renderItemOverlays(this.mc.fontRendererObj, stack, x, y);
		this.mc.getRenderItem().zLevel = 0.0F;
		net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		GlStateManager.disableCull();
		GlStateManager.enableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.disableLighting();
		GlStateManager.scale(0.5D, 0.5D, 0.5D);
		GlStateManager.disableDepth();
		GlStateManager.enableDepth();
		GlStateManager.scale(2.0F, 2.0F, 2.0F);
		GL11.glPopMatrix();
	}
	
	private void whatTheFuckOpenGLThisFixesItemGlint() {
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }
	
	public void drawBorderedRectNameTag(final float x, final float y, final float x2, final float y2, final float l1, final int col1, final int col2) {
        RenderUtil.drawRect(x, y, x2, y2, col2);
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y);
        GL11.glVertex2d((double) x2, (double) y);
        GL11.glVertex2d((double) x, (double) y2);
        GL11.glVertex2d((double) x2, (double) y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
	
	
}
