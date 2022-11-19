package moe.kotori.server.entity;

import net.minecraft.world.entity.monster.AbstractSkeleton;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftAbstractSkeleton;
import org.bukkit.entity.Skeleton;
import org.jetbrains.annotations.NotNull;

public class CraftCustomSkeleton extends CraftAbstractSkeleton {

    public CraftCustomSkeleton(CraftServer server, AbstractSkeleton entity) {
        super(server, entity);
    }

    @Override
    public @NotNull Skeleton.SkeletonType getSkeletonType() {
        return Skeleton.SkeletonType.MOD_CUSTOM;
    }
}
