package ru.konovalov.service;

import ru.konovalov.model.Project;

import java.util.List;

public interface ServiceInterface<T> {

    List<T> getAll();

    void create(T t);

    void update(T t);

    T get(long id);

    void delete(long id);
}
