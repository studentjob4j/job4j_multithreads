package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 23.05.2021
 * threadsafe storage for SimpleUser
 */

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class UserStorageTest {

    private static final Logger LOG = LoggerFactory.getLogger(UserStorageTest.class.getName());
    private UserStorage storage;
    private final SimpleUser user = new SimpleUser(1, 100);
    private final SimpleUser user2 = new SimpleUser(2, 100);
    private boolean resultOne;
    private boolean resultTwo;

    @Before
    public void createStorage() {
        this.storage = new UserStorage(new HashMap<>());
    }

    @Test
    public void whenAddUser() {

        Thread one = new Thread(() -> {
                this.resultOne =  this.storage.add(1, new SimpleUser(1, 100));
                System.out.println(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getState());
        });

        Thread two = new Thread(() -> {
                this.resultTwo = this.storage.add(2, new SimpleUser(2, 200));
                System.out.println(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getState());
        });
        one.start();
        two.start();
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
           LOG.error(e.getMessage(), e);
        }
        assertTrue(resultOne);
        assertTrue(resultTwo);
    }

    @Test
    public void whenUpdateUser() {
        this.storage.add(1, new SimpleUser(1, 150));
        this.storage.add(2, new SimpleUser(2, 150));
        SimpleUser user3 = new SimpleUser(3, 100);
        SimpleUser user4 = new SimpleUser(4, 100);
        Thread one = new Thread(() -> {

            resultOne = this.storage.update(1, user3);
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getState());
        });
        Thread two = new Thread(() -> {

            resultTwo = this.storage.update(2, user4);
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getState());
        });
        one.start();
        two.start();
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
        assertTrue(resultOne);
        assertTrue(resultTwo);
    }

    @Test
    public void whenDelete() {
        this.storage.add(1, user);
        this.storage.add(2, user2);
        Thread one = new Thread(() -> {
            resultOne = this.storage.delete(1, user);
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getState());
        });
        Thread two = new Thread(() -> {
            resultTwo = this.storage.delete(2, user2);
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getState());
        });
        two.start();
        one.start();
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
        System.out.println(one.getState());
        System.out.println(two.getState());
        assertTrue(resultOne);
        assertTrue(resultTwo);
    }

    @Test
    public void whenTransfer() {
        this.storage.add(1, user);
        this.storage.add(2, user2);
        Thread one = new Thread(() -> {
            resultOne = this.storage.transfer(1, 2, 50);
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getState());
        });
        Thread two = new Thread(() -> {
            resultTwo = this.storage.transfer(2, 1, 50);
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getState());
        });
        two.start();
        one.start();
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
        System.out.println(one.getState());
        System.out.println(two.getState());
        assertTrue(resultOne);
        assertTrue(resultTwo);
    }
}