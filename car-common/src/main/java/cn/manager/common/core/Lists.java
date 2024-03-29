package cn.manager.common.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * 快捷创建list
 *
 * @author ljc
 * @version 1.0.0
 * @description ok
 */
public class Lists {
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> iterator) {
        ArrayList<E> list = newArrayList();
        Iterators.addAll(list, iterator);
        return list;
    }

    @SuppressWarnings("unchecked")
    public static <E> ArrayList<E> newArrayList(Long[] args) {

        return new ArrayList<>((Collection<? extends E>) Arrays.asList(args));
    }

    private Lists() {
    }
}