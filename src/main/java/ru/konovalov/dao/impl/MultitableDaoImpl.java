package ru.konovalov.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.konovalov.dao.MultitableDao;
import ru.konovalov.model.Employee;
import ru.konovalov.model.Project;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MultitableDaoImpl implements MultitableDao<Employee, Project, String> {

    private final DataSource dataSource;

    @Override
    public List<Project> getProjectOfEmployee(String lastName) {
        List<Project> projectList = new ArrayList<>();
        String query = "select p.project_name " +
                "from employees AS e " +
                "join employee_project AS ep on e.id = ep.employees_id " +
                "join projects AS p on p.id = ep.projects_id " +
                "where e.last_name = ? " +
                "order by p.project_name";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = createPreparedStatement(connection, query, lastName);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                projectList.add(newProjectOfEmployee(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectList;
    }

    @Override
    public List<Employee> getEmployeeOfProject(String projectName) {
        List<Employee> employeeList = new ArrayList<>();
        String query = "select e.first_name, e.last_name " +
                "from employees AS e " +
                "join employee_project AS ep on e.id = ep.employees_id " +
                "join projects AS p on p.id = ep.projects_id " +
                "where p.project_name = ? " +
                "order by e.first_name";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = createPreparedStatement(connection, query, projectName);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                employeeList.add(createEmployee(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    @Override
    public List<Employee> getEmployeeOfPosition(String positionName) {
        List<Employee> employeeList = new ArrayList<>();
        String query = "select e.first_name, e.last_name " +
                "from employees AS e " +
                "join positions AS p on p.id = e.positions_id " +
                "where p.position_name = ? " +
                "order by e.first_name";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = createPreparedStatement(connection, query, positionName);
             ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                employeeList.add(createEmployee(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    private PreparedStatement createPreparedStatement(
            Connection con,
            String query,
            String param) throws SQLException {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, param);
        return ps;
    }

    private Employee createEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        return employee;
    }

    private Project newProjectOfEmployee(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setProjectName(rs.getString("project_name"));
        return project;
    }
}
