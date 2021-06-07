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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class RolColSum {

    public static class Sums {
        // Эти поля содержат суммы  по строке и по столбцу
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            Sums temp = new Sums();
            int one = rowSum(matrix, i);
            temp.setRowSum(one);
            int two = colSum(matrix, i);
            temp.setColSum(two);
            result[i] = temp;
        }
        return result;
    }

    private static int rowSum(int[][] array, int row) {
        Predicate<String> predicate = x -> x.equals("rowSum");
        return sumValue(array, predicate, row);
    }

    private static int colSum(int[][] array, int col) {
        Predicate<String> predicate = x -> x.equals("colSum");
        return sumValue(array, predicate, col);
    }

    private static int sumValue(int[][] array, Predicate<String> predicate, int value) {
        int result = 0;
        if (predicate.test("rowSum")) {
            for (int i = 0; i < array.length; i++) {
                result += array[value][i];
            }
        } else if (predicate.test("colSum")) {
            for (int i = 0; i < array.length; i++) {
                result += array[i][value];
            }
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] result = new Sums[matrix.length];
        List<CompletableFuture> list = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
           list.add(async(matrix, i));
        }

        for (int i = 0; i < list.size(); i++) {
            result[i] = (Sums) list.get(i).get();
        }
        return result;
    }

    private static CompletableFuture<Sums> async(int[][] array, int value) {
        return CompletableFuture.supplyAsync(new Supplier<Sums>() {
            @Override
            public Sums get() {
                    Sums temp = new Sums();
                    int one = rowSum(array, value);
                    temp.setRowSum(one);
                    int two = colSum(array, value);
                    temp.setColSum(two);
                return temp;
            }
        });
    }
}
