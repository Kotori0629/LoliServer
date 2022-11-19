package moe.kotori.server.patcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CatServer
 */
public class PatcherManager {
    private static Map<String, IPatcher> pluginPatcher = new HashMap<>();

    static {

    }

    public static IPatcher getPluginPatcher(String pluginName) {
        return pluginPatcher.get(pluginName);
    }

    public static boolean registerPluginPatcher(String pluginName, IPatcher patcher) {
        if (!pluginPatcher.containsKey(pluginName) && patcher != null) {
            pluginPatcher.put(pluginName, patcher);
            return true;
        }
        return false;
    }
}
