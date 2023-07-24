package ru.konovalov.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.konovalov.dao.MultitableDao;
import ru.konovalov.model.Employee;
import ru.konovalov.model.Project;
import ru.konovalov.service.MultitableServiceInterface;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MultitableServiceInterfaceImpl
        implements MultitableServiceInterface<Employee, Project, String> {

    private final MultitableDao<Employee, Project, String> multitableDao;

    public List<Project> getProject(String lastName) {
        return multitableDao.getProjectOfEmployee(lastName);
    }

    @Override
    public List<Employee> getEmployeeOfPosition(String positionName) {
        return multitableDao.getEmployeeOfPosition(positionName);
    }

    @Override
    public List<Employee> getEmployeeOfProject(String projectName) {
        return multitableDao.getEmployeeOfProject(projectName);
    }
}
