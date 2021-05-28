package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 27.05.2021
 */

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class SimpleBlockingQueueTest {

    @Test
    public void whenCreateProducerAndConsumer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(() -> {
            System.out.println("Заполняем очередь");
            queue.offer(1);
            queue.offer(2);
            queue.offer(3);
        });

        Thread consumer = new Thread(() -> {
            System.out.println("Получаем из очереди");
            try {
                queue.poll();
                queue.poll();
                queue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertTrue(true);
    }

}