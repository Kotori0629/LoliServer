package moe.kotori.server.entity;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.FakePlayer;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.permissions.Permission;

/**
 * @author CatServer
 */
public class CraftCustomFakePlayer extends CraftPlayer {

    public CraftCustomFakePlayer(CraftServer server, FakePlayer entity) {
        super(server, entity);
    }

    /* TODO : FakePlayer
    @Override
    public boolean hasPermission(String name) {
        if (CatServer.getConfig().fakePlayerPermissions.contains(name)) {
            return true;
        }
        return super.hasPermission(name);
    }

    @Override
    public boolean hasPermission(Permission perm) {
        if (CatServer.getConfig().fakePlayerPermissions.contains(perm.getName())) {
            return true;
        }
        return super.hasPermission(perm);
    }

    @Override
    public boolean isPermissionSet(String name) {
        if (CatServer.getConfig().fakePlayerPermissions.contains(name)) {
            return true;
        }
        return super.hasPermission(name);
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        if (CatServer.getConfig().fakePlayerPermissions.contains(perm.getName())) {
            return true;
        }
        return super.hasPermission(perm);
    }
     */

    @Override
    public boolean isOnline() {
        return true;
    }
}