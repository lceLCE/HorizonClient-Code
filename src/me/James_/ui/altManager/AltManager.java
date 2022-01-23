package me.James_.ui.altManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import me.James_.Client;
import net.minecraft.client.Minecraft;

public class AltManager {
    public static ArrayList<String> altList = new ArrayList();
    public static ArrayList<GuiAltSlot> guiSlotList = new ArrayList();
    public static final File altFile = new File(Minecraft.getMinecraft().mcDataDir + "/" + Client.CLIENT_NAME + "/alts.txt");

    public static void saveAltsToFile() {
        try {
            PrintWriter writer = new PrintWriter(altFile);
            for (GuiAltSlot slot : guiSlotList) {
                writer.write(String.valueOf(slot.getUsername()) + ":" + slot.getPassword() + "\n");
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadAltsFromFile() {
        guiSlotList.clear();
        try {
            if (!altFile.exists()) {
                altFile.createNewFile();
            } else {
                String line;
                BufferedReader bufferedReader = new BufferedReader(new FileReader(altFile));
                while ((line = bufferedReader.readLine()) != null) {
                    String[] alt = line.split(":");
                    if (alt.length < 2) continue;
                    String username = alt[0];
                    String password = alt[1];
                    guiSlotList.add(new GuiAltSlot(username, password));
                }
                bufferedReader.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

