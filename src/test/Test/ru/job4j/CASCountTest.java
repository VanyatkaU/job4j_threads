package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CASCountTest {

    @Test
    public void when3incrementThen3Get() {
        CASCount casCount = new CASCount();
        casCount.increment();
        casCount.increment();
        casCount.increment();
        assertThat(casCount.get()).isEqualTo(3);
    }

    @Test
    public void when1incrementThen1Get() {
        CASCount casCount = new CASCount();
        casCount.increment();
        assertThat(casCount.get()).isEqualTo(1);
    }

    @Test
    public void when0incrementThen0Get() {
        CASCount casCount = new CASCount();
        assertThat(casCount.get()).isEqualTo(0);
    }

    @Test
    public void when10incrementThen10Get() {
        CASCount casCount = new CASCount();
        for (int i = 0; i < 10; i++) {
            casCount.increment();
        }
        assertThat(casCount.get()).isEqualTo(10);
    }

}
