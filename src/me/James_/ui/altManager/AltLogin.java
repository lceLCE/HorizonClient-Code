package me.James_.ui.altManager;

import java.lang.reflect.Field;
import java.net.Proxy;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class AltLogin {
	
	
    public static void login(String username, String password) {
    	if(password.contains("OfflineAccountName")) {
        	Session sess = new Session(username, "", "", "mojang");
        	try {
        		Minecraft.getMinecraft().session = sess;
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	GuiAltManager.status = "Logged in (Cracked name)";
        } else {
        	YggdrasilAuthenticationService authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication authentication = (YggdrasilUserAuthentication)authenticationService.createUserAuthentication(Agent.MINECRAFT);
            authentication.setUsername(username);
            authentication.setPassword(password);
            try {
                authentication.logIn();
                Session sess = new Session(authentication.getSelectedProfile().getName(), authentication.getSelectedProfile().getId().toString(), authentication.getAuthenticatedToken(), "mojang");
                Minecraft.getMinecraft().session = sess;
                GuiAltManager.status = "Logged in (Premium name)";
            } catch (Exception e) {
            	GuiAltManager.status = e.getMessage();
            }
        }
    }
}

