package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 03.06.2021
 * Принцип работы - Создается объект пул , устанавливается размер очереди и заполняется threads
 * новыми потоками
 * .Каждый поток получает из очереди задание и вызывает метод ран у него.
 * метод инит создает определенное количество потоков в зависимости от кол.свободных ядер процессора
 * метод ворк добавляет в очередь задачи . метод старт запускает потоки . метод shutdown выставляет
 * прерывание потокам.
 * Тест проходит , но получаем исключения т.к очепедь становится пустой и потоки в ожидании ,
 * а я вызываю метод
 * shutdown соответсвенно получаю исключение
 */


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ThreadPoolTest {

    private final ThreadPool pool = new ThreadPool(12);
    private final PrintStream console = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream out = new PrintStream(outputStream);

    @Before
    public void setUp() {
        for (int i = 0; i < 10; i++) {
            pool.work((() -> System.out.println("Я работаю")));
        }
        System.setOut(out);
    }

    @Test
    public void executeTest() throws InterruptedException {
        pool.start();
        Thread.sleep(2000);
        pool.shutdown();
        assertThat(outputStream.toString(), is("Я работаю" + System.lineSeparator()
                + "Я работаю" + System.lineSeparator()
                + "Я работаю" + System.lineSeparator()
                + "Я работаю" + System.lineSeparator()
                + "Я работаю" + System.lineSeparator()
                + "Я работаю" + System.lineSeparator()
                + "Я работаю" + System.lineSeparator()
                + "Я работаю" + System.lineSeparator()
                + "Я работаю" + System.lineSeparator()
                + "Я работаю" + System.lineSeparator()));
    }

    @After
    public void setConsole() throws Exception {
        System.setOut(console);
    }
}