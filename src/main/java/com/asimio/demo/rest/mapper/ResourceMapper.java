package com.asimio.demo.rest.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@FunctionalInterface
public interface ResourceMapper<E, R> {

    R map(E entity);

    default List<R> map(Collection<E> entities) {
        List<R> result = new ArrayList<>();
        entities.stream().forEach(entity -> result.add(map(entity)));
        return result;
    }
}
