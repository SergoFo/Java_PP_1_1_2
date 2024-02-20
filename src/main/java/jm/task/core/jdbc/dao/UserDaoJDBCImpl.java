package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    Connection connection = getConnection();

    public UserDaoJDBCImpl() {

    }

    /**
     * Создание таблицы пользователей
     */
    public void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                "Id int auto_increment primary key, " +
                "Name varchar(40) not null, " +
                "Lastname varchar(40) not null, " +
                "Age tinyint not null" +
                ")";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаление таблицы
     */
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Добавление пользователя
     */
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаление пользователя по ID
     */
    public void removeUserById(long id) {

        String sql = "DELETE FROM Users WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получение всех пользователей из БД
     */
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();

        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM Users";

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("Name"));
                user.setLastName(resultSet.getString("Lastname"));
                user.setAge(resultSet.getByte("Age"));

                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    /**
     * Очистка таблицы
     */
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE Users";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
