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
@RequestMapping("/employee")
public class EmployeeController {

    private final ServiceInterface<Employee> employeeService;
    private final MultitableServiceInterface<Employee, Project, String> multitableService;

    @GetMapping("/show")
    public List<Employee> showAllEmployees() {
        return employeeService.getAll();
    }

     @GetMapping("/project")
     public List<Project> getProjectOfEmployee(@RequestParam String lastName) {
         return multitableService.getProject(lastName);
     }

    @GetMapping("/department")
    public List<Project> getProjectOfEmployee(@RequestParam String lastName) {
        return multitableService.getProject(lastName);
    }

     @GetMapping("/{id}")
     public Employee getEmployee(@PathVariable long id) {
         return employeeService.get(id);
     }

     @PostMapping("/add")
     public Employee createEmployee(@RequestBody Employee employee) {
         employeeService.create(employee);
         return employee;
     }

    @PutMapping("/update")
    public Employee updateEmployee(@RequestBody Employee employee) {
        employeeService.update(employee);
        return employee;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable long id) {
        employeeService.delete(id);
        return "Employee with ID = " + id + " was deleted";
    }


}
