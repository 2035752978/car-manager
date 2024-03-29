package cn.manager.common.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * list处理迭代器
 *
 * @author ljc
 * @description ok
 * @version 1.0.0
 */
public class Iterators {
    public static <T> boolean addAll(Collection<T> collection, Iterator<? extends T> iterator) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(iterator);

        boolean wasModified;
        for(wasModified = false; iterator.hasNext(); wasModified |= collection.add(iterator.next())) {
        }

        return wasModified;
    }

    private Iterators() {
    }
}
