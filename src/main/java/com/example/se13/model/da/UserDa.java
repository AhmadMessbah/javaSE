package com.example.se13.model.da;

import com.example.se13.controller.exception.DisabledUserException;
import com.example.se13.model.entity.Role;
import com.example.se13.model.entity.User;
import com.example.se13.model.utils.JdbcProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDa implements AutoCloseable {
    private Connection connection;
    private PreparedStatement statement;

    public UserDa() throws SQLException {
        connection = JdbcProvider.getJdbc().getConnection();
    }

    public User save(User user) throws SQLException {
        statement = connection.prepareStatement(
                "SELECT USER_SEQ.NEXTVAL AS NEXT FROM DUAL"
        );
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        user.setId(resultSet.getInt("NEXT"));

        statement = connection.prepareStatement(
                "INSERT INTO USER_TBL VALUES (?,?,?,?,?,?)"
        );
        statement.setInt(1, user.getId());
        statement.setString(2, user.getUserName());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getRole().name());
        statement.setBoolean(5, user.getActive());
//        statement.setBoolean(5, true);
        statement.setBoolean(6, false);
        statement.execute();
        return user;
    }

    public User edit(User user) throws SQLException {
        String sqlCommand = "UPDATE USER_TBL SET ";

        List<String> params = new ArrayList<>();

        if (!user.getPassword().isEmpty()) {
            params.add("PASSWORD=?");
        }

        if (user.getRole() != null) {
            params.add("ROLE=?");
        }

        if (user.getActive() != null) {
            params.add("ACTIVE=?");
        }

        sqlCommand = sqlCommand + String.join(", ", params) + " WHERE ID=?";
        System.out.println(sqlCommand);

        // todo : set statement.set  values
//        statement = connection.prepareStatement(sqlCommand);
//        statement.setString(1, user.getPassword());
//        statement.setString(2, user.getRole().name());
//        statement.setBoolean(3, user.isActive());
//        statement.setInt(4, user.getId());
//        statement.execute();
        return user;
    }

    public int remove(int id) throws SQLException {
        statement = connection.prepareStatement(
//              Physical delete
//              "DELETE FROM USER_TBL WHERE ID=?"

//              Logical delete
                "UPDATE USER_TBL SET DELETE=1 WHERE ID=?"
        );
        statement.setInt(1, id);

        statement.execute();
        return id;
    }

    public List<User> findAll() throws SQLException {
        statement = connection.prepareStatement(
                "SELECT * FROM USER_TBL WHERE DELETED=0"
        );

        List<User> userList = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            User user = User
                    .builder()
                    .id(resultSet.getInt("id"))
                    .userName(resultSet.getString("user_name"))
//                  .password(resultSet.getString("password"))
                    .role(Role.valueOf(resultSet.getString("role")))
                    .active(resultSet.getBoolean("active"))
                    .build();
            userList.add(user);
        }

        statement.execute();
        return (userList.isEmpty()) ? null : userList;
    }

    public User findById(int id) throws SQLException {
        statement = connection.prepareStatement(
                "SELECT * FROM USER_TBL WHERE ID=?"
        );
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        User user = null;
        if (resultSet.next()) {
            user = User
                    .builder()
                    .id(resultSet.getInt("id"))
                    .userName(resultSet.getString("user_name"))
//                  .password(resultSet.getString("password"))
                    .role(Role.valueOf(resultSet.getString("role")))
                    .active(resultSet.getBoolean("active"))
                    .build();
        }

        statement.execute();
        return user;
    }

    public User findByUserName(String userName) throws SQLException {
        statement = connection.prepareStatement(
                "SELECT * FROM USER_TBL WHERE USER_NAME=?"
        );
        statement.setString(1, userName);

        ResultSet resultSet = statement.executeQuery();

        User user = null;
        if (resultSet.next()) {
            user = User
                    .builder()
                    .id(resultSet.getInt("id"))
                    .userName(resultSet.getString("user_name"))
//                  .password(resultSet.getString("password"))
                    .role(Role.valueOf(resultSet.getString("role")))
                    .active(resultSet.getBoolean("active"))
                    .build();
        }

        statement.execute();
        return user;
    }

    public User findByUserNameAndPassword(String userName, String password) throws Exception {
        statement = connection.prepareStatement(
                "SELECT * FROM USER_TBL WHERE USER_NAME=? AND PASSWORD=?"
        );
        statement.setString(1, userName);
        statement.setString(2, password);

        ResultSet resultSet = statement.executeQuery();

        User user = null;
        if (resultSet.next()) {
            user = User
                    .builder()
                    .id(resultSet.getInt("id"))
                    .userName(resultSet.getString("user_name"))
//                  .password(resultSet.getString("password"))
                    .role(Role.valueOf(resultSet.getString("role")))
                    .active(resultSet.getBoolean("active"))
                    .build();
        }
        statement.execute();
        if (user !=null && !user.getActive()) {
            throw new DisabledUserException();
        }
        return user;
    }

    @Override
    public void close() throws Exception {
        statement.close();
        connection.close();
    }
}
