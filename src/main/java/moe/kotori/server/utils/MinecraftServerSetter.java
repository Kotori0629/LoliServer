package moe.kotori.server.utils;

import com.mojang.serialization.DynamicOps;
import joptsimple.OptionSet;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.DataPackConfig;

public class MinecraftServerSetter {
    private static OptionSet options;
    private static DataPackConfig dataPackConfig;
    private static DynamicOps<Tag> registryreadops;

    public static void init(OptionSet options, DataPackConfig dataPackConfig, DynamicOps<Tag> registryreadops) {
        MinecraftServerSetter.options = options;
        MinecraftServerSetter.dataPackConfig = dataPackConfig;
        MinecraftServerSetter.registryreadops = registryreadops;
    }

    public static OptionSet getOptions() {
        return options;
    }

    public static DataPackConfig getDataPackConfig() {
        return dataPackConfig;
    }

    public static DynamicOps<Tag> getRegistryReadOps() {
        return registryreadops;
    }
}
