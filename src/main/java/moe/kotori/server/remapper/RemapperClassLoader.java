package moe.kotori.server.remapper;

import cpw.mods.modlauncher.TransformingClassLoader;

public interface RemapperClassLoader {
    ClassLoaderRemapper getRemapper();

    static ClassLoader asTransforming(ClassLoader classLoader) {
        boolean found = false;
        while (classLoader != null) {
            if (classLoader instanceof TransformingClassLoader || classLoader instanceof RemapperClassLoader) {
                found = true;
                break;
            } else {
                classLoader = classLoader.getParent();
            }
        }
        return found ? classLoader : RemapperClassLoader.class.getClassLoader();
    }
}
