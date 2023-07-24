package ru.konovalov.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.konovalov.dao.DaoInterface;
import ru.konovalov.exception_handling.NoAddedElementException;
import ru.konovalov.exception_handling.NoSuchElementException;
import ru.konovalov.model.Project;
import ru.konovalov.service.ServiceInterface;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ServiceInterface<Project> {

    private final DaoInterface<Project> projectDaoInterface;

    @Override
    public List<Project> getAll() {
        return projectDaoInterface.getAll();
    }

    @Override
    public void create(Project project) throws NoAddedElementException {
        if (!projectDaoInterface.create(project)) {
            throw new NoAddedElementException("New project not added in Database");
        }
    }

    @Override
    public void update(Project project) throws NoAddedElementException, NoSuchElementException {
        isPresentProject(project.getId());
        if (!projectDaoInterface.update(project)) {
            throw new NoAddedElementException("Error update project in Database");
        }
    }

    @Override
    public Project get(long id) throws NoSuchElementException {
        isPresentProject(id);
        return projectDaoInterface.get(id);
    }

    @Override
    public void delete(long id) throws NoSuchElementException {
        isPresentProject(id);
        projectDaoInterface.delete(id);
    }

    private void isPresentProject(long id) throws NoSuchElementException {
        Project project = projectDaoInterface.get(id);
        if (project == null) {
            throw new NoSuchElementException("There is no project with ID = "
                    + id + " in Database");
        }
    }
}
