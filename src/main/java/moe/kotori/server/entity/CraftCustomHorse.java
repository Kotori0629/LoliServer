package moe.kotori.server.entity;

import net.minecraft.world.entity.animal.horse.AbstractHorse;

import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftAbstractHorse;
import org.bukkit.entity.Horse;

public class CraftCustomHorse extends CraftAbstractHorse {

    public CraftCustomHorse(CraftServer server, AbstractHorse entity) {
        super(server, entity);
    }

    @Override
    public String toString() {
        return "CraftCustomHorse";
    }

    @Override
    public Horse.Variant getVariant() {
        return Horse.Variant.MOD_CUSTOM;
    }
}