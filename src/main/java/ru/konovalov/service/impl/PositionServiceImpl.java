package ru.konovalov.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.konovalov.dao.DaoInterface;
import ru.konovalov.exception_handling.NoAddedElementException;
import ru.konovalov.exception_handling.NoSuchElementException;
import ru.konovalov.model.Position;
import ru.konovalov.service.ServiceInterface;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements ServiceInterface<Position> {

    private final DaoInterface<Position> positionDaoInterface;

    @Override
    public List<Position> getAll() {
        return positionDaoInterface.getAll();
    }

    @Override
    public void create(Position position) throws NoAddedElementException {
        if (!positionDaoInterface.create(position)) {
            throw new NoAddedElementException("New position not added in Database");
        }
    }

    @Override
    public void update(Position position) throws NoAddedElementException, NoSuchElementException {
        isPresentPosition(position.getId());
        if (!positionDaoInterface.update(position)) {
            throw new NoAddedElementException("Error update position in Database");
        }
    }

    @Override
    public Position get(long id) throws NoSuchElementException {
        isPresentPosition(id);
        return positionDaoInterface.get(id);
    }

    @Override
    public void delete(long id) throws NoSuchElementException {
        isPresentPosition(id);
        positionDaoInterface.delete(id);
    }

    private void isPresentPosition(long id) throws NoSuchElementException {
        Position pos = positionDaoInterface.get(id);
        if (pos == null) {
            throw new NoSuchElementException("There is no position with ID = "
                    + id + " in Database");
        }
    }
}
