package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 27.05.2021
 * Данный класс реализует блокирующую очередь на основе линкедлиста. Если очередь
 * пустая , то Producer  добавляем значение до тех пор пока размер не станет равным установленному
 * пределу .Далее очередь засыпает .  Consumer берет из очереди значения и если очередь пустая ,
 * нить засыпает .
 */

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int size;

    public SimpleBlockingQueue(int size) {
        this.size = size;
    }

    public void offer(T value) {
        synchronized (this) {
         while (queue.size() >= size) {
             try {
                 wait();
             } catch (InterruptedException e) {
                 Thread.currentThread().interrupt();
                 e.printStackTrace();
             }
         }
            queue.offer(value);
            notifyAll();
            System.out.println("Заполняем");
        }
    }

    public T poll() throws InterruptedException {
        T result = null;
        synchronized (this) {
            while (queue.isEmpty()) {
                wait();
            }
            result = queue.poll();
            System.out.println("Получаем - " + result);
            notifyAll();
        }
        return result;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

