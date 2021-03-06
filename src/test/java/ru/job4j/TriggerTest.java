package ru.job4j;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TriggerTest {

    @Test
    public void whenCreateTrigger() {
        Trigger trigger = new Trigger();
        int res = trigger.sum(5, 5);
        assertThat(res, is(10));
    }

    @Test
    public void test() {
        Trigger trigger = new Trigger();
        assertThat(trigger.result(), is(0));
    }
}