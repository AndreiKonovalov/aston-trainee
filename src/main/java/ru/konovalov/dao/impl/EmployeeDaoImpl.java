package ru.konovalov.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.konovalov.dao.DaoInterface;
import ru.konovalov.model.Employee;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeDaoImpl implements DaoInterface<Employee> {

    private final DataSource dataSource;

    @Override
    public List<Employee> getAll() {
        String query = "select id, first_name, last_name, salary, birthday, positions_id from employees";
        List<Employee> empList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                empList.add(newEmployee(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empList;
    }

    @Override
    public boolean create(Employee employee) {
        String query = "insert into employees (first_name, last_name, salary, birthday, positions_id) " +
                "values (?, ?, ?, ?, ?)";
        return saveEmployee(query, employee);
    }

    @Override
    public Employee get(long id) {
        Employee employee = new Employee();
        String query = "select * from employees where id = " + id;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                employee = newEmployee(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void delete(long id) {
        String query = "delete from employees where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean update(Employee employee) {
        String query = "update employees " +
                "set first_name = ?, last_name = ?, salary = ?, birthday = ?, positions_id = ? " +
                "where id = " + employee.getId();
        return saveEmployee(query,employee);
    }

    private Employee newEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getLong("id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setSalary(rs.getInt("salary"));
        employee.setBirthday(rs.getDate("birthday"));
        employee.setPositionId(rs.getLong("positions_id"));
        return employee;
    }

    private boolean saveEmployee(String query, Employee emp) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, emp.getFirstName());
            preparedStatement.setString(2, emp.getLastName());
            preparedStatement.setInt(3, emp.getSalary());
            preparedStatement.setDate(4, emp.getBirthday());
            preparedStatement.setLong(5, emp.getPositionId());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
