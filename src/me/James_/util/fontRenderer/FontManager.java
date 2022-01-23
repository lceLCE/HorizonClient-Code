package me.James_.util.fontRenderer;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;

public class FontManager {
	private HashMap<String, HashMap<Float, UnicodeFontRenderer>> fonts = new HashMap();
	
	public UnicodeFontRenderer tahoma5;
	public UnicodeFontRenderer tahoma10;
	public UnicodeFontRenderer tahoma11;
	public UnicodeFontRenderer tahoma12;
	public UnicodeFontRenderer tahoma13;
	public UnicodeFontRenderer tahoma14;
	public UnicodeFontRenderer tahoma15;
	public UnicodeFontRenderer tahoma16;
	public UnicodeFontRenderer tahoma17;
	public UnicodeFontRenderer tahoma18;
	public UnicodeFontRenderer tahoma19;
	public UnicodeFontRenderer tahoma20;
	public UnicodeFontRenderer tahoma25;
	public UnicodeFontRenderer tahoma30;
	public UnicodeFontRenderer tahoma35;
	public UnicodeFontRenderer tahoma40;
	public UnicodeFontRenderer tahoma45;
	public UnicodeFontRenderer tahoma50;
	public UnicodeFontRenderer tahoma55;
	public UnicodeFontRenderer tahoma60;
	public UnicodeFontRenderer tahoma65;
	public UnicodeFontRenderer tahoma70;
	
	public UnicodeFontRenderer sansation5;
	public UnicodeFontRenderer sansation10;
	public UnicodeFontRenderer sansation11;
	public UnicodeFontRenderer sansation12;
	public UnicodeFontRenderer sansation13;
	public UnicodeFontRenderer sansation14;
	public UnicodeFontRenderer sansation15;
	public UnicodeFontRenderer sansation16;
	public UnicodeFontRenderer sansation17;
	public UnicodeFontRenderer sansation18;
	public UnicodeFontRenderer sansation19;
	public UnicodeFontRenderer sansation20;
	public UnicodeFontRenderer sansation25;
	public UnicodeFontRenderer sansation30;
	public UnicodeFontRenderer sansation35;
	public UnicodeFontRenderer sansation40;
	public UnicodeFontRenderer sansation45;
	public UnicodeFontRenderer sansation50;
	public UnicodeFontRenderer sansation55;
	public UnicodeFontRenderer sansation60;
	public UnicodeFontRenderer sansation65;
	public UnicodeFontRenderer sansation70;
	
	public UnicodeFontRenderer simpleton10;
    public UnicodeFontRenderer simpleton11;
    public UnicodeFontRenderer simpleton12;
    public UnicodeFontRenderer simpleton13;
    public UnicodeFontRenderer simpleton14;
    public UnicodeFontRenderer simpleton15;
    public UnicodeFontRenderer simpleton16;
    public UnicodeFontRenderer simpleton17;
    public UnicodeFontRenderer simpleton18;
    public UnicodeFontRenderer simpleton20;
    public UnicodeFontRenderer simpleton25;
    public UnicodeFontRenderer simpleton30;
    public UnicodeFontRenderer simpleton35;
    public UnicodeFontRenderer simpleton40;
    public UnicodeFontRenderer simpleton45;
    public UnicodeFontRenderer simpleton50;
    public UnicodeFontRenderer simpleton70;
    
    public UnicodeFontRenderer consolasbold14;
    public UnicodeFontRenderer consolasbold18;
    public UnicodeFontRenderer consolasbold20;
    
	public UnicodeFontRenderer asu5;
	public UnicodeFontRenderer asu10;
	public UnicodeFontRenderer asu11;
	public UnicodeFontRenderer asu12;
	public UnicodeFontRenderer asu13;
	public UnicodeFontRenderer asu14;
	public UnicodeFontRenderer asu15;
	public UnicodeFontRenderer asu16;
	public UnicodeFontRenderer asu17;
	public UnicodeFontRenderer asu18;
	public UnicodeFontRenderer asu19;
	public UnicodeFontRenderer asu20;
    
