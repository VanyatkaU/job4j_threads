package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<Integer> {

    private final int to;
    private final int from;
    private final T[] array;
    private final T element;

    public ParallelSearchIndex(T[] array, int from, int to, T element) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.element = element;
    }

    @Override
    protected Integer compute() {
        if ((to - from) <= 10) {
            return searchLine();
        }
        int  mid = (from + to) / 2;
        ParallelSearchIndex<T> leftSearch = new ParallelSearchIndex<>(
                array, from, mid, element);
        ParallelSearchIndex<T> rightSearch = new ParallelSearchIndex<>(
                array, mid + 1, to, element);
        leftSearch.fork();
        rightSearch.fork();
        return Math.max(leftSearch.join(), rightSearch.join());
    }

    private int searchLine() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(element)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static <T> Integer recursiveFind(T[] array, T lineIndex) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearchIndex<>(
                array, 0, array.length - 1, lineIndex));
    }
}

