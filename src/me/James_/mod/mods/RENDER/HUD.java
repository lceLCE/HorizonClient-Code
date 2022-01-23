	package me.James_.mod.mods.RENDER;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import com.darkmagician6.eventapi.EventTarget;

import me.James_.Client;
import me.James_.Value;
import me.James_.events.EventRender2D;
import me.James_.mod.Category;
import me.James_.mod.Mod;
import me.James_.ui.ClientNotification;
import me.James_.util.ClientUtil;
import me.James_.util.Colors;
import me.James_.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class HUD extends Mod {

	public Value<String> logo_mode = new Value("HUD", "Logo", 0);
	public Value<Boolean> logo = new Value("HUD_Logo", false);
	public static Value<Boolean> hotbar = new Value("HUD_Hotbar", true);
	public static Value<Boolean> FPS = new Value("HUD_FPS", true);
	public Value<Boolean> array = new Value("HUD_ArrayList", false);
	public static Value<Boolean> stuff = new Value("HUD_ArmorStatus", true);
	public Value<Boolean> potion = new Value("HUD_PotionStatus", true);
	public static Value<Boolean> ttfChat = new Value("HUD_TTFChat", false);
	public static Value<Boolean> info = new Value("HUD_info", false);
	
	private Comparator<Mod> comparator = (m, m1) -> {
        String mName = m.getDisplayName() == "" ? m.getName() : String.format("%s %s", m.getName(), m.getDisplayName());
        String m1Name = m1.getDisplayName() == "" ? m1.getName() : String.format("%s %s", m1.getName(), m1.getDisplayName());
        return Integer.compare(Client.instance.fontMgr.tahoma18.getStringWidth(m1Name), Client.instance.fontMgr.tahoma18.getStringWidth(mName));
    };
    
    public int etb_color = new Color(105, 180, 255).getRGB();
	
	public HUD() {
		super("HUD", Category.RENDER);
		this.logo_mode.mode.add("Exhi:B");
		this.logo_mode.mode.add("ETB:B");
		this.logo_mode.mode.add("Meme:B");
		this.logo_mode.mode.add("Test:B");
		this.logo_mode.mode.add("Exhi:N");
		this.logo_mode.mode.add("ETB:N");
		this.logo_mode.mode.add("Meme:N");
		this.logo_mode.mode.add("Test:N");
	}
	
	FontRenderer fr = mc.fontRendererObj;
	boolean lowhealth = false;
	public static boolean toggledlyric = false;
	
	
	@EventTarget
	public void onRender2D(EventRender2D event) {
		//向Margele学习！！！
		//低血量提示
		ScaledResolution sr = new ScaledResolution(mc);
		if(mc.thePlayer.getHealth() < 6 && !lowhealth) {

			ClientUtil.sendClientMessage("Your Health is Low!", ClientNotification.Type.WARNING);
			lowhealth = true;
		}
		if(mc.thePlayer.getHealth() > 6 && lowhealth) {lowhealth = false;}
		
		this.renderToggled(sr);
		
		this.renderPotionStatus(sr);
		
		if(this.logo.getValueState().booleanValue()) {
			this.renderLogo();
		}
		
		//Coords Render START
		//("FPS:" + Minecraft.getDebugFPS()
		//String info = "\247bCoords: \247r" + (int)mc.thePlayer.posX + " " + (int)mc.thePlayer.posY + " " + (int)mc.thePlayer.posZ;
		String info = "\247bX: \247r" + (int)mc.thePlayer.posX + " "  + "\247bY: \247r" + (int)mc.thePlayer.posY + " "  + "\247bZ: \247r" + (int)mc.thePlayer.posZ;
		String fps = "\247bFPS: \247r" + (int)Minecraft.getDebugFPS() + (mc.isSingleplayer() ? " \247bPing: \247r0ms" : " \247bPing: \247r" + mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime() + "ms");
		String Dev = "\247bUser: \247r" + "Dev" + (mc.isSingleplayer() ? " \247bPing: \247r0ms" : " \247bPing: \247r" + mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime() + "ms");
		String Other = "\247bUser: \247r" + "Other" + (mc.isSingleplayer() ? " \247bPing: \247r0ms" : " \247bPing: \247r" + mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime() + "ms");
		if(this.info.getValueState().booleanValue()) {
			if(!(mc.currentScreen instanceof GuiChat)) {
				if(mc.fontRendererObj.getStringWidth(info) > mc.fontRendererObj.getStringWidth(fps)) {
					RenderUtil.drawRect(0, sr.getScaledHeight() - 23, fr.getStringWidth(info) + 8, sr.getScaledHeight(), Integer.MIN_VALUE);
				} else if(mc.fontRendererObj.getStringWidth(fps) > mc.fontRendererObj.getStringWidth(info)) {
					RenderUtil.drawRect(0, sr.getScaledHeight() - 23, fr.getStringWidth(fps) + 8, sr.getScaledHeight(), Integer.MIN_VALUE);
				}
				fr.drawString(info, 2, sr.getScaledHeight() - 10, 0xffffff);
				fr.drawString(fps, 2, sr.getScaledHeight() - 20, 0xffffff);
			}
			GlStateManager.color(1, 1, 1);
			//Coords Render END
		}	
		}

	
	
	public void renderLogo() {
		if(this.logo_mode.isCurrentMode("ETB:B")) {
			String dire = Direction.values()[MathHelper.floor_double(mc.thePlayer.rotationYaw * 4.0f / 180.0f + 0.5) & 0x7].name();
			String text = Client.CLIENT_LOGO;
			String text_b = text.substring(1) + " " + Client.CLIENT_VER;
			fr.drawStringWithShadow(Client.CLIENT_LOGO.toUpperCase(), 2, 1, etb_color);
			fr.drawStringWithShadow(Client.CLIENT_VER, 5 + fr.getStringWidth(Client.CLIENT_LOGO.toUpperCase()), 1, 0x989898);
			fr.drawStringWithShadow("[" + dire + "]", fr.getStringWidth(Client.CLIENT_LOGO.toUpperCase()) + fr.getStringWidth(Client.CLIENT_VER) + 9, 1, etb_color);
			if(this.FPS.getValueState().booleanValue()) {
				fr.drawStringWithShadow("FPS:" + Minecraft.getDebugFPS(), 2, 9, etb_color);
			}
		}
		if(this.logo_mode.isCurrentMode("Exhi:B")) {
			DateFormat dft = new SimpleDateFormat("hh:mm a");
            Date time = Calendar.getInstance().getTime();
            String rendertime = dft.format(time);
            Client.fontManager.tahoma20.drawStringWithShadow(Client.CLIENT_LOGO.substring(0, 3), 2, -1, RenderUtil.rainbow(50));

            mc.fontRendererObj.drawStringWithShadow("[" + rendertime + "]", Client.fontManager.tahoma18.getStringWidth(Client.CLIENT_LOGO) + 8, +2, RenderUtil.rainbow(1000));
			if(this.FPS.getValueState().booleanValue()) {
				Client.fontManager.tahoma20.drawStringWithShadow("FPS:" + Minecraft.getDebugFPS(), 2, 8, RenderUtil.rainbow(50));
			}
		}
		if(this.logo_mode.isCurrentMode("Meme:B")) {
			DateFormat dft = new SimpleDateFormat("hh:mm a");
            Date time = Calendar.getInstance().getTime();
            String rendertime = dft.format(time);
            Client.fontManager.sansation20.drawStringWithShadow(Client.CLIENT_LOGO.substring(0, 1), 2, -1, RenderUtil.rainbow(50));
            Client.fontManager.sansation20.drawStringWithShadow(Client.CLIENT_LOGO.substring(1), Client.fontManager.sansation20.getStringWidth(Client.CLIENT_LOGO.substring(0, 1)) + 2, -1, Colors.WHITE.c);
            mc.fontRendererObj.drawStringWithShadow("[" + rendertime + "]", Client.fontManager.sansation18.getStringWidth(Client.CLIENT_LOGO) + 8, +2, Colors.WHITE.c);
			if(this.FPS.getValueState().booleanValue()) {
				Client.fontManager.sansation20.drawStringWithShadow("FPS:" + Minecraft.getDebugFPS(), 2, 8, RenderUtil.rainbow(50));
			}
		}
		if(this.logo_mode.isCurrentMode("Test:B")) {
			DateFormat dft = new SimpleDateFormat("hh:mm a");
            Date time = Calendar.getInstance().getTime();
            String rendertime = dft.format(time);
            Client.fontManager.consolasbold20.drawStringWithShadow(Client.CLIENT_LOGO.substring(0, 1), 2, -1, RenderUtil.rainbow(50));
            Client.fontManager.consolasbold20.drawStringWithShadow(Client.CLIENT_LOGO.substring(1), Client.fontManager.consolasbold20.getStringWidth(Client.CLIENT_LOGO.substring(0, 1)) + 2, -1, Colors.WHITE.c);
            mc.fontRendererObj.drawStringWithShadow("[" + rendertime + "]", Client.fontManager.consolasbold18.getStringWidth(Client.CLIENT_LOGO) + 8, +2, Colors.WHITE.c);
			if(this.FPS.getValueState().booleanValue()) {
				Client.fontManager.consolasbold20.drawStringWithShadow("FPS:" + Minecraft.getDebugFPS(), 2, 8, RenderUtil.rainbow(50));
			}
		}
		if(this.logo_mode.isCurrentMode("Exhi:N")) {
			DateFormat dft = new SimpleDateFormat("hh:mm a");
            Date time = Calendar.getInstance().getTime();
            String rendertime = dft.format(time);
            Client.fontManager.tahoma20.drawStringWithShadow(Client.CLIENT_NAME2.substring(0, 1), 2, -1, RenderUtil.rainbow(50));
            Client.fontManager.tahoma20.drawStringWithShadow(Client.CLIENT_NAME2.substring(1), Client.fontManager.tahoma20.getStringWidth(Client.CLIENT_NAME2.substring(0, 1)) + 2, -1, Colors.WHITE.c);
            mc.fontRendererObj.drawStringWithShadow("[" + rendertime + "]", Client.fontManager.tahoma18.getStringWidth(Client.CLIENT_NAME2) + 8, +2, Colors.WHITE.c);
			if(this.FPS.getValueState().booleanValue()) {
				Client.fontManager.tahoma20.drawStringWithShadow("FPS:" + Minecraft.getDebugFPS(), 2, 8, RenderUtil.rainbow(50));
			}
		}
		if(this.logo_mode.isCurrentMode("ETB:N")) {
			String dire = Direction.values()[MathHelper.floor_double(mc.thePlayer.rotationYaw * 4.0f / 180.0f + 0.5) & 0x7].name();
			String text = Client.CLIENT_NAME2;
			String text_b = text.substring(1) + " " + Client.CLIENT_VER;
			fr.drawStringWithShadow(Client.CLIENT_NAME2.toUpperCase(), 2, 1, etb_color);
			fr.drawStringWithShadow(Client.CLIENT_VER, 5 + fr.getStringWidth(Client.CLIENT_NAME2.toUpperCase()), 1, 0x989898);
			fr.drawStringWithShadow("[" + dire + "]", fr.getStringWidth(Client.CLIENT_NAME2.toUpperCase()) + fr.getStringWidth(Client.CLIENT_VER) + 9, 1, etb_color);
			if(this.FPS.getValueState().booleanValue()) {
				fr.drawStringWithShadow("FPS:" + Minecraft.getDebugFPS(), 2, 9, etb_color);
			}
		}
		if(this.logo_mode.isCurrentMode("Meme:N")) {
			DateFormat dft = new SimpleDateFormat("hh:mm a");
            Date time = Calendar.getInstance().getTime();
            String rendertime = dft.format(time);
            Client.fontManager.sansation20.drawStringWithShadow(Client.CLIENT_NAME2.substring(0, 1), 2, -1, RenderUtil.rainbow(50));
            Client.fontManager.sansation20.drawStringWithShadow(Client.CLIENT_NAME2.substring(1), Client.fontManager.sansation20.getStringWidth(Client.CLIENT_NAME2.substring(0, 1)) + 2, -1, Colors.WHITE.c);
            mc.fontRendererObj.drawStringWithShadow("[" + rendertime + "]", Client.fontManager.sansation18.getStringWidth(Client.CLIENT_NAME2) + 8, +2, Colors.WHITE.c);
			if(this.FPS.getValueState().booleanValue()) {
				Client.fontManager.sansation20.drawStringWithShadow("FPS:" + Minecraft.getDebugFPS(), 2, 8, RenderUtil.rainbow(50));
			}
		}
		if(this.logo_mode.isCurrentMode("Test:N")) {
			DateFormat dft = new SimpleDateFormat("hh:mm a");
            Date time = Calendar.getInstance().getTime();
            String rendertime = dft.format(time);
            Client.fontManager.consolasbold20.drawStringWithShadow(Client.CLIENT_NAME2.substring(0, 1), 2, -1, RenderUtil.rainbow(50));
            Client.fontManager.consolasbold20.drawStringWithShadow(Client.CLIENT_NAME2.substring(1), Client.fontManager.consolasbold20.getStringWidth(Client.CLIENT_NAME2.substring(0, 1)) + 2, -1, Colors.WHITE.c);
            mc.fontRendererObj.drawStringWithShadow("[" + rendertime + "]", Client.fontManager.consolasbold18.getStringWidth(Client.CLIENT_NAME2) + 8, +2, Colors.WHITE.c);
			if(this.FPS.getValueState().booleanValue()) {
				Client.fontManager.consolasbold20.drawStringWithShadow("FPS:" + Minecraft.getDebugFPS(), 2, 8, RenderUtil.rainbow(50));
			}
		}
	}
	
	private void renderToggled(ScaledResolution sr) {
		if(this.array.getValueState().booleanValue()) {
			ArrayList<Mod> mods = new ArrayList(Client.instance.modMgr.getToggled());
			int counter[] = {0};
			mods.sort(this.comparator);
			int yStart = 0;
			int right = sr.getScaledWidth();
			for(Mod module : mods) {
				RenderUtil.drawRect(sr.getScaledWidth() - 2, yStart - 1, sr.getScaledWidth(), yStart + Client.fontManager.tahoma18.FONT_HEIGHT, RenderUtil.rainbow(counter[0] * 200));
				if(module.getDisplayName() != "") {
					RenderUtil.drawRect(sr.getScaledWidth() - Client.fontManager.tahoma18.getStringWidth(module.getDisplayName() + module.getName()) - 9, yStart, sr.getScaledWidth() - 2, yStart + Client.fontManager.tahoma18.FONT_HEIGHT, ClientUtil.INSTANCE.reAlpha(Colors.BLACK.c, 0.4f));
				} else {
					RenderUtil.drawRect(sr.getScaledWidth() - Client.fontManager.tahoma18.getStringWidth(module.getName()) - 6, yStart, sr.getScaledWidth() - 2, yStart + Client.fontManager.tahoma18.FONT_HEIGHT, ClientUtil.INSTANCE.reAlpha(Colors.BLACK.c, 0.4f));
				}
				//RenderFont
				Client.fontManager.tahoma18.drawStringWithShadow(module.getName(), right - Client.fontManager.tahoma18.getStringWidth(module.getName()) - 4, yStart, RenderUtil.rainbow(counter[0] * 200));
				if(module.getDisplayName() != null) {
					Client.fontManager.tahoma18.drawStringWithShadow(module.getDisplayName(), right - Client.fontManager.tahoma18.getStringWidth(module.getDisplayName() + module.getName()) - 7, yStart , Colors.WHITE.c);
				}
				yStart += Client.fontManager.tahoma18.FONT_HEIGHT;
				counter[0]++;
			}
		}
	}
	
	public int x = 0;
	public void renderPotionStatus(final ScaledResolution sr) {
		if(!this.potion.getValueState().booleanValue()) {
			return;
		}
		x = 0;
		for (final PotionEffect effect : (Collection<PotionEffect>) this.mc.thePlayer.getActivePotionEffects()) {
			Potion potion = Potion.potionTypes[effect.getPotionID()];
            String PType = I18n.format(potion.getName()), d = "";
            switch (effect.getAmplifier()) {
                case 1:
                    PType = PType + EnumChatFormatting.DARK_AQUA + " II";
                    break;
                case 2:
                    PType = PType + EnumChatFormatting.BLUE +  " III";
                    break;
                case 3:
                    PType = PType + EnumChatFormatting.DARK_PURPLE+  " IV";
                    break;
                default:
                    break;
            }
            if (effect.getDuration() < 600 && effect.getDuration() > 300)
                d = EnumChatFormatting.YELLOW + Potion.getDurationString(effect);
            else if (effect.getDuration() < 300) d = EnumChatFormatting.RED + Potion.getDurationString(effect);
            else if (effect.getDuration() > 600) d = EnumChatFormatting.WHITE + Potion.getDurationString(effect);

            int x1 = (int) ((sr.getScaledWidth() - 6) * 1.33);
            int y1 = (int) ((sr.getScaledHeight() - 32 - this.mc.fontRendererObj.FONT_HEIGHT + x + 5) * 1.33F);

            if (potion.hasStatusIcon()) {
                GlStateManager.pushMatrix();
                GlStateManager.color(1, 1, 1, 1);
                int var10 = potion.getStatusIconIndex();
                ResourceLocation location = new ResourceLocation("textures/gui/container/inventory.png");
                mc.getTextureManager().bindTexture(location);
                GlStateManager.scale(0.75, 0.75, 0.75);
                mc.ingameGUI.drawTexturedModalRect(x1 - 9, y1 + 20, var10 % 8 * 18, 198 + var10 / 8 * 18, 18, 18);

                GlStateManager.popMatrix();
            }
            int y = (sr.getScaledHeight() - this.mc.fontRendererObj.FONT_HEIGHT + x) - 5;
            int m = 15;
            mc.fontRendererObj.drawStringWithShadow(PType, sr.getScaledWidth() - m - mc.fontRendererObj.getStringWidth(PType) - 1, y - this.mc.fontRendererObj.FONT_HEIGHT - 1, Colors.GREEN.c);
            mc.fontRendererObj.drawStringWithShadow(d, sr.getScaledWidth() - m - mc.fontRendererObj.getStringWidth(d) - 1, y, -1);
            x -= 17;
		}
    }
	
	
	public enum Direction {
        S("S", 0), 
        SW("SW", 1), 
        W("W", 2), 
        NW("NW", 3), 
        N("N", 4), 
        NE("NE", 5), 
        E("E", 6), 
        SE("SE", 7);
        private Direction(final String s, final int n) {}
    }
    
    private int getRainbow(int speed, int offset) {
        float hue = (System.currentTimeMillis() + offset) % speed;
        hue /= speed;
        return Color.getHSBColor(hue, 1f, 1f).getRGB();
    }

}
