package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 14.05.2021
 */

public class ThreadState {

    public static void main(String[] args) {
        Thread first = new Thread(
                () -> { }
        );
        Thread second = new Thread(
                () -> { }
        );
        System.out.println(first.getState());
        System.out.println(second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                || (second.getState() != Thread.State.TERMINATED)) {
            System.out.println(first.getState());
            System.out.println(second.getState());
        }

        System.out.println(first.getState());
        System.out.println(second.getState());
        System.out.println("Работа 2 нитей завершена");
    }
}
