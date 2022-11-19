package org.bukkit.plugin.java;

import java.net.URLClassLoader;
import java.util.List;

public interface JavaPluginLoaderBridge {
    List<URLClassLoader> bridge$getLoaders();

    void bridge$setClass(final String name, final Class<?> clazz);
}
