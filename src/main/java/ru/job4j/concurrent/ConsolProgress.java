package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 14.05.2021
 * в данном классе происходит создание потока прогресс он находится в состоянии Runnable
 * , главный поток засыпает на 5 сек начинает работать поток прогресс, каждую итерацию он засыпает
 * на 0.5 сек и выводит  данные в консоль по истечении 5 сек из главного потока выставляется прерыв
 * ание и попадаем ,в большинстве случаев, в поток прогресс, в состоянии сна,
 * вызывается исключение и флаг вновь равен false цикл начинается заново
 */

import java.util.List;

public class ConsolProgress implements Runnable {

    @Override
    public void run() {
        List<String> list = List.of("\\", "|", "/");
        int count = 0;
       while (!Thread.currentThread().isInterrupted()) {
           try {
               Thread.sleep(500);
               System.out.print("\r load: " + list.get(count++));
               if (count == 3) {
                   count = 0;
               }
           } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
           }
       }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsolProgress());
        progress.start();
        System.out.println(progress.getState());
        Thread.sleep(5000);
        progress.interrupt();
    }
}
