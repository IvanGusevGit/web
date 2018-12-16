package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.Item;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.AttributePair;
import ru.itmo.webmail.model.repository.Repository;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RepositoryImpl<T extends Item> implements Repository<T> {

    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();
    private Class<T> tClass;
    private String[] attributes;
    private String tName;


    public RepositoryImpl(Class<T> tClass) {
        this.tClass = tClass;
        String[] piecies = tClass.getName().split("\\.");
        tName = piecies[piecies.length - 1];
        Object[] methods = Arrays.stream(tClass.getMethods()).filter(method -> !method.getName().equals("getClass") && (method.getName().startsWith("get") && !method.getName().endsWith("String"))).toArray();
        attributes = new String[methods.length];
        for (int i = 0; i < attributes.length; i++) {
            attributes[i] = ((Method)methods[i]).getName();
            attributes[i] = Character.toLowerCase(attributes[i].charAt(3)) + attributes[i].substring(4);
            if (attributes[i].endsWith("String")) {
                attributes[i] = attributes[i].substring(0, attributes[i].length() - 6);
            }
        }
    }


    @Override
    public void save(T item, AttributePair<?> ... attributePairs) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(generateSaveRequest(attributePairs), Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0, j = 0; i < attributes.length; i++) {
                    if (!"id".equals(attributes[i]) && !"creationTime".equals(attributes[i])) {
                        try {
                            Method getMethod = tClass.getMethod(makeGet(attributes[i]));
                            getMethod.setAccessible(true);
                            statement.setString(j + 1, getMethod.invoke(item).toString());
                            j++;
                        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                            //ignore it. It does not occur.
                        }
                    }
                }
                for (int j = 0, start = attributes.length - 2; j + start < attributes.length - 2 + attributePairs.length; j++) {
                    statement.setString(start + j + 1 , attributePairs[j].getValue().toString());
                }
                if (statement.executeUpdate() == 1) {
                    ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                    if (generatedIdResultSet.next()) {
                        item.setId(generatedIdResultSet.getLong(1));
                        item.setCreationTime(findCreationTime(item.getId()));
                    } else {
                        throw new RepositoryException("Can't find id of saved Item.");
                    }
                } else {
                    throw new RepositoryException("Can't save Item.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save Item.", e);
        }
    }

    @Override
    public void update(long id, AttributePair<?> value) {
        T item = findByAttributes(new AttributePair<>("id", id));
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE " + tName + " SET "+value.getAttribute() + "=" + value.getValue().toString() + " where id=?",
                    Statement.RETURN_GENERATED_KEYS)) {
                statement.setLong(1, item.getId());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't update Item.", e);
        }
    }

    @Override
    public List<T> findAll() {
        List<T> items = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM "+ tName + " ORDER BY id")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        items.add(toT(statement.getMetaData(), resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find all items.", e);
        }
        return items;
    }

    @Override
    public T findByAttributes(AttributePair<?>... values) {
        return findWithAttributes("AND", values);
    }

    @Override
    public T findOccur(AttributePair<?>... values) {
        return findWithAttributes("OR", values);
    }


    private T findWithAttributes(String delimiter, AttributePair<?>[] values) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(generateSearchRequest(values, delimiter))) {
                for (int i = 0; i < values.length; i++) {
                    statement.setString(i + 1, values[i].getValue().toString());
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toT(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find item by id attributes", e);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private T toT(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        T item = null;
        try {
            item = tClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            //ignore. Does not occur.
        }
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if (columnName.equalsIgnoreCase("creationTime")) {
                item.setCreationTime(resultSet.getTimestamp(i));
            } else {
                for (String attribute : attributes) {
                    if (attribute.equalsIgnoreCase(columnName)) {
                        try {
                            Method method = tClass.getMethod(makeSet(attribute), String.class);
                            method.setAccessible(true);
                            method.invoke(item, resultSet.getString(i));
                            break;
                        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                            //ignore. Does not occur.
                        }
                    }
                }
            }
        }
        return item;
    }

    private Date findCreationTime(long id) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT creationTime FROM " + tName + " WHERE id=?")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getTimestamp(1);
                    }
                }
                throw new RepositoryException("Can't find User.creationTime by id.");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.creationTime by id.", e);
        }
    }


    private String generateSaveRequest(AttributePair<?> ... attributePairs) {
        StringBuilder request = new StringBuilder("INSERT INTO ").append(tName).append(" (");
        boolean isFirstAttribute = true;
        for (String attribute : attributes) {
            if (!"creationTime".equals(attribute) && (!"id".equals(attribute))) {
                if (isFirstAttribute) {
                    request.append(attribute);
                    isFirstAttribute = false;
                } else {
                    request.append(", ").append(attribute);
                }
            }
        }
        for (AttributePair<?> attributePair : attributePairs) {
            if (isFirstAttribute) {
                request.append(attributePair.getAttribute());
                isFirstAttribute = false;
            } else {
                request.append(", ").append(attributePair.getAttribute());
            }
        }
        if (isFirstAttribute) {
            request.append("creationTime");
        } else {
            request.append(", ").append("creationTime");
        }
        request.append(") VALUES (");
        for (int i = 0; i < attributes.length + attributePairs.length - 2; i++) {
            if (i == 0) {
                request.append("?");
            } else {
                request.append(", ?");
            }
        }
        if (attributes.length + attributePairs.length- 2 > 0) {
            request.append(", NOW()");
        } else {
            request.append("NOW()");
        }
        request.append(")");
        return request.toString();
    }

    private String generateSearchRequest(AttributePair<?>[] values, String delimiter) {
        StringBuilder request = new StringBuilder("SELECT * FROM ").append(tName).append(" WHERE ");
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                request.append(values[i].getAttribute()).append("=?");
            } else {
                request.append(" ").append(delimiter).append(" ").append(values[i].getAttribute()).append("=?");
            }
        }
        return request.toString();
    }


    private String makeGet(String value) {
        return "get" + Character.toUpperCase(value.charAt(0)) + value.substring(1) + "String";
    }

    private String makeSet(String value) {
        return "set" + Character.toUpperCase(value.charAt(0)) + value.substring(1) + "String";
    }
}
