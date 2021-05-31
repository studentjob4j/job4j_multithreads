package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 31.05.2021
 * this class shows correct use non blocking algoritm CAS
 */

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenUseCASCount() throws InterruptedException {
        CASCount count = new CASCount();
        Thread one = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("one");
                count.increment();
            }
        });

        Thread two = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("two");
                count.increment();
            }
        });
        one.start();
        two.start();
        one.join();
        two.join();
        assertThat(count.get(), is(10));
    }
}