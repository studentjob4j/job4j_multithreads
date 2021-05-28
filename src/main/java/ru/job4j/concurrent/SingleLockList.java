package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 25.05.2021
 * Потокобезопасная коллекция на основе simpleArray
 */

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {

    @GuardedBy("this")
    private final SimpleArray<T> list;

    public SingleLockList(SimpleArray list) {
        this.list = list.clone();
    }

    public synchronized void add(T value) {
         this.list.add(value);
    }

    public synchronized T get(int index) {
        return this.list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    private SimpleArray<T> copy(SimpleArray<T> list) {
        SimpleArray<Integer> temp = new SimpleArray<>();
        this.list.forEach(x -> temp.add((Integer) x));
        return (SimpleArray<T>) temp;
    }
}
