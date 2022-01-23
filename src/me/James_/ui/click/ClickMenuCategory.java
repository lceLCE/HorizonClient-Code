package me.James_.ui.click;

import java.awt.Color;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import me.James_.Client;
import me.James_.mod.Category;
import me.James_.util.ClientUtil;
import me.James_.util.Colors;
import me.James_.util.RenderUtil;
import me.James_.util.fontRenderer.UnicodeFontRenderer;
import me.James_.util.handler.MouseInputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ClickMenuCategory {
	public Category c;
    ClickMenuMods uiMenuMods;
    private MouseInputHandler handler;
    public boolean open;
    public int x;
    public int y;
    public int width;
    public int tab_height;
    public int x2;
    public int y2;
    public boolean drag = true;
    private double arrowAngle = 0.0;

    public ClickMenuCategory(Category c, int x, int y, int width, int tab_height, MouseInputHandler handler) {
        this.c = c;
        this.x = x;
        this.y = y;
        this.width = width;
        this.tab_height = tab_height;
        this.uiMenuMods = new ClickMenuMods(c, handler);
        this.handler = handler;
    }

    public void draw(int mouseX, int mouseY) {
        boolean hoverArrow;
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        UnicodeFontRenderer font = Client.instance.fontMgr.asu18;
        RenderUtil.drawRoundedRect(this.x, this.y, this.x + this.width +0.5f, this.y + this.tab_height - 1, 0.0f, ClientUtil.reAlpha(Colors.BLACK.c, 0.1F));
        //RenderUtil.drawRoundedRect(this.x, this.y, this.x + this.width, this.y + this.tab_height - 1, 1.5f, new Color(66,165,245).getRGB());
        String name = "";
        name = "   " + this.c.name().substring(0, 1) + this.c.name().toLowerCase().substring(1, this.c.name().length());
        font.drawString(name, (float)this.x + (this.width - font.getStringWidth(name)) / 2, this.y + (this.tab_height - font.FONT_HEIGHT) / 2, ClientUtil.reAlpha(Colors.WHITE.c, 1F));
        double xMid = this.x + this.width - 6;
        double yMid = this.y + 10;
        this.arrowAngle = RenderUtil.getAnimationState(this.arrowAngle, this.uiMenuMods.open ? -90 : 0, 1000.0);
        GL11.glPushMatrix();
        GL11.glTranslated((double)xMid, (double)yMid, (double)0.0);
        GL11.glRotated((double)this.arrowAngle, (double)0.0, (double)0.0, (double)1.0);
        GL11.glTranslated((double)(-xMid), (double)(-yMid), (double)0.0);
        boolean bl = hoverArrow = mouseX >= this.x + this.width - 15 && mouseX <= this.x + this.width - 5 && mouseY >= this.y + 7 && mouseY <= this.y + 17;
        if (hoverArrow) {
            RenderUtil.drawImage(new ResourceLocation("J/icons/arrow-down.png"), this.x + this.width - 9, this.y + 7, 6, 6, new Color(0.7058824f, 0.7058824f, 0.7058824f));
        } else {
            RenderUtil.drawImage(new ResourceLocation("J/icons/arrow-down.png"), this.x + this.width - 9, this.y + 7, 6, 6);
        }
        GL11.glPopMatrix();
        RenderUtil.drawImage(new ResourceLocation("J/icons/panel.png"), this.x + 5, this.y + 6, 8, 8, new Color(255,255,255));
        this.upateUIMenuMods();
        this.width = font.getStringWidth(name) + 50;
        this.uiMenuMods.draw(mouseX, mouseY);
        this.move(mouseX, mouseY);
    }

    private void move(int mouseX, int mouseY) {
        boolean hoverArrow;
        boolean bl = hoverArrow = mouseX >= this.x + this.width - 15 && mouseX <= this.x + this.width - 5 && mouseY >= this.y + 7 && mouseY <= this.y + 17;
        if (!hoverArrow && this.isHovering(mouseX, mouseY) && this.handler.canExcecute()) {
            this.drag = true;
            this.x2 = mouseX - this.x;
            this.y2 = mouseY - this.y;
        }
        if (hoverArrow && this.handler.canExcecute()) {
            boolean bl2 = this.uiMenuMods.open = !this.uiMenuMods.open;
        }
        if (!Mouse.isButtonDown((int)0)) {
            this.drag = false;
        }
        if (this.drag) {
            this.x = mouseX - this.x2;
            this.y = mouseY - this.y2;
        }
    }

    private boolean isHovering(int mouseX, int mouseY) {
        if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.tab_height) {
            return true;
        }
        return false;
    }

    private void upateUIMenuMods() {
        this.uiMenuMods.x = this.x;
        this.uiMenuMods.y = this.y;
        this.uiMenuMods.tab_height = this.tab_height;
        this.uiMenuMods.width = this.width;
    }

    public void mouseClick(int mouseX, int mouseY) {
        this.uiMenuMods.mouseClick(mouseX, mouseY);
    }

    public void mouseRelease(int mouseX, int mouseY) {
        this.uiMenuMods.mouseRelease(mouseX, mouseY);
    }
}
