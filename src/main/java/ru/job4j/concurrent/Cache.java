package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 18.05.2021
 * this class shows correct use method instOf in multithreads program when
 * we need only one Cache
 */

public final class Cache {

    private static Cache cache;

    public synchronized static Cache instOf() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
