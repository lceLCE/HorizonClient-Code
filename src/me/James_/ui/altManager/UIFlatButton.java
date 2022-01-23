package me.James_.ui.altManager;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.James_.Client;
import me.James_.util.timeUtils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

public class UIFlatButton extends GuiButton {
	private TimeHelper time = new TimeHelper();
    public String displayString;
    public int id;
    public boolean enabled;
    public boolean visible;
    protected boolean hovered;
    private int color;
    private float opacity;
    private FontRenderer font;

    public UIFlatButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int color) {
        super(buttonId, x, y, 10, 12, buttonText);
        this.width = widthIn;
        this.height = heightIn;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.displayString = buttonText;
        this.color = color;
        this.font = Minecraft.getMinecraft().fontRendererObj;
    }

    public UIFlatButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int color, FontRenderer font) {
        super(buttonId, x, y, 10, 12, buttonText);
        this.width = widthIn;
        this.height = heightIn;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.displayString = buttonText;
        this.color = color;
        this.font = font;
    }

    @Override
    protected int getHoverState(boolean mouseOver) {
        int i = 1;
        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }
        return i;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int var5 = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            if (!this.hovered) {
                this.time.reset();
                this.opacity = 0.0f;
            }
            if (this.hovered) {
                this.opacity += 0.5f;
                if (this.opacity > 1.0f) {
                    this.opacity = 1.0f;
                }
            }
            float radius = (float)this.height / 2.0f;
            drawRoundedRect((float)this.xPosition - this.opacity * 0.1f, (float)this.yPosition - this.opacity, (float)(this.xPosition + this.width) + this.opacity * 0.1f, (float)this.yPosition + radius * 2.0f + this.opacity, 1.0f, this.color);
            GL11.glColor3f((float)2.55f, (float)2.55f, (float)2.55f);
            this.mouseDragged(mc, mouseX, mouseY);
            GL11.glPushMatrix();
            GL11.glPushAttrib((int)1048575);
            GL11.glScaled((double)1.0, (double)1.0, (double)1.0);
            int var6 = -1;
            float textHeight = this.font.FONT_HEIGHT;
            Client.fontManager.tahoma20.drawCenteredString(this.displayString, this.xPosition + this.width / 2 - 2, (int) ((float)this.yPosition + (float)(this.height - this.font.FONT_HEIGHT) / 2.0f) - 2, this.hovered ? -1 : -3487030);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }
    }

    private Color darkerColor(Color c, int step) {
        int red = c.getRed();
        int blue = c.getBlue();
        int green = c.getGreen();
        if (red >= step) {
            red -= step;
        }
        if (blue >= step) {
            blue -= step;
        }
        if (green >= step) {
            green -= step;
        }
        return c.darker();
    }

    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isMouseOver() {
        return this.hovered;
    }

    @Override
    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
    }

    @Override
    public int getButtonWidth() {
        return this.width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void drawRoundedRect(float x, float y, float x2, float y2, float round, int color) {
        x = (float)((double)x + ((double)(round / 2.0f) + 0.5));
        y = (float)((double)y + ((double)(round / 2.0f) + 0.5));
        x2 = (float)((double)x2 - ((double)(round / 2.0f) + 0.5));
        y2 = (float)((double)y2 - ((double)(round / 2.0f) + 0.5));
        drawRect(x, y, x2, y2, color);
        circle(x2 - round / 2.0f, y + round / 2.0f, round, color);
        circle(x + round / 2.0f, y2 - round / 2.0f, round, color);
        circle(x + round / 2.0f, y + round / 2.0f, round, color);
        circle(x2 - round / 2.0f, y2 - round / 2.0f, round, color);
        drawRect(x - round / 2.0f - 0.5f, y + round / 2.0f, x2, y2 - round / 2.0f, color);
        drawRect(x, y + round / 2.0f, x2 + round / 2.0f + 0.5f, y2 - round / 2.0f, color);
        drawRect(x + round / 2.0f, y - round / 2.0f - 0.5f, x2 - round / 2.0f, y2 - round / 2.0f, color);
        drawRect(x + round / 2.0f, y, x2 - round / 2.0f, y2 + round / 2.0f + 0.5f, color);
    }
}
