package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 23.05.2021
 */

public class SimpleUser {

    private final int id;
    private int amount;

    public SimpleUser(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "SimpleUser{" + "id=" + id + ", amount=" + amount + '}';
    }
}
