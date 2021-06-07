package ru.job4j.concurrent.completablefuture;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 07.06.2021
 * этот класс считает суммы чисел в квадратной матрице по строкам и столбцам . Класс Sums
 * содержит поля с суммами по строке и столбцу.Метод sum считает сумму чисел по строкам и столбцам
 * используя предикат для определения - как считать ? по строкам или по столбцам .Результат
 * помещаем в массив -последовательное исполнение программы
 * Метод asyncSum реализует тот же функционал но использую CompletableFuture.supplyAsync исп
 * асинхронные потоки для подсчета.
 */

import org.junit.Test;
import java.util.concurrent.ExecutionException;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    private final int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

    @Test
    public void whenUseOneThreadMethodSum() {
       RolColSum.Sums[] result =  RolColSum.sum(matrix);
       assertThat(result[0].getRowSum(), is(6));
       assertThat(result[0].getColSum(), is(12));
       assertThat(result[1].getRowSum(), is(15));
       assertThat(result[1].getColSum(), is(15));
       assertThat(result[2].getRowSum(), is(24));
       assertThat(result[2].getColSum(), is(18));
    }

    @Test
    public void whenUseAsyncMethodAsyncSum() throws ExecutionException, InterruptedException {
        RolColSum.Sums[] result =  RolColSum.asyncSum(matrix);
        assertThat(result[0].getRowSum(), is(6));
        assertThat(result[0].getColSum(), is(12));
        assertThat(result[1].getRowSum(), is(15));
        assertThat(result[1].getColSum(), is(15));
        assertThat(result[2].getRowSum(), is(24));
        assertThat(result[2].getColSum(), is(18));
    }

}