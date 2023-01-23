package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimpleBlockingQueueTest {

    @Test
    void whenOffer5AndPoll5() throws InterruptedException {
        List<Integer> list = new LinkedList<>();
        SimpleBlockingQueue<Integer> queue
                = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            list.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list.size()).isEqualTo(5);
    }

    @Test
    void whenOffer5AndPoll3() throws InterruptedException {
        List<Integer> list = new LinkedList<>();
        SimpleBlockingQueue<Integer> queue
                = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    for (int i = 0; i < 3; i++) {
                        try {
                            list.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list.size()).isEqualTo(3);
    }

    @Test
    public void whenOffer6AndPoll6() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(6);
        Thread producer = new Thread(
                () -> {
                    for (int i = 0; i < 6; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        for (int i = 0; i < 6; i++) {
                            try {
                                buffer.add(queue.poll());
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).contains(0, 1, 2, 3, 4, 5);
    }
}