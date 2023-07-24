package ru.konovalov.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.konovalov.dao.DaoInterface;
import ru.konovalov.model.Position;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PositionDaoImpl implements DaoInterface<Position> {

    private final DataSource dataSource;

    @Override
    public List<Position> getAll() {
        String query = "select id, position_name from positions";
        List<Position> posList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                posList.add(newPosition(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posList;
    }

    @Override
    public boolean create(Position position) {
        String query = "insert into positions (position_name) " +
                "values (?)";
        return savePosition(query, position);
    }

    @Override
    public Position get(long id) {
        Position position = new Position();
        String query = "select * from positions where id = " + id;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                position = newPosition(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return position;
    }

    @Override
    public void delete(long id) {
        String query = "delete from positions where id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean update(Position position) {
        String query = "update positions " +
                "set position_name = ? " +
                "where id = " + position.getId();
        return savePosition(query,position);
    }

    private Position newPosition(ResultSet rs) throws SQLException {
        Position position = new Position();
        position.setId(rs.getLong("id"));
        position.setPositionName(rs.getString("position_name"));
        return position;
    }

    private boolean savePosition(String query, Position pos) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pos.getPositionName());
            if (preparedStatement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