    public UnicodeFontRenderer verdana10;
    public UnicodeFontRenderer verdana11;
    public UnicodeFontRenderer verdana12;
    public UnicodeFontRenderer verdana13;
    public UnicodeFontRenderer verdana15;
    public UnicodeFontRenderer verdana16;
    public UnicodeFontRenderer verdana17;
    public UnicodeFontRenderer verdana18;
    public UnicodeFontRenderer verdana20;
    public UnicodeFontRenderer verdana25;
    public UnicodeFontRenderer verdana30;
    public UnicodeFontRenderer verdana35;
    public UnicodeFontRenderer verdana40;
    public UnicodeFontRenderer verdana45;
    public UnicodeFontRenderer verdana50;

    
	
	public FontManager() {
		tahoma5 = this.getFont("tahoma", 5f);
		tahoma10 = this.getFont("tahoma", 10f);
		tahoma11 = this.getFont("tahoma", 11f);
		tahoma12 = this.getFont("tahoma", 12f);
		tahoma13 = this.getFont("tahoma", 13f);
		tahoma14 = this.getFont("tahoma", 14f);
		tahoma15 = this.getFont("tahoma", 15f);
		tahoma16 = this.getFont("tahoma", 16f);
		tahoma17 = this.getFont("tahoma", 17f);
		tahoma18 = this.getFont("tahoma", 18f);
		tahoma19 = this.getFont("tahoma", 19f);
		tahoma20 = this.getFont("tahoma", 20f);
		tahoma25 = this.getFont("tahoma", 25f);
		tahoma30 = this.getFont("tahoma", 30f);
		tahoma35 = this.getFont("tahoma", 35f);
		tahoma40 = this.getFont("tahoma", 40f);
		tahoma45 = this.getFont("tahoma", 45f);
		tahoma50 = this.getFont("tahoma", 50f);
		tahoma55 = this.getFont("tahoma", 55f);
		tahoma60 = this.getFont("tahoma", 60f);
		tahoma65 = this.getFont("tahoma", 65f);
		tahoma70 = this.getFont("tahoma", 70f);
		sansation5 = this.getFont("sansation", 5f);
		sansation10 = this.getFont("sansation", 10f);
		sansation11 = this.getFont("sansation", 11f);
		sansation12 = this.getFont("sansation", 12f);
		sansation13 = this.getFont("sansation", 13f);
		sansation14 = this.getFont("sansation", 14f);
		sansation15 = this.getFont("sansation", 15f);
		sansation16 = this.getFont("sansation", 16f);
		sansation17 = this.getFont("sansation", 17f);
		sansation18 = this.getFont("sansation", 18f);
		sansation19 = this.getFont("sansation", 19f);
		sansation20 = this.getFont("sansation", 20f);
		sansation25 = this.getFont("sansation", 25f);
		sansation30 = this.getFont("sansation", 30f);
		sansation35 = this.getFont("sansation", 35f);
		sansation40 = this.getFont("sansation", 40f);
		sansation45 = this.getFont("sansation", 45f);
		sansation50 = this.getFont("sansation", 50f);
		sansation55 = this.getFont("sansation", 55f);
		sansation60 = this.getFont("sansation", 60f);
		sansation65 = this.getFont("sansation", 65f);
		sansation70 = this.getFont("sansation", 70f);
		asu5 = this.getFont("asu", 5f);
		asu10 = this.getFont("asu", 10f);
		asu11 = this.getFont("asu", 11f);
		asu12 = this.getFont("asu", 12f);
		asu13 = this.getFont("asu", 13f);
		asu14 = this.getFont("asu", 14f);
		asu15 = this.getFont("asu", 15f);
		asu16 = this.getFont("asu", 16f);
		asu17 = this.getFont("asu", 17f);
		asu18 = this.getFont("asu", 18f);
		asu19 = this.getFont("asu", 19f);
		asu20 = this.getFont("asu", 20f);
		this.simpleton10 = this.getFont("simpleton", 10.0f, true);
        this.simpleton11 = this.getFont("simpleton", 11.0f, true);
        this.simpleton12 = this.getFont("simpleton", 12.0f, true);
        this.simpleton13 = this.getFont("simpleton", 13.0f, true);
        this.simpleton14 = this.getFont("simpleton", 14.0f, true);
        this.simpleton15 = this.getFont("simpleton", 15.0f, true);
        this.simpleton16 = this.getFont("simpleton", 16.0f, true);
        this.simpleton17 = this.getFont("simpleton", 17.0f, true);
        this.simpleton18 = this.getFont("simpleton", 18.0f, true);
        this.simpleton20 = this.getFont("simpleton", 20.0f, true);
        this.simpleton25 = this.getFont("simpleton", 25.0f, true);
        this.simpleton30 = this.getFont("simpleton", 30.0f, true);
        this.simpleton35 = this.getFont("simpleton", 35.0f, true);
        this.simpleton40 = this.getFont("simpleton", 40.0f, true);
        this.simpleton45 = this.getFont("simpleton", 45.0f, true);
        this.simpleton50 = this.getFont("simpleton", 50.0f, true);
        this.simpleton70 = this.getFont("simpleton", 70.0f, true);
        this.consolasbold14 = this.getFont("consolasbold", 14.0f);
        this.consolasbold18 = this.getFont("consolasbold", 18.0f);
        this.consolasbold20 = this.getFont("consolasbold", 20.0f);

        this.verdana10 = this.getFont("verdana", 10.0f);
        this.verdana11 = this.getFont("verdana", 11.0f);
        this.verdana12 = this.getFont("verdana", 12.0f);
        this.verdana13 = this.getFont("verdana", 13.0f);
        this.verdana15 = this.getFont("verdana", 15.0f);
        this.verdana16 = this.getFont("verdana", 16.0f);
        this.verdana17 = this.getFont("verdana", 17.0f);
        this.verdana18 = this.getFont("verdana", 18.0f);
        this.verdana20 = this.getFont("verdana", 20.0f);
        this.verdana25 = this.getFont("verdana", 25.0f);
        this.verdana30 = this.getFont("verdana", 30.0f);
        this.verdana35 = this.getFont("verdana", 35.0f);
        this.verdana40 = this.getFont("verdana", 40.0f);
        this.verdana45 = this.getFont("verdana", 45.0f);
        this.verdana50 = this.getFont("verdana", 50.0f);
	}
	
