package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 21.05.2021
 * данный класс теперь потокобезопастный т.к изменил поля класса на final и объект не
 * изменяется после создания , обернул потоки io в try catch с ресурсами , добавил логгер,
 * разделил логику работы на 2 класса и убрал дублирование кода используя паттерн стратегия
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

public class ParseFile {

    private static final Logger LOG = LoggerFactory.getLogger(ParseFile.class.getName());
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    private String content(Predicate<Character> filter) {
        StringBuilder builder = new StringBuilder();
        try (InputStream i = new FileInputStream(file)) {
            builder.append("");
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test((char) data)) {
                    builder.append((char) data);
                }
            }
        } catch (IOException e) {
          LOG.error(e.getMessage(), e);
        }
        return builder.toString();
    }

    public String getContent() {
       return content(x -> true);
    }

    public String getContentWithoutUnicode() {
        return content(x -> x < 0x80);
    }
}
