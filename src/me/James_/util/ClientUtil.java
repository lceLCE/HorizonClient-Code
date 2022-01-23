package me.James_.util;

import java.awt.Color;
import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import me.James_.Client;
import me.James_.ui.ClientNotification;
import me.James_.ui.ClientNotification.Type;
import me.James_.util.fontRenderer.UnicodeFontRenderer;
import me.James_.util.fontRenderer.CFont.CFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;

public enum ClientUtil {
	
	INSTANCE;
	
	private static ArrayList<ClientNotification> notifications = new ArrayList<>();
	public CFontRenderer sansation19 = new CFontRenderer(this.getSansation(19), true, true);
	public CFontRenderer tahoma19 = new CFontRenderer(this.getTahoma(19), true, true);
	
	public Font getTahoma(float size) {
		Font f;
		try {
			InputStream is = UnicodeFontRenderer.class.getResourceAsStream("fonts/tahoma.ttf");
			f = Font.createFont(0, is);
            f = f.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			f = new Font("Arial", 0, (int)size);
        }
		return f;
	}
	
	public Font getSansation(float size) {
		Font f;
		try {
			InputStream is = UnicodeFontRenderer.class.getResourceAsStream("fonts/sansation.ttf");
			f = Font.createFont(0, is);
            f = f.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error loading font");
			f = new Font("Arial", 0, (int)size);
        }
		return f;
	}
	
	public void drawNotifications() {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		double startY = res.getScaledHeight() - 25;
		final double lastY = startY;
		for (int i = 0; i < notifications.size(); i++) {
			ClientNotification not = notifications.get(i);
			if (not.shouldDelete())
				notifications.remove(i);
			not.draw(startY, lastY);
			startY -= not.getHeight() + 1;
		}
	}
	
	public static void sendClientMessage(String message, Type type) {
		notifications.add(new ClientNotification(message, type));
	}
	
	public static int reAlpha(int color, float alpha) {
		Color c = new Color(color);
		float r = ((float) 1 / 255) * c.getRed();
		float g = ((float) 1 / 255) * c.getGreen();
		float b = ((float) 1 / 255) * c.getBlue();
		return new Color(r, g, b, alpha).getRGB();
	}
	
	public static void sendChatMessage(String message,ChatType type) {
    	if(type == ChatType.INFO) {
    		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\247c\247l[" + Client.CLIENT_NAME + "]\247r\2477\247l " + message));
    	} else if(type == ChatType.WARN) {
    		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\247c\247l[" + Client.CLIENT_NAME + "]\247r\247e\247l " + message));
    	} else if(type == ChatType.ERROR) {
    		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\247b\247l[" + Client.CLIENT_NAME + "]\247r\2474\247l " + message));
    	}
    }
}
