package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 26.05.2021
 * При запуске создается поток main и еще 2 доп потока. Один из доп потоков занимает метод await
 * и засыпает, главный поток увеличивает счетчик, при определенном числе счетчика
 * поток просыпается .Для того , чтобы наверняка 2 поток начал работать я усыпил главный
 * поток на 2 сек .Второй доп поток заходит в метод await и засыпает.
 * главный поток вновь запускает счетчик
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountBarrier {

    private static final Logger LOG = LoggerFactory.getLogger(CountBarrier.class.getName());
    private final Object monitor = this;
    private final int total;
    private int count = 1;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            System.out.println(count);
            if (count >= total) {
                monitor.notifyAll();
            }
            count++;
        }
    }

    public void await() {
       synchronized (monitor) {
           while (count < total) {
               try {
                   monitor.wait();
               } catch (InterruptedException e) {
                 LOG.error(e.getMessage(), e);
               }
           }
           count = 1;
       }
    }

    public static void main(String[] args) {
        CountBarrier countBarrier = new CountBarrier(2);
        Thread one = new Thread(() -> {
            countBarrier.await();
            System.out.println("Действие 1 завершено");
        });
        Thread two = new Thread(() -> {
            countBarrier.await();
            System.out.println("Действие 2 завершено");
        });
        one.start();
        two.start();
        System.out.println(one.getState());
        System.out.println(two.getState());
        countBarrier.count();
        countBarrier.count();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
        countBarrier.count();
        countBarrier.count();
        try {
            one.join();
            two.join();
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
        System.out.println(one.getState());
        System.out.println(two.getState());
    }
}