	public UnicodeFontRenderer getFont(String name, float size) {
        UnicodeFontRenderer unicodeFont = null;
        try {
            if (this.fonts.containsKey(name) && this.fonts.get(name).containsKey(Float.valueOf(size))) {
                return this.fonts.get(name).get(Float.valueOf(size));
            }
            InputStream inputStream = this.getClass().getResourceAsStream("fonts/" + name + ".ttf");
            Font font = null;
            font = Font.createFont(0, inputStream);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size));
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            HashMap<Float, UnicodeFontRenderer> map = new HashMap<Float, UnicodeFontRenderer>();
            if (this.fonts.containsKey(name)) {
                map.putAll((Map)this.fonts.get(name));
            }
            map.put(Float.valueOf(size), unicodeFont);
            this.fonts.put(name, map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return unicodeFont;
    }

    public UnicodeFontRenderer getFont(String name, float size, boolean b) {
        UnicodeFontRenderer unicodeFont = null;
        try {
            if (this.fonts.containsKey(name) && this.fonts.get(name).containsKey(Float.valueOf(size))) {
                return this.fonts.get(name).get(Float.valueOf(size));
            }
            InputStream inputStream = this.getClass().getResourceAsStream("fonts/" + name + ".otf");
            Font font = null;
            font = Font.createFont(0, inputStream);
            unicodeFont = new UnicodeFontRenderer(font.deriveFont(size));
            unicodeFont.setUnicodeFlag(true);
            unicodeFont.setBidiFlag(Minecraft.getMinecraft().mcLanguageManager.isCurrentLanguageBidirectional());
            HashMap<Float, UnicodeFontRenderer> map = new HashMap<Float, UnicodeFontRenderer>();
            if (this.fonts.containsKey(name)) {
                map.putAll((Map)this.fonts.get(name));
            }
            map.put(Float.valueOf(size), unicodeFont);
            this.fonts.put(name, map);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return unicodeFont;
    }
}
