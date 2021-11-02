package com.dljd.crm.services;

import java.util.List;

public interface BaseService<T, V> {

    T get(V id);

    List<T> getAll();

    int add(T param);

    int delete(V[] ids);

    int update(T param);
}
