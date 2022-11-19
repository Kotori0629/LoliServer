package moe.kotori.server.utils;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moe.kotori.LoliServer;
import moe.kotori.server.entity.CraftCustomEntity;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.craftbukkit.v1_19_R1.enchantments.CraftEnchantment;
import org.bukkit.craftbukkit.v1_19_R1.potion.CraftPotionEffectType;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffectType;

public class BukkitInjector {

    public static boolean initializedBukkit = false;
    public static BiMap<ResourceKey<LevelStem>, World.Environment> environment =
            HashBiMap.create(ImmutableMap.<ResourceKey<LevelStem>, World.Environment>builder()
                    .put(LevelStem.OVERWORLD, World.Environment.NORMAL)
                    .put(LevelStem.NETHER, World.Environment.NETHER)
                    .put(LevelStem.END, World.Environment.THE_END)
                    .build());

    public static Map<Villager.Profession, ResourceLocation> profession = new HashMap<>();
    public static Map<org.bukkit.attribute.Attribute, ResourceLocation> attributemap = new HashMap<>();
    public static Map<net.minecraft.world.entity.EntityType<?>, String> entityTypeMap = new HashMap<>();

    public static void injectItemMaterials() {
        for (Item item : ForgeRegistries.ITEMS) {
            ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(item);
            if (!resourceLocation.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                String materialName = normalizeName(resourceLocation.toString()).replace("RESOURCEKEYMINECRAFT_ITEM__", "");
                int id = Item.getId(item);
                Material material = Material.addMaterial(materialName, id, false, resourceLocation.getNamespace());
                CraftMagicNumbers.ITEM_MATERIAL.put(item, material);
                CraftMagicNumbers.MATERIAL_ITEM.put(material, item);
                if (material != null) {
                    LoliServer.LOGGER.info("Registered forge Item Material: {}", material.name());
                }
            }
        }
    }


    public static void injectBlockMaterials() {
        for (Block block : ForgeRegistries.BLOCKS) {
            ResourceLocation resourceLocation = ForgeRegistries.BLOCKS.getKey(block);
            if (!resourceLocation.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                String materialName = normalizeName(resourceLocation.toString()).replace("RESOURCEKEYMINECRAFT_BLOCK__", "");
                int id = Item.getId(block.asItem());
                Material material = Material.addMaterial(materialName, id, true, resourceLocation.getNamespace());
                CraftMagicNumbers.BLOCK_MATERIAL.put(block, material);
                CraftMagicNumbers.MATERIAL_BLOCK.put(material, block);
                if (material != null) {
                    LoliServer.LOGGER.info("Registered forge Block Material: {}", material.name());
                }
            }
        }
    }


    public static void injectEnchantments() {
        for (Map.Entry<ResourceKey<Enchantment>, Enchantment> entry : ForgeRegistries.ENCHANTMENTS.getEntries()) {
            org.bukkit.enchantments.Enchantment.registerEnchantment(new CraftEnchantment(entry.getValue()));
            LoliServer.LOGGER.info("Registered forge Enchantment: {}", entry.getValue().getDescriptionId());
        }
    }

    public static void injectMobEffects() {
        for (Map.Entry<ResourceKey<MobEffect>, MobEffect> entry : ForgeRegistries.MOB_EFFECTS.getEntries()) {
            PotionEffectType pet = new CraftPotionEffectType(entry.getValue());
            if (PotionEffectType.getByName(pet.getName()) == null) {
                PotionEffectType.registerPotionEffectType(pet);
                LoliServer.LOGGER.info("Registered forge MobEffect: {} <{}>", pet.getName(), pet.getId());
            }
        }
    }

    public static void injectBiomes() {
        List<String> map = new ArrayList<>();
        for (Map.Entry<ResourceKey<Biome>, Biome> entry : ForgeRegistries.BIOMES.getEntries()) {
            String biomeName = entry.getKey().registry().getNamespace();
            if (!biomeName.equals(NamespacedKey.MINECRAFT) && !map.contains(biomeName)) {
                map.add(biomeName);
                org.bukkit.block.Biome biome = EnumHelper.addEnum(org.bukkit.block.Biome.class, biomeName, new Class[0]);
                LoliServer.LOGGER.info("Registered forge Biome: {}", biome.name());
            }
        }
        map.clear();
    }


