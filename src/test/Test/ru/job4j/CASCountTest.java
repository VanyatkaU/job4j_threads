package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    public void when3incrementThen3Get() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread = new Thread(() -> {
               casCount.increment();
               casCount.increment();
        });
        Thread thread1 = new Thread(() -> {
            casCount.increment();
        });
        thread.start();
        thread1.start();
        Thread.sleep(3000);
        assertThat(casCount.get()).isEqualTo(3);
    }

    @Test
    public void when1incrementThen1Get() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread = new Thread(() -> { });
        Thread thread1 = new Thread(() -> {
            casCount.increment();
        });
        thread.start();
        thread1.start();
        Thread.sleep(3000);
        assertThat(casCount.get()).isEqualTo(1);
    }

    @Test
    public void when0incrementThen0Get() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread = new Thread(() -> { });
        Thread thread1 = new Thread(() -> { });
        thread.start();
        thread1.start();
        Thread.sleep(3000);
        assertThat(casCount.get()).isEqualTo(0);
    }

    @Test
    public void when10incrementThen10Get() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                casCount.increment();
            }
        });
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                casCount.increment();
            }
        });
        thread.start();
        thread1.start();
        Thread.sleep(3000);
        assertThat(casCount.get()).isEqualTo(10);
    }

}
