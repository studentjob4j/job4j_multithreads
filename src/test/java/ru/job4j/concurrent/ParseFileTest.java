package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 21.05.2021
 */

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ParseFileTest {

    @Test
    public void whenGetContentWithoutUnicode() {
        File file = new File("temp.xml");
        ParseFile parseFile = new ParseFile(file);
        String res = parseFile.getContentWithoutUnicode();
        assertThat(res.length(), is(2998));
    }

    @Test
    public void whenGetContent() {
        File file = new File("temp.xml");
        ParseFile parseFile = new ParseFile(file);
        String res = parseFile.getContent();
        assertThat(res.length(), is(2998));
    }
}