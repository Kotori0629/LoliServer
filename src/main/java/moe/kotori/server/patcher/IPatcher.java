package moe.kotori.server.patcher;

/**
 * @author CatServer
 */
public interface IPatcher {
    byte[] transform(String className, byte[] basicClass);
}
