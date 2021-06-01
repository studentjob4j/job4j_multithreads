package ru.job4j.concurrent;

import org.junit.Test;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 1.06.2021
 * не блокируюший кеш использую атомарные методы putifabsent , remove, computeifpresent
 */

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ConcurrentCacheTest {

    @Test
    public void whenAddValue() {
        ConcurrentCache cache = new ConcurrentCache();
        assertTrue(cache.add(new Base(1, 1)));
    }

    @Test
    public void whenDeleteValue() {
        ConcurrentCache cache = new ConcurrentCache();
        cache.add(new Base(1, 1));
        cache.delete(new Base(1, 1));
        assertThat(cache.getSize(), is(0));
    }

    @Test
    public void whenUpdateValue() {
        ConcurrentCache cache = new ConcurrentCache();
        cache.add(new Base(1, 1));
        Base newValue = new Base(1, 1);
        newValue.setName("new");
        assertTrue(cache.update(newValue));
    }

    @Test(expected = OptimisticException.class)
    public void whenGetException() {
        ConcurrentCache cache = new ConcurrentCache();
        cache.add(new Base(1, 1));
        Base newValue = new Base(1, 2);
        cache.update(newValue);
    }
}
