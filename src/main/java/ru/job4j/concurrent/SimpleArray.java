package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 25.05.2021
 * Потокобезопасная коллекция на основе simpleArray
 */

import java.util.*;

public class SimpleArray<T> implements Iterable<T>, Cloneable {

    private Object[] container;
    private int index;
    private int modCount;

    public SimpleArray() {
        this.container = new Object[10];
    }

    public T get(int pos) {
        Objects.checkIndex(pos, index);
        return (T) container[pos];
    }

    public void add(T model) {
        if (index == container.length) {
            expansionArray();
        }
        container[index++] = model;
        modCount++;
    }

    private void expansionArray() {
        container = Arrays.copyOf(container, container.length * 2);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int position;
            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return position < index;
            }

            @Override
            public T next() {
                T result = null;
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                } else {
                    result = (T) container[position++];
                }
                return result;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleArray<?> array = (SimpleArray<?>) o;
        return index == array.index && modCount == array.modCount
                && Arrays.equals(container, array.container);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(index, modCount);
        result = 31 * result + Arrays.hashCode(container);
        return result;
    }

    @Override
    protected SimpleArray clone() {
        SimpleArray result = null;
        try {
            result = (SimpleArray) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
