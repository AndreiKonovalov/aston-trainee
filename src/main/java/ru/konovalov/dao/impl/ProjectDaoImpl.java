package ru.konovalov.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.konovalov.dao.DaoInterface;
import ru.konovalov.model.Project;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectDaoImpl implements DaoInterface<Project> {

    private final DataSource dataSource;

    @Override
    public List<Project> getAll() {
        String query = "select * from projects";
        List<Project> projectList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                projectList.add(newProject(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectList;
    }

    @Override
    public boolean create(Project project) {
        String query = "insert into projects (project_name, cost) " +
                "values (?, ?)";
        return saveProject(query, project);
    }

    @Override
    public Project get(long id) {
        Project project = new Project();
        String query = "select * from projects where id = " + id;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                project = newProject(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public void delete(long id) {
        String query = "delete from projects where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean update(Project project) {
        String query = "update projects " +
                "set project_name = ?, cost = ? " +
                "where id = " + project.getId();
        return saveProject(query, project);
    }

    private Project newProject(ResultSet rs) throws SQLException {
        Project project = new Project();
        project.setId(rs.getLong("id"));
        project.setProjectName(rs.getString("project_name"));
        project.setCost(rs.getInt("cost"));
        return project;
    }

    private boolean saveProject(String query, Project project) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setInt(2, project.getCost());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
