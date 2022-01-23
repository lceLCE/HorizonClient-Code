package net.minecraft.util;

public interface IRegistry<K, V> extends Iterable<V>
{
    V getObject(K name);

    /**
     * Register an object on this registry.
     *  
     * @param key The key of the registry entry
     * @param value The value of the registry entry
     */
    void putObject(K key, V value);
}
