package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 23.05.2021
 * threadsafe storage for SimpleUser
 */

import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {

    private Map<Integer, SimpleUser> storage = new HashMap();

    public UserStorage(Map<Integer, SimpleUser> storage) {
        this.storage = storage;
    }

    public synchronized boolean add(int key, SimpleUser user) {
        boolean result = false;
        SimpleUser temp = this.storage.putIfAbsent(key, user);
        if (temp == null) {
            result = true;
        }
        return result;
    }

    public synchronized boolean update(int key, SimpleUser user) {
        boolean result = false;
        SimpleUser temp = this.storage.replace(key, user);
        if (temp != null) {
           result = true;
        }
        return result;
    }

    public synchronized boolean delete(int key, SimpleUser user) {
        return this.storage.remove(key, user);
    }

    public synchronized boolean transfer(int fromKey, int toKey, int amount) {
        boolean result = false;
        SimpleUser fromUser = this.storage.get(fromKey);
        SimpleUser toUser = this.storage.get(toKey);
        if (fromUser != null && toUser != null && fromUser.getAmount() >= amount) {
            toUser.setAmount(toUser.getAmount() + amount);
            fromUser.setAmount(fromUser.getAmount() - amount);
            result = true;
        }
        return result;
    }
}
