package ru.konovalov.dao;

import java.util.List;

public interface DaoInterface<T>{

    List<T> getAll();

    boolean create(T t);

    T get(long id);

    void delete(long id);

    boolean update(T t);
}