    public static void injectEnvironment(net.minecraft.core.Registry<LevelStem> registry) {
        int i = World.Environment.values().length;
        for (Map.Entry<ResourceKey<LevelStem>, LevelStem> entry : registry.entrySet()) {
            ResourceKey<LevelStem> key = entry.getKey();
            World.Environment environment1 = environment.get(key);
            if (environment1 == null) {
                String name = normalizeName(key.location().toString());
                int id = i - 1;
                environment1 = EnumHelper.addEnum(World.Environment.class, name, new Class[]{Integer.TYPE}, id);
                environment.put(key, environment1);
                LoliServer.LOGGER.info("Registered forge DimensionType as environment {}", environment1);
                i++;
            }
        }
    }

    public static WorldType addEnumWorldType(String name) {
        WorldType worldType = EnumHelper.addEnum(WorldType.class, name, new Class[]{String.class}, name);
        Map<String, WorldType> BY_NAME = ObfuscationReflectionHelper.getPrivateValue(WorldType.class, null, "BY_NAME");
        BY_NAME.put(name.toUpperCase(), worldType);
        return worldType;
    }

    public static void injectEntityTypes() {
        for (Map.Entry<ResourceKey<net.minecraft.world.entity.EntityType<?>>, net.minecraft.world.entity.EntityType<?>> entity : ForgeRegistries.ENTITY_TYPES.getEntries()) {
            ResourceLocation resourceLocation = entity.getKey().location();
            if (!resourceLocation.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                String entityType = normalizeName(resourceLocation.toString());
                int typeId = entityType.hashCode();
                EntityType bukkitType = EnumHelper.addEnum(EntityType.class, entityType, new Class[]{String.class, Class.class, Integer.TYPE, Boolean.TYPE}, entityType.toLowerCase(), CraftCustomEntity.class, typeId, false);
                EntityType.NAME_MAP.put(entityType.toLowerCase(), bukkitType);
                EntityType.ID_MAP.put((short) typeId, bukkitType);
                entityTypeMap.put(entity.getValue(), entityType);
                LoliServer.LOGGER.info("Registered forge EntityType: {}", bukkitType.getName());
            }
        }
    }

    public static void addEnumVillagerProfession() {
        for (Map.Entry<ResourceKey<VillagerProfession>, VillagerProfession> villagerProfession : ForgeRegistries.VILLAGER_PROFESSIONS.getEntries()) {
            ResourceLocation resourceLocation = villagerProfession.getKey().registry();
            if (!resourceLocation.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                String name = normalizeName(resourceLocation.toString());
                Villager.Profession vp = EnumHelper.addEnum(Villager.Profession.class, name, new Class[0]);
                profession.put(vp, resourceLocation);
                LoliServer.LOGGER.debug("Registered forge VillagerProfession as Profession {}", vp.name());
            }
        }
    }

    public static void addEnumAttribute() {
        for (Map.Entry<ResourceKey<Attribute>, Attribute> attribute : ForgeRegistries.ATTRIBUTES.getEntries()) {
            ResourceLocation resourceLocation = attribute.getKey().registry();
            String name = normalizeName(resourceLocation.getPath());
            if (!resourceLocation.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                org.bukkit.attribute.Attribute ab = EnumHelper.addEnum(org.bukkit.attribute.Attribute.class, name, new Class[]{String.class}, resourceLocation.getPath());
                attributemap.put(ab, resourceLocation);
                LoliServer.LOGGER.debug("Registered forge Attribute as Attribute(Bukkit) {}", ab.name());
            }
        }
    }

    public static String normalizeName(String name) {
        return name.toUpperCase(java.util.Locale.ENGLISH).replaceAll("(:|\\s)", "_").replaceAll("\\W", "");
    }
}
