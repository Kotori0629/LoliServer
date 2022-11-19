package moe.kotori.server.level;

/**
 * @author CatServer
 */
public class BukkitWorldSetter {
    private static final ThreadLocal<BukkitWorldSetter> threadLocal = new ThreadLocal<>();
    public static BukkitWorldSetter get() {
        BukkitWorldSetter currentThreadObject = threadLocal.get();
        if (currentThreadObject == null) {
            currentThreadObject = new BukkitWorldSetter();
            threadLocal.set(currentThreadObject);
        }
        return currentThreadObject;
    }

    private org.bukkit.generator.ChunkGenerator generator;
    private org.bukkit.World.Environment environment;
    private org.bukkit.generator.BiomeProvider biomeProvider;

    public void setWorld(org.bukkit.generator.ChunkGenerator gen, org.bukkit.World.Environment env, org.bukkit.generator.BiomeProvider biome) {
        generator = gen;
        environment = env;
        biomeProvider = biome;
    }

    public org.bukkit.generator.ChunkGenerator getChunkGenerator() {
        return generator;
    }

    public org.bukkit.World.Environment getEnvironment() {
        return environment;
    }

    public org.bukkit.generator.BiomeProvider getBiomeProvider() {
        return biomeProvider;
    }

    public void reset() {
        generator = null;
        environment = null;
        biomeProvider = null;
    }
}
