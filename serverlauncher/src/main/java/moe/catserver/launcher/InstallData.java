package moe.catserver.launcher;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InstallData {
    public static Map<String, String> setupVersions() {
        Map<String, String> versionMap = new HashMap<>();

        // Put some version from the txt File
        var forgeVersion = readFileFromJar("versions/forge.txt").get(0);
        var mcpVersion = readFileFromJar("versions/mcp.txt").get(0);
        var minecraftVersion = readFileFromJar("versions/minecraft.txt").get(0);
        versionMap.put("forge", forgeVersion);
        versionMap.put("mcp", mcpVersion);
        versionMap.put("minecraft", minecraftVersion);

        return versionMap;
    }

    public static @NotNull List<String> setupLaunchArgs() {
        List<String> launchArgs = new ArrayList<>();

        // Put launchArgs from OS's args txt
        if (Launcher.isWindows) {
            launchArgs.addAll(readFileFromJar("data/win_args.txt"));
        } else {
            launchArgs.addAll(readFileFromJar("data/unix_args.txt"));
        }

        return launchArgs;
    }

    // https://github.com/MohistMC/Tools/blob/master/src/main/java/com/mohistmc/tools/FileUtils.java
    public static List<String> readFileFromJar(String filePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(InstallData.class.getClassLoader().getResourceAsStream(filePath))))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
