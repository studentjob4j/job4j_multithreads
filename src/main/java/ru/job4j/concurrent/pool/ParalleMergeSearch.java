package ru.job4j.concurrent.pool;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 05.06.2021
 * Параллельный поиск индекса в массиве  используя ForkJoin Pool принцип работы. Вызывая метод join
 * делим массив до тех пор пока не сработает условие поиска линейно и только тогда !!!  возвращаем
 * результат в переменные left and right и через тернарное условие возвращаем результат работы
 */

import java.util.concurrent.RecursiveTask;

public class ParalleMergeSearch<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T value;

    public ParalleMergeSearch(T[] array, int from, int to, T value) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        int result = -1;
        if (to - from <= 10) {
          for (int i = from; i < to; i++) {
              if (array[i].equals(value) || array[i] == value) {
                   result = i;
                   return result;
              }
          }
            return result;
        }
            int mid = (from + to) / 2;
            ParalleMergeSearch leftSearch = new ParalleMergeSearch(array, from, mid, value);
            ParalleMergeSearch rightSearch = new ParalleMergeSearch(array, mid + 1, to, value);
            leftSearch.fork();
            rightSearch.fork();
            int left = (int) leftSearch.join();
            int right = (int) rightSearch.join();
            return left != -1 ? left : right;
    }
}
