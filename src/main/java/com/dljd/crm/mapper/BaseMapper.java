package com.dljd.crm.mapper;

import java.util.List;

public interface BaseMapper<T,V> {
    T get(V id);

    List<T> getAll();

    int add(T param);

    int delete(V[] ids);

    int update(T param);
}
