package me.James_.ui.altManager;

import me.James_.Client;
import me.James_.util.ClientUtil;
import me.James_.util.Colors;
import me.James_.util.FlatColors;
import me.James_.util.RenderUtil;
import me.James_.util.handler.MouseInputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiAltSlot {
    private String username;
    private String password;
    public int x;
    public int y;
    public int WIDTH;
    private ScaledResolution res;
    public static final int HEIGHT = 25;
    private boolean clicked;
    public boolean selected;
    public float opacity;
    public int MIN_HEIGHT;
    public int MAX_HEIGHT;
    private float animation_one;
    private float animation_two;
    private float animation_three;
    private MouseInputHandler handler = new MouseInputHandler(0);

    public GuiAltSlot(String username, String password) {
        this.username = username;
        this.password = password;
        this.x = 12;
    }

    private void update() {
        this.res = new ScaledResolution(Minecraft.getMinecraft());
    }

    public void drawScreen(int mouseX, int mouseY) {
        if (this.y > this.MAX_HEIGHT - 25) {
            return;
        }
        if (this.y < this.MIN_HEIGHT) {
            return;
        }
        FontRenderer font = Minecraft.getMinecraft().fontRendererObj;
        this.update();
        int lightGray = -15066598;
        int stripeWidth = 2;
        Gui.drawRect(this.x, this.y + 1, this.WIDTH - 2, this.y + 25 - 1, ClientUtil.INSTANCE.reAlpha(251658240, 0.025f * this.opacity));
        Gui.drawRect(this.x - stripeWidth, this.y + 1, this.x, this.y - 1 + 25, FlatColors.BLUE.c);
        if (this.isHovering(mouseX, mouseY)) {
            Gui.drawRect(this.x, this.y + 1, this.WIDTH - 2, this.y + 25 - 1, ClientUtil.INSTANCE.reAlpha(251658240, 0.2f * this.opacity));
        }
        String text = this.username;
        String password = this.password;
        Client.fontManager.tahoma20.drawString(text + (password.equalsIgnoreCase("OfflineAccountName") ? " (Cracked)" : " (Premium)"), (float)(this.WIDTH - 110) / 2, this.y + 10 - font.FONT_HEIGHT / 2, ClientUtil.INSTANCE.reAlpha(Colors.WHITE.c, this.opacity));
        int boxX = this.x + this.WIDTH - 10;
        int boxY = this.y + 1;
        int boxSize = 25;
        int size = 5;
        RenderUtil.drawRect(boxX, boxY, boxX + boxSize - 1, boxY + boxSize - 1, ClientUtil.INSTANCE.reAlpha(FlatColors.RED.c, 0.7f));
        Client.fontManager.tahoma17.drawString("DEL", (float)boxX + (boxSize - size) / 2 - 5, boxY + boxSize - 1 - 16, -1);
        boolean hoveringCross = mouseX >= boxX && mouseX <= boxX + boxSize && mouseY >= boxY && mouseY <= boxY + boxSize && mouseY < this.res.getScaledHeight() - 35;
        this.animation_one = (float)RenderUtil.INSTANCE.getAnimationState(this.animation_one, (boolean)hoveringCross ? 1 : 0, 5.0);
        RenderUtil.drawRect(boxX, boxY, boxX + boxSize - 1, boxY + boxSize - 1, ClientUtil.INSTANCE.reAlpha(Colors.BLACK.c, 0.25f * this.animation_one));
        size = 14;
        RenderUtil.drawRect(boxX + boxSize, boxY, (boxX += boxSize + 1) + boxSize - 2 + 25, boxY + boxSize - 1, ClientUtil.INSTANCE.reAlpha(FlatColors.GREEN.c, 0.7f));
        Client.fontManager.tahoma17.drawString("LOGIN", (float)(boxX += boxSize + 1) + boxSize - 41, boxY + boxSize - 1 - 16, -1);
        boolean hoveringLogin = mouseX >= boxX - 25 && mouseX <= boxX + boxSize && mouseY >= boxY && mouseY <= boxY + boxSize;
        this.animation_two = (float)RenderUtil.INSTANCE.getAnimationState(this.animation_two, (boolean)hoveringLogin ? 1 : 0, 5.0);
        Gui.drawRect(boxX - 27, boxY, boxX + boxSize - 3, boxY + boxSize - 1, ClientUtil.INSTANCE.reAlpha(Colors.BLACK.c, 0.25f * this.animation_two));
        if (this.handler.canExcecute()) {
            if (hoveringCross) {
                GuiAltManager.toDelete.add(new GuiAltSlot(this.username, this.password));
            } else if (hoveringLogin) {
                AltLogin.login(this.username, this.password);
            }
        }
    }

    public boolean isHovering(int mouseX, int mouseY) {
    	if (this.y > this.MAX_HEIGHT - 25) {
            return false;
        }
        if (mouseY > this.y && mouseY <= this.y + 25 && mouseX >= this.x && mouseX <= this.WIDTH && mouseY <= this.MAX_HEIGHT - 25 && mouseY >= this.MIN_HEIGHT + 25) {
            return true;
        }
        return false;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

