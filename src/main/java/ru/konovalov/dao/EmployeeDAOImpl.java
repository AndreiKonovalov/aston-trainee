package ru.konovalov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
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
public class EmployeeDAOImpl implements EmployeeDAO {

    private final DataSource dataSource;

    private Employee employee;

    @Override
    public List<Employee> getAllEmployees() {
        String query = "select id, first_name, last_name, salary, birthday, positions_id from employees";
        List<Employee> empList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                createEmployee(resultSet);
                empList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empList;
    }

    @Override
    public void saveEmployee(Employee employee) {
        String query = "insert into employees (first_name, last_name, salary, birthday, positions_id) " +
                "values (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getSalary());
            preparedStatement.setDate(4, employee.getBirthday());
            preparedStatement.setLong(5, employee.getPositionId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee getEmployee(int id) {

        String query = "select * from employees where id = " + id;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                createEmployee(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void deleteEmployee(int id) {

    }

    private void createEmployee(ResultSet rs) throws SQLException {
        employee =  new Employee();
        employee.setId(rs.getLong("id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setSalary(rs.getInt("salary"));
        employee.setBirthday(rs.getDate("birthday"));
        employee.setPositionId(rs.getLong("positions_id"));
    }
}
