package com.laboratory.data;

import com.laboratory.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserJDBC {
    private final String SQL_INSERT = "INSERT INTO user(user, password) VALUES(?, ?)";
    private final String SQL_UPDATE = "UPDATE user SET user = ?, password = ? WHERE id_user = ?";
    private final String SQL_SELECT = "SELECT * FROM user ORDER BY id_user";
    private final String SQL_DELETE = "DELETE FROM user WHERE id_user = ?";

    public int insert(String user, String password){
        Connection connection = null;
        PreparedStatement statement = null;

        int rows = 0;

        try{
            connection = (Connection) UserConnection.getConnection();
            statement = (PreparedStatement) connection.prepareStatement(SQL_INSERT);

            int index = 1;
            statement.setString(index++, user);
            statement.setString(index, password);

            rows = statement.executeUpdate();

            System.out.println("Affected index: " + rows);

        }catch (SQLException e){
            System.out.println("Wrong starting connection");
            e.printStackTrace();
        }finally {
            UserConnection.close(statement);
            UserConnection.close(connection);
        }
        return rows;
    }

    public int update(String user, String password, int id_user){

        Connection connection = null;
        PreparedStatement statement = null;

        int rows = 0;

        try {
            connection = (Connection) UserConnection.getConnection();
            statement = (PreparedStatement) connection.prepareStatement(SQL_UPDATE);

            int index = 1;

            statement.setString(index++, user);
            statement.setString(index++, password);
            statement.setInt(index, id_user);

            rows = statement.executeUpdate();

            System.out.println("Rows updated: " + rows);

        }catch (SQLException e){
            System.out.println("Wrong updating user");
            e.printStackTrace();
        }finally {
            UserConnection.close(statement);
            UserConnection.close(connection);
        }
        return rows;
    }

    public int delete(int id_user){
        Connection connection = null;
        PreparedStatement statement = null;

        int rows = 0;

        try{
            connection = (Connection) UserConnection.getConnection();
            statement = (PreparedStatement) connection.prepareStatement(SQL_DELETE);

            statement.setInt(1, id_user);
            rows = statement.executeUpdate();

            System.out.println("Rows eliminated: " + rows);

        }catch (SQLException e){
            System.out.println("Wrong updating user");
            e.printStackTrace();
        }finally {
            UserConnection.close(statement);
            UserConnection.close(connection);
        }

        return rows;
    }

    public List<User> select(){

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User userObject = null;

        List<User> userList = new ArrayList<>();

        try{
            connection = UserConnection.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                int id_user = resultSet.getInt(1);
                String user = resultSet.getString(2);
                String password = resultSet.getString(3);

                userObject = new User();

                userObject.setId_user(id_user);
                userObject.setUser(user);
                userObject.setPassword(password);

                userList.add(userObject);
            }
        }catch (SQLException e){
            System.out.println("Wrong selecting data");
            e.printStackTrace();
        }finally {
            UserConnection.close(resultSet);
            UserConnection.close(statement);
            UserConnection.close(connection);
        }
        return userList;
    }
}
