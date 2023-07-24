package ru.konovalov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.konovalov.model.Employee;
import ru.konovalov.model.Project;
import ru.konovalov.service.MultitableServiceInterface;
import ru.konovalov.service.ServiceInterface;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project")
public class ProjectController {

    private final ServiceInterface<Project> projectService;

    private final MultitableServiceInterface<Employee, Project, String> multitableService;

    @GetMapping("/show")
    public List<Project> showAllProject() {
        return projectService.getAll();
    }

    @GetMapping("/{id}")
    public Project getProject(@PathVariable long id) {
        return projectService.get(id);
    }

    @GetMapping("/employee")
    public List<Employee> getProjectOfEmployee(@RequestParam String projectName) {
        return multitableService.getEmployeeOfProject(projectName);
    }

    @PostMapping("/add")
    public Project createProject(@RequestBody Project project) {
        projectService.create(project);
        return project;
    }

    @PutMapping("/update")
    public Project updateProject(@RequestBody Project project) {
        projectService.update(project);
        return project;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProject(@PathVariable long id) {
        projectService.delete(id);
        return "Project with ID = " + id + " was deleted";
    }
}
