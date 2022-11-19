package moe.kotori.api;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;

/**
 * @author Kotori0629
 */
public class LoliServerAPI {
    public static ServerLevel toNMS(org.bukkit.World world) {
        return ((CraftWorld) world).getHandle();
    }

    public static ServerPlayer toNMS(org.bukkit.entity.Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public static Entity toNMS(org.bukkit.entity.Entity entity) {
        return ((CraftEntity) entity).getHandle();
    }

    public static MinecraftServer toNMS(org.bukkit.Server server) {
        return ((CraftServer) server).getServer();
    }
}