package ru.konovalov.dao;

import java.util.List;

public interface MultitableDao<T, S, E> {

    List<S> getProjectOfEmployee(E e);

    List<T> getEmployeeOfProject(E e);

    List<T> getEmployeeOfPosition(E e);
}
