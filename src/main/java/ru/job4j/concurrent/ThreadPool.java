package ru.job4j.concurrent;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 03.06.2021
 * Принцип работы - Создается объект пул , устанавливается размер очереди и заполняется threads
 * новыми потоками .Каждый поток получает из очереди задание и вызывает метод ран у него.
 * метод инит создает определенное количество потоков в зависимости от кол.свободных ядер процессора
 * метод ворк добавляет в очередь задачи . метод старт запускает потоки . метод shutdown
 * выставляет прерывание потокам.Тест проходит , но получаем исключения т.к очепедь становится
 * пустой и потоки в ожидании , а я вызываю метод shutdown соответсвенно получаю исключение
 */

public class ThreadPool {

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int size) {
        this.tasks =  new SimpleBlockingQueue<>(size);
        init();
    }

    private void init() {
        int count = 0;
        while (count < Runtime.getRuntime().availableProcessors()) {
            threads.add(new Job());
            count++;
        }
    }

    public void start() {
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) {
         tasks.offer(job);
    }

    public void shutdown() {
       threads.forEach(Thread::interrupt);
    }

    private final class Job extends Thread {

        @Override
        public void run() {
           while (!Thread.currentThread().isInterrupted()) {
               try {
                   tasks.poll().run();
               } catch (InterruptedException e) {
                   e.printStackTrace();
                   Thread.currentThread().interrupt();
               }
           }
        }
    }
}
