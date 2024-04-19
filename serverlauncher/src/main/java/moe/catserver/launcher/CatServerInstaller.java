package moe.catserver.launcher;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class CatServerInstaller {
    // Versions
    public String forgeVersion;
    public String mcpVersion;
    public String minecraftVersion;

    // Forges
    public String forgeFolder;
    public File forgeUniversalJar;
    public File forgeServerJar;

    // lzma
    public File serverLzma;

    // Vanillas
    public String vanillaFolder;
    public File vanillaExtraJar;
    public File vanillaSlimJar;
    public File vanillaSrgJar;
    public File minecraftServerJar;

    // Mcp
    public String mcpFolder;
    public File mcpZip;
    public File mcpMappingsTxt;

    protected CatServerInstaller() throws IOException {
        this.forgeVersion = Launcher.getVersion("forge");
        this.mcpVersion = Launcher.getVersion("mcp");
        this.minecraftVersion = Launcher.getVersion("minecraft");

        this.forgeFolder = Launcher.getLauncherJarDir() + "/libraries/net/minecraftforge/forge/%s-%s/".formatted(minecraftVersion, forgeVersion);
        this.forgeUniversalJar = new File(this.forgeFolder + "forge-%s-%s-universal.jar".formatted(minecraftVersion, forgeVersion));
        this.forgeServerJar = new File(this.forgeFolder + "forge-%s-%s-server.jar".formatted(minecraftVersion, forgeVersion));

        this.serverLzma = new File(Launcher.getLauncherJarDir() + "/caches/server.lzma");

        this.vanillaFolder = Launcher.getLauncherJarDir() + "/libraries/net/minecraft/server/%s-%s/".formatted(minecraftVersion, mcpVersion);
        this.vanillaExtraJar = new File(this.vanillaFolder + "server-%s-%s-extra.jar".formatted(minecraftVersion, mcpVersion));
        this.vanillaSlimJar = new File(this.vanillaFolder + "server-%s-%s-slim.jar".formatted(minecraftVersion, mcpVersion));
        this.vanillaSrgJar = new File(this.vanillaFolder + "server-%s-%s-srg.jar".formatted(minecraftVersion, mcpVersion));

        this.minecraftServerJar = new File(Launcher.getLauncherJarDir() + "/caches/minecraft_server.%s.jar".formatted(minecraftVersion));

        this.mcpFolder = Launcher.getLauncherJarDir() + "/libraries/de/oceanlabs/mcp/mcp_config/%s-%s/".formatted(minecraftVersion, mcpVersion);
        this.mcpZip = new File(this.mcpFolder + "mcp_config-%s-%s.zip".formatted(minecraftVersion, mcpVersion));
        this.mcpMappingsTxt = new File(this.mcpFolder + "mcp_config-%s-%s-mappings.txt".formatted(minecraftVersion, mcpVersion));

        File outputLog = new File(Launcher.getLauncherJarDir() + "/caches/installLog.log");
        if (outputLog.exists()) {
            outputLog.delete();
        }
        outputLog.createNewFile();
    }
}
