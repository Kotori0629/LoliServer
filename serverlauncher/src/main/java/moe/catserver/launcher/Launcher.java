package moe.catserver.launcher;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Launcher {
    public static boolean isWindows = System.getProperty("os.name").toLowerCase().contains("windows");
    public static Map<String, String> verisonsMap = new HashMap<>();
    public static boolean isInitializationVersions;

    private static String[] mainArgs;

    public static void main(String[] args) {
        Launcher.mainArgs = args;

        verisonsMap = InstallData.setupVersions();
        isInitializationVersions = true;

        List<String> launchArgs = InstallData.setupLaunchArgs();
        List<String> forgeArgs = new ArrayList<>();
        if (!launchArgs.isEmpty()) {

            launchArgs.stream().filter(s ->
                    s.startsWith("--launchTarget") || s.startsWith("--fml.forgeVersion") || s.startsWith("--fml.mcVersion") || s.startsWith("--fml.forgeGroup") || s.startsWith("--fml.mcpVersion"))
                    .toList()
                    .forEach(arg -> {
                                forgeArgs.add(arg.split(" ")[0]);
                                forgeArgs.add(arg.split(" ")[1]);
            });

            System.out.println("LaunchArgs: " + launchArgs);
            System.out.println("ForgeLaunchArgs: " + forgeArgs);
        }
    }

    public static @Nullable String getVersion(String key) {
        if (isInitializationVersions) {
            if (!verisonsMap.isEmpty()) {
                System.out.println("[CatServerLauncher]: Missing version data (Data map is empty !!!)");
            }
            return verisonsMap.get(key);
        } else {
            System.out.println("[CatServerLauncher]: Missing version data (It's not initialization) ");
        }
        return null;
    }

    public static @NotNull File getLauncherJarDir() {
        return getLauncherJarFile().getParentFile();
    }

    public static @NotNull File getLauncherJarFile() {
        return new File(URLDecoder.decode(Launcher.class.getProtectionDomain().getCodeSource().getLocation().getFile(), StandardCharsets.UTF_8));
    }
}