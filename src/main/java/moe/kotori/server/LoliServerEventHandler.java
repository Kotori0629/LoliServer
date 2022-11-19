package moe.kotori.server;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.block.CraftBlock;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

/**
 * @author Kotori0629
 */
public class LoliServerEventHandler {

    public static final Logger LOGGER = LogManager.getLogger();
    public static boolean isDropItems;

    public LoliServerEventHandler() {
        LoliServerEventHandler.LOGGER.info("Registered LoliServer EventHandler");
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onBreakBlockEvent(BlockEvent.BreakEvent event) {
        if (!event.getLevel().isClientSide()) {
            CraftBlock craftBlock = CraftBlock.at(event.getLevel(), event.getPos());
            BlockBreakEvent bukkitEvent = new BlockBreakEvent(craftBlock, ((ServerPlayer) event.getPlayer()).getBukkitEntity());
            bukkitEvent.setCancelled(event.isCanceled());
            bukkitEvent.setExpToDrop(event.getExpToDrop());
            Bukkit.getPluginManager().callEvent(bukkitEvent);
            event.setCanceled(bukkitEvent.isCancelled());
            event.setExpToDrop(bukkitEvent.getExpToDrop());
            isDropItems = bukkitEvent.isDropItems();
        }
    }

    @SubscribeEvent
    public void onAdvancementEvent(AdvancementEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            Bukkit.getPluginManager().callEvent(new PlayerAdvancementDoneEvent(player.getBukkitEntity(), event.getAdvancement().bukkit));
        }
    }
}
