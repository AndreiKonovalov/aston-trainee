package ru.konovalov.service;

import java.util.List;

public interface MultitableServiceInterface<T, S, E>{

    List<S> getProject(E e);

    List<T> getEmployeeOfPosition(E e);

    List<T> getEmployeeOfProject(E e);
}
