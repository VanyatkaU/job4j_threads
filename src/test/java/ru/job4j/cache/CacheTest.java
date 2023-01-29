package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    void whenAddValues() {
        Cache cache = new Cache();
        boolean result = cache.add(new Base(1, 0));
        boolean result1 = cache.add(new Base(2, 1));
        assertThat(result).isTrue();
        assertThat(result1).isTrue();
    }

    @Test
    void whenDoesNotAdd() {
        Cache cache = new Cache();
        cache.add(new Base(1, 0));
        boolean result = cache.add(new Base(1, 1));
        assertThat(result).isFalse();
    }

    @Test
    void whenAddValueAndUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 0);
        base.setName("Ivan");
        cache.add(base);
        Base updateBase = new Base(1, 0);
        base.setName("Alex");
        boolean result = cache.update(updateBase);
        assertThat(result).isTrue();
    }

    @Test
    void whenDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base updateBase = new Base(2, 1);
        cache.add(base);
        cache.add(updateBase);
        cache.delete(base);
        assertThat(cache.get(1)).isNull();
        assertThat(cache.get(2)).isEqualTo(new Base(2, 1));
    }

    @Test
    void whenThrowException() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        cache.update(base);
        assertThatThrownBy(() -> cache.update(base))
                .isInstanceOf(OptimisticException.class)
                .hasMessageContaining("Versions are not equal");
    }
}