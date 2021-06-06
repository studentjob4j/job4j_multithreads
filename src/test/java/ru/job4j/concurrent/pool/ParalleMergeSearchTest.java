package ru.job4j.concurrent.pool;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 05.06.2021
 * Параллельный поиск индекса в массиве  используя ForkJoin Pool принцип работы. Вызывая метод join
 * делим массив до тех пор пока не сработает условие поиска линейно и только тогда !!!  возвращаем
 * результат
 * в переменные left and right и через Math.max return value
 */

import org.junit.Test;
import java.util.concurrent.ForkJoinPool;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ParalleMergeSearchTest {

    @Test
    public void whenSearchIndexInSmallArray() {
        ForkJoinPool pool = new ForkJoinPool();
        int result = (int) pool.invoke(new ParalleMergeSearch(new Object[]{
                5, 7, 2, 6}, 0, 3, 6));
        assertThat(result, is(3));
    }

    @Test
    public void whenSearchNotExistIndexInSmallArray() {
        ForkJoinPool pool = new ForkJoinPool();
        int result = (int) pool.invoke(new ParalleMergeSearch(new Object[]{
                5, 7, 2, 6}, 0, 3, 3));
        assertThat(result, is(-1));

    }

    @Test
    public void whenParalleleSearchIndex() {
        ForkJoinPool pool = new ForkJoinPool();
        int result = (int) pool.invoke(new ParalleMergeSearch(new Object[]{
                5, 7, 2, 6, 12, 9, 0, 45, 23, 55, 666, 222, 45}, 0, 12, 666));
        assertThat(result, is(10));
    }

    @Test
    public void whenParalleleSearchIndexNotFound() {
        ForkJoinPool pool = new ForkJoinPool();
        int result = (int) pool.invoke(new ParalleMergeSearch(new Object[]{
                5, 7, 2, 6, 12, 9, 0, 45, 23, 55, 666, 222, 45}, 0, 12, 8));
        assertThat(result, is(-1));
    }
}