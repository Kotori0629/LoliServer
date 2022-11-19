package moe.kotori.server.utils;

import moe.kotori.LoliServer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * @author CatServer
 */
public class ServerUtils {
    public static void acceptEula() {
        Properties properties = new Properties();
        try (FileInputStream fileinputstream = new FileInputStream("eula.txt")) {
            properties.load(fileinputstream);
        } catch (Exception ignored) { }

        if (!"true".equals(properties.getProperty("eula"))) {
            try (FileOutputStream fileoutputstream = new FileOutputStream("eula.txt")) {
                properties.setProperty("eula", "true");
                properties.store(fileoutputstream, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://account.mojang.com/documents/minecraft_eula).");
            } catch (Exception e) {
                LoliServer.LOGGER.warn(e.toString());
            }
        }
    }
}
