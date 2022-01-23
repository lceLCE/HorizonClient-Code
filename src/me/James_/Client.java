package me.James_;

import java.util.logging.Logger;

import me.James_.command.CommandManager;
import me.James_.mod.ModManager;
import me.James_.ui.click.UIClick;
import me.James_.util.FileUtil;
import me.James_.util.fontRenderer.FontManager;

public class Client {
	
	public static String CLIENT_name = "Horizon";
	public static String CLIENT_LOGO = "Horizon";
	public static String CLIENT_NAME = "Horizon";
	public static String CLIENT_NAME2 = "Horizon";
	public static String CLIENT_VER = "0.1";
	public static boolean isClientLoading;
	public static Client instance;
	public static Logger LOGGER = Logger.getLogger(CLIENT_NAME);
	
	public ModManager modMgr;
	public FileUtil fileMgr;
	public FontManager fontMgr;
	public static FontManager fontManager;
	public UIClick clickface;
	public CommandManager cmdMgr;
	
	public Client() {
		instance = this;
		isClientLoading = true;
	}
	
	public void onClientStart() {
		modMgr = new ModManager();
		fontManager = fontMgr = new FontManager();
		fileMgr = new FileUtil();
		clickface = new UIClick();
		cmdMgr = new CommandManager();
		new Tiplan();
		isClientLoading = false;
	}
	
	public void onClientStop() {
		
	}
	
	public UIClick showClickGui() {
		return this.clickface;
	}
}
