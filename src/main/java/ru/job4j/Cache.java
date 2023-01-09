package ru.job4j;

public class Cache {
    private static Cache cache;

    public static Cache instOf() {
        synchronized (Cache.class) {
            if (cache == null) {
                cache = new Cache();
            }
            return cache;
        }
    }
}
