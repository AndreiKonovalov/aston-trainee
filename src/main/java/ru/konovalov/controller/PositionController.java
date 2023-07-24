package ru.konovalov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.konovalov.model.Employee;
import ru.konovalov.model.Position;
import ru.konovalov.model.Project;
import ru.konovalov.service.MultitableServiceInterface;
import ru.konovalov.service.ServiceInterface;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/position")
public class PositionController {

    private final ServiceInterface<Position> positionService;

    private final MultitableServiceInterface<Employee, Project, String> multitableService;

    @GetMapping("/show")
    public List<Position> showAllPosition() {
        return positionService.getAll();
    }

    @GetMapping("/{id}")
    public Position getPosition(@PathVariable long id) {
        return positionService.get(id);
    }

    @GetMapping("/employee")
    public List<Employee> getEmployeeOfPosition(@RequestParam String positionName){
        return multitableService.getEmployeeOfPosition(positionName);
    }

    @PostMapping("/add")
    public Position createPosition(@RequestBody Position position) {
        positionService.create(position);
        return position;
    }

    @PutMapping("/update")
    public Position updatePosition(@RequestBody Position position) {
        positionService.update(position);
        return position;
    }

    @DeleteMapping("/delete/{id}")
    public String deletePosition(@PathVariable long id) {
        positionService.delete(id);
        return "Position with ID = " + id + " was deleted";
    }
}
