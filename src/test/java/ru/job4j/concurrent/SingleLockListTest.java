package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 25.05.2021
 * Потокобезопасная коллекция на основе simpleArray
 */

import org.junit.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SingleLockListTest {

    private Set<Integer> set = new TreeSet<>();
    private int one;
    private int two;

    @Test
    public void add() {
        SingleLockList<Integer> list = new SingleLockList(new SimpleArray());
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();

        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2)));
    }

    @Test
    public void whenGetValue() {
        SingleLockList<Integer> list = new SingleLockList(new SimpleArray());
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        try {
            first.join();
            second.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread third = new Thread(() -> one = list.get(0));
        Thread fourth = new Thread(() -> two = list.get(1));

        third.start();
        fourth.start();
        try {
            third.join();
            fourth.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        set.add(one);
        set.add(two);
        assertTrue(set.contains(one));
        assertTrue(set.contains(two));
    }

    @Test
    public void whenGetValueByIterator() {
        SingleLockList<Integer> list = new SingleLockList(new SimpleArray());
        list.add(1);
        list.add(2);
        Iterator it = list.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(false));
    }
}