package me.James_.ui.altManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicate;

import me.James_.Client;
import me.James_.util.ClientUtil;
import me.James_.util.Colors;
import me.James_.util.FlatColors;
import me.James_.util.RenderUtil;
import me.James_.util.timeUtils.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class GuiAltManager
extends GuiScreen {
    private final GuiScreen parentScreen;
    private UIFlatButton loginButton;
    private UIFlatButton addAltButton;
    private UIFlatButton editAltButton;
    private UIFlatButton deleteAltButton;
    private UIFlatButton importAltsButton;
    private UIFlatButton cancelButton;
    private int scroll;
    float opacity = 0.0f;
    private float sliderY = 0.0f;
    private int sliderY2;
    private float sliderOpacity;
    private boolean clickedSlider;
    private boolean dragSlider;
    private final JFileChooser fc = new JFileChooser();
    private final int MAX_PARTICLES = 5000;
    private Random random = new Random();
    public static TimeHelper timer = new TimeHelper();
    private boolean clicked;
    private UITextField usernameField;
    private UITextField passwordField;
    private UITextField usernamePasswordField;
    private Predicate<String> field_181032_r;
    public static ArrayList<GuiAltSlot> toDelete = new ArrayList();
    
    public static String status;
    
    public static boolean isBanned;

    public GuiAltManager(GuiScreen p_i1033_1_) {
        this.field_181032_r = new Predicate<String>(){
            public boolean apply(String p_apply_1_) {
                if (p_apply_1_.length() == 0) {
                    return true;
                }
                String[] astring = p_apply_1_.split(":");
                if (astring.length == 0) {
                    return true;
                }
                try {
                    String s = IDN.toASCII(astring[0]);
                    return true;
                }
                catch (IllegalArgumentException var4) {
                    return false;
                }
            }
        };
        this.parentScreen = p_i1033_1_;
    }

    @Override
    public void updateScreen() {
        this.usernameField.updateCursorCounter();
        this.passwordField.updateCursorCounter();
        this.usernamePasswordField.updateCursorCounter();
    }

    @Override
    public void initGui() {
    	isBanned = false;
    	status = "Waiting...";
        Keyboard.enableRepeatEvents((boolean)true);
        AltManager.loadAltsFromFile();
        this.scroll = 0;
        int c = -15698006;
        this.buttonList.clear();
        this.addAltButton = new UIFlatButton(1, this.width / 2 + 220, 52, 52, 15, "Add Alt", FlatColors.ASPHALT.c);
        this.buttonList.add(this.addAltButton);
        this.cancelButton = new UIFlatButton(4, 10, this.height - 20, 65, 15, "Back", FlatColors.ASPHALT.c);
        this.buttonList.add(this.cancelButton);
        this.importAltsButton = new UIFlatButton(5, 80, this.height - 20, 105, 15, "Import", FlatColors.ASPHALT.c);
        this.buttonList.add(this.importAltsButton);
        this.usernameField = new UITextField(0, this.fontRendererObj, this.width / 2 - 190, 48, 200, 20);
        this.usernameField.setFocused(true);
        this.passwordField = new UITextField(1, this.fontRendererObj, this.width / 2 + 15, 48, 200, 20);
        this.passwordField.setMaxStringLength(128);
        this.passwordField.setValidator(this.field_181032_r);
        this.usernamePasswordField = new UITextField(2, this.fontRendererObj, this.width / 2 - 190, 18, 405, 20);
        this.usernamePasswordField.setMaxStringLength(128);
        this.usernamePasswordField.setValidator(this.field_181032_r);
        ((GuiButton)this.buttonList.get((int)0)).enabled = this.usernameField.getText().length() > 0 && this.passwordField.getText().length() > 0 || this.usernamePasswordField.getText().split(":").length >= 2;
        this.sliderOpacity = 0.5f;
        this.opacity = 0.0f;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            if (!this.usernameField.getText().isEmpty() && !this.passwordField.getText().isEmpty()) {
                AltManager.guiSlotList.add(new GuiAltSlot(this.usernameField.getText(), this.passwordField.getText()));
            } else if(!this.usernameField.getText().isEmpty()) {
            	AltManager.guiSlotList.add(new GuiAltSlot(this.usernameField.getText(), "OfflineAccountName"));
            }
            if (!this.usernamePasswordField.getText().isEmpty() && this.usernamePasswordField.getText().indexOf(":") != -1) {
                String[] account = this.usernamePasswordField.getText().split(":");
                AltManager.guiSlotList.add(new GuiAltSlot(account[0], account[1]));
                status = "Added " + account[0];
            } else {
            	status = "Invalid Format!";
            }
            this.usernameField.setText("");
            this.usernamePasswordField.setText("");
            this.passwordField.setText("");
            AltManager.saveAltsToFile();
        }
        if (button.id == 4) {
            AltManager.saveAltsToFile();
            this.mc.displayGuiScreen(this.parentScreen);
        }
        if (button.id == 5) {
            Runnable run = () -> {
                this.importAlts();
            };
            new Thread(run).start();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.usernameField.textboxKeyTyped(typedChar, keyCode);
        this.passwordField.textboxKeyTyped(typedChar, keyCode);
        this.usernamePasswordField.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 15) {
            this.usernameField.setFocused(!this.usernameField.isFocused());
            this.passwordField.setFocused(!this.passwordField.isFocused());
            this.usernamePasswordField.setFocused(!this.passwordField.isFocused());
        }
        if (keyCode == 28 || keyCode == 156) {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
        ((GuiButton)this.buttonList.get((int)0)).enabled = this.usernameField.getText().length() > 0 && this.passwordField.getText().length() > 0 || this.usernamePasswordField.getText().split(":").length >= 2;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.passwordField.mouseClicked(mouseX, mouseY, mouseButton);
        this.usernameField.mouseClicked(mouseX, mouseY, mouseButton);
        this.usernamePasswordField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        toDelete.clear();
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        ScaledResolution res = new ScaledResolution(this.mc);
        if (Keyboard.isKeyDown((int)1)) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
        int topHeight = 30;
        int darkGray = -15658735;
        int lightGray = -15066598;
        int red = -1023904;
        if (this.opacity < 1.0f) {
            this.opacity += 0.1f;
        }
        if (this.opacity > 1.0f) {
            this.opacity = 1.0f;
        }
        RenderUtil.drawImage(new ResourceLocation("J/bg.jpg"), 0, 0, res.getScaledWidth(), res.getScaledHeight());
        int y = 0;
       	Client.fontManager.tahoma40.drawString("AltManager", (float)20, 15, ClientUtil.INSTANCE.reAlpha(Colors.WHITE.c, this.opacity));
       	Client.fontManager.tahoma20.drawString("Session: ", (float)0, 1, ClientUtil.INSTANCE.reAlpha(Colors.WHITE.c, this.opacity));
		boolean session_status = this.checkSite("https://sessionserver.mojang.com/");
		boolean auth_status = this.checkSite("https://authserver.mojang.com/authenticate");
       	Client.fontManager.tahoma20.drawString((session_status ? "OK" : "ERR"), (float)40, 1, ClientUtil.INSTANCE.reAlpha((session_status ? Colors.GREEN.c : Colors.RED.c), this.opacity));
       	Client.fontManager.tahoma20.drawString("Auth: ", (float)70, 1, ClientUtil.INSTANCE.reAlpha(Colors.WHITE.c, this.opacity));
       	Client.fontManager.tahoma20.drawString((auth_status ? "OK" : "ERR"), (float)95, 1, ClientUtil.INSTANCE.reAlpha((session_status ? Colors.GREEN.c : Colors.RED.c), this.opacity));
       	Client.fontManager.tahoma20.drawString("Username: ", (float)20, 40, ClientUtil.INSTANCE.reAlpha(Colors.WHITE.c, this.opacity));
        Client.fontManager.tahoma20.drawString(String.valueOf(mc.getSession().getUsername()) , (float)75, 40, ClientUtil.INSTANCE.reAlpha(Colors.WHITE.c, this.opacity));
        Client.fontManager.tahoma20.drawString("Alts: ", (float)20, 50, ClientUtil.INSTANCE.reAlpha(Colors.WHITE.c, this.opacity));
        Client.fontManager.tahoma20.drawString(String.valueOf(AltManager.guiSlotList.size()), (float)45, 50, ClientUtil.INSTANCE.reAlpha(Colors.WHITE.c, this.opacity));
        Client.fontManager.tahoma20.drawString("Status: ", (float)190, this.height - 16, ClientUtil.INSTANCE.reAlpha(Colors.WHITE.c, this.opacity));
        Client.fontManager.tahoma20.drawString(status, (float)225, this.height - 16, ClientUtil.INSTANCE.reAlpha(Colors.WHITE.c, this.opacity));
        int MIN_HEIGHT = 75;
        int MAX_HEIGHT = res.getScaledHeight() - 35;
        float percent = (this.sliderY - (float)MIN_HEIGHT) / (float)(MAX_HEIGHT - MIN_HEIGHT);
        float scrollAmount = (float)(- Mouse.getDWheel()) * 0.07f;
        if (scrollAmount > 0.0f) {
            this.sliderY = this.sliderY + scrollAmount < (float)MAX_HEIGHT ? (this.sliderY += scrollAmount) : (float)MAX_HEIGHT;
        } else if (scrollAmount < 0.0f) {
            this.sliderY = this.sliderY - scrollAmount > (float)MIN_HEIGHT ? (this.sliderY += scrollAmount) : (float)MIN_HEIGHT;
        }
        int all = 0;
        for (GuiAltSlot slot : AltManager.guiSlotList) {
            all += 25;
        }
        int slotY = - (int)((float)all * percent - (float)(75 + y));
        GL11.glPushMatrix();
        GL11.glEnable((int)3089);
        RenderUtil.doGlScissor(0, 75, res.getScaledWidth(), res.getScaledHeight() - 110);
        for (GuiAltSlot slot : AltManager.guiSlotList) {
            slot.y = slotY;
            slot.opacity = this.opacity;
            slot.WIDTH = res.getScaledWidth() - 95;
            slot.MIN_HEIGHT = 50;
            slot.MAX_HEIGHT = res.getScaledHeight() - 10;
            slot.drawScreen(mouseX, mouseY);
            slotY += 25;
        }
        GL11.glDisable((int)3089);
        GL11.glPopMatrix();
        for (GuiAltSlot slot : toDelete) {
            GuiAltManager.delte(slot.getUsername(), slot.getPassword());
        }
        FontRenderer textBoxFont = Minecraft.getMinecraft().fontRendererObj;
        Client.fontManager.tahoma20.drawString("Email:Password", (float)this.width / 2 - 190, 7, Colors.WHITE.c);
        Client.fontManager.tahoma20.drawString("Email/Username", (float)this.width / 2 - 190, 37, Colors.WHITE.c);
        Client.fontManager.tahoma20.drawString("Password", (float)this.width / 2 + 15, 37, Colors.WHITE.c);
        this.usernameField.drawTextBox();
        this.passwordField.drawTextBox();
        this.usernamePasswordField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawSlider(mouseX, mouseY);
    }

    public static void delte(String username, String password) {
        int i = 0;
        while (i < AltManager.guiSlotList.size()) {
            GuiAltSlot slot = AltManager.guiSlotList.get(i);
            if (slot.getUsername().equalsIgnoreCase(username) && slot.getPassword().equalsIgnoreCase(password)) {
                AltManager.guiSlotList.remove(i);
            }
            ++i;
        }
        AltManager.saveAltsToFile();
    }
    
    public boolean checkSite(String url) {
        HttpURLConnection connection = null;
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection)u.openConnection();
            connection.setRequestMethod("POST");
            return true;
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }
        catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void drawSlider(int mouseX, int mouseY) {
        int color;
        boolean yAdd;
        ScaledResolution res = new ScaledResolution(this.mc);
        int MIN_HEIGHT = 75;
        int MAX_HEIGHT = res.getScaledHeight() - 35;
        int WIDTH = res.getScaledWidth() - 150;
        int radius = 5;
        if (!AltManager.guiSlotList.isEmpty()) {
            AltManager.guiSlotList.get(0);
        }
        int allAltsHeight = AltManager.guiSlotList.size() * (AltManager.guiSlotList.size() == 0 ? 0 : 25);
        float height = allAltsHeight <= MAX_HEIGHT - 75 ? (float)(MAX_HEIGHT - 75) : (float)(MAX_HEIGHT - 75) / (float)(allAltsHeight + 12) * (float)(MAX_HEIGHT - 75);
        if (height > (float)(MAX_HEIGHT - 75)) {
            height = MAX_HEIGHT - 75;
        }
        int x = res.getScaledWidth() - radius - 2;
        int y = (int)this.sliderY;
        int x2 = x + radius;
        int y2 = (int)(this.sliderY + height - (float)radius);
        Gui.drawRect(x, 75, x2 + 2, MAX_HEIGHT, ClientUtil.INSTANCE.reAlpha(Colors.GREY.c, 0.15f));
        boolean bl = yAdd = height < 2.0f;
        boolean hover = mouseX >= x && mouseX <= x2 && mouseY >= y - (yAdd ? 2 : 0) && mouseY <= y2 + (yAdd ? 2 : 0);
        int n = color = hover || this.clickedSlider ? FlatColors.DARK_GREY.c : FlatColors.GREY.c;
        if (Mouse.isButtonDown((int)0)) {
            if (!this.clickedSlider && hover) {
                this.clickedSlider = true;
                this.sliderY2 = (int)((float)mouseY - this.sliderY);
            }
        } else {
            this.clickedSlider = false;
        }
        if (this.clickedSlider) {
            this.sliderY = mouseY - this.sliderY2;
        }
        if (this.sliderY + height > (float)MAX_HEIGHT) {
            this.sliderY = (float)MAX_HEIGHT - height;
        }
        if (this.sliderY < 75.0f) {
            this.sliderY = 75.0f;
        }
        Gui.drawRect(x + 1, 1 + (int)this.sliderY - (yAdd ? 2 : 0), x2 + 1, (int)(this.sliderY + height - (float)radius) + (yAdd ? 2 : 0), ClientUtil.INSTANCE.reAlpha(color, this.opacity));
    }

    private void scroll(boolean canScroll) {
        int scroll_ = Mouse.getDWheel() / 12;
        if (canScroll && scroll_ > 0) {
            this.scroll -= scroll_;
        }
        if (this.scroll < 0) {
            this.scroll = 0;
        }
    }

    private void importAlts() {
        File fromFile = null;
        this.fc.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int returnVal = this.fc.showOpenDialog(null);
        this.fc.requestFocus();
        if (returnVal == 0) {
            fromFile = this.fc.getSelectedFile();
            ArrayList<String> altsToImport = new ArrayList<String>();
            try {
                String line;
                BufferedReader bufferedReader = new BufferedReader(new FileReader(fromFile));
                while ((line = bufferedReader.readLine()) != null) {
                    String[] alt = line.split(":");
                    if (alt.length <= 0) continue;
                    altsToImport.add(line);
                }
                bufferedReader.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                FileWriter fWriter = new FileWriter(AltManager.altFile, true);
                PrintWriter writer = new PrintWriter(fWriter);
                for (String s : altsToImport) {
                    writer.write(String.valueOf(s) + "\n");
                }
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            this.mc.displayGuiScreen(this);
        }
    }
}

