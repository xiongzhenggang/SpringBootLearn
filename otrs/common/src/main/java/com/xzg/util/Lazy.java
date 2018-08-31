package com.xzg.util;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <p>Title: ${file_name}</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2013</p>
 *
 * @author xiongzhenggang
 * @version 1.0
 * @date ${date}
 */
public class Lazy<T> {
    private transient Supplier<T> supplier;
    private volatile T value;

    private Lazy(){}
    private Lazy(Supplier<T> supplier) {
        this.supplier = Objects.requireNonNull(supplier);
    }

    public T get() {
        if (value == null) {
            synchronized (this) {
                if (value == null) {
                    value = Objects.requireNonNull(supplier.get());
                    supplier = null;
                }
            }
        }
        return value;
    }
    public <R> Lazy<R> map(Function<T, R> mapper) {
        return new Lazy<>(() -> mapper.apply(this.get()));
    }
    public <R> Lazy<R> flatMap(Function<T, Lazy<R>> mapper) {
        return new Lazy<>(() -> mapper.apply(this.get()).get());
    }
    public Lazy<Optional<T>> filter(Predicate<T> predicate) {
        return new Lazy<>(() -> Optional.of(get()).filter(predicate));
    }

    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }
}
