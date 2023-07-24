package ru.konovalov.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.konovalov.dao.DaoInterface;
import ru.konovalov.exception_handling.NoAddedElementException;
import ru.konovalov.exception_handling.NoSuchElementException;
import ru.konovalov.model.Employee;
import ru.konovalov.service.ServiceInterface;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements ServiceInterface<Employee> {

    private final DaoInterface<Employee> employeeDao;

    @Override
    public List<Employee> getAll() {
        return employeeDao.getAll();
    }

    @Override
    public void create(Employee employee) throws NoAddedElementException {
        if (!employeeDao.create(employee)) {
            throw new NoAddedElementException("New employee not added in Database");
        }
    }

    @Override
    public void update(Employee employee) throws NoAddedElementException, NoSuchElementException {
        isPresentEmployee(employee.getId());
        if (!employeeDao.update(employee)) {
            throw new NoAddedElementException("Error update employee in Database");
        }
    }

    @Override
    public Employee get(long id) throws NoSuchElementException {
        isPresentEmployee(id);
        return employeeDao.get(id);
    }

    @Override
    public void delete(long id) throws NoSuchElementException {
        isPresentEmployee(id);
        employeeDao.delete(id);
    }

    private void isPresentEmployee(long id) throws NoSuchElementException {
        Employee emp = employeeDao.get(id);
        if (emp == null) {
            throw new NoSuchElementException("There is no employee with ID = "
                    + id + " in Database");
        }
    }
}
