package com.transactions.data;

import com.laboratory.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserJDBC {

    private java.sql.Connection userConn;

    private final String INSERT = "INSERT INTO user(user, password) VALUES(?, ?)";
    private final String UPDATE = "UPDATE user SET user = ?, password = ? WHERE id_user = ?";
    private final String SELECT = "SELECT * FROM user ORDER BY id_user";
    private final String DELETE = "DELETE FROM user WHERE id_user = ?";

    public UserJDBC(){ }

    public UserJDBC(Connection userConn) {
        this.userConn = userConn;
    }

    public int insert(String user, String password) throws SQLException{
        Connection conn = null;
        PreparedStatement statement = null;

        int rows = 0;

        //Para el manejo de transacciones no vamos a hacer uso del bloque catch, asi podemos hacer el manejo de las excepciones
        //dentro del main que crearemos más adelante, por en el metodo definimos que podria arrojar una excepcion de tipo SQLException
        //y asi permitir que se propague hasta el metodo que usaremos para controlar las transacciones
        try{
            conn = (this.userConn != null) ? this.userConn : UserConnection.getConnection();
            statement = conn.prepareStatement(INSERT);
            int index = 1;

            statement.setString(index++, user);
            statement.setString(index, password);

            rows = statement.executeUpdate();

        }finally {
            UserConnection.close(statement);
            //Evitamos que se cierre la conexion hasta que su valor sea nulo para asi mantenerla viva de inicio a fin y poder manejar cosas como
            //el rollBack entre otras
            if (this.userConn == null){
                UserConnection.close(conn);
            }
        }


        return rows;
    }

    public int update(String user, String password, int id_user) throws SQLException{
        Connection conn = null;
        PreparedStatement statement = null;

        int rows = 0;

        try{
            conn = (this.userConn != null) ? this.userConn : UserConnection.getConnection();
            statement = conn.prepareStatement(UPDATE);

            int index = 1;
            statement.setString(index++, user);
            statement.setString(index++, password);
            statement.setInt(index, id_user);

            rows = statement.executeUpdate();

        }finally {
            com.laboratory.data.UserConnection.close(statement);
            if (this.userConn == null){
                com.laboratory.data.UserConnection.close(conn);
            }
        }
        return rows;
    }
    public int delete(int id_user) throws SQLException{
        Connection conn = null;
        PreparedStatement statement = null;

        int rows = 0;

        try{
            conn = (this.userConn != null) ? this.userConn : UserConnection.getConnection();
            statement = conn.prepareStatement(DELETE);
            statement.setInt(1, id_user);

            rows = statement.executeUpdate();
        }finally {
            UserConnection.close(statement);
            if (this.userConn == null){
                UserConnection.close(conn);
            }
        }
        return rows;
    }

    public List<User> select() throws SQLException{
        List<User> userList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User userObject = null;

        try{
            conn = (this.userConn != null) ? this.userConn : UserConnection.getConnection();
            statement = conn.prepareStatement(SELECT);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                int id_user = resultSet.getInt(1);
                String user = resultSet.getString(2);
                String password = resultSet.getString(3);

                userObject = new User();
                userObject.setId_user(id_user);
                userObject.setUser(user);
                userObject.setPassword(password);

                userList.add(userObject);
            }
        }finally {
            UserConnection.close(resultSet);
            UserConnection.close(statement);
            if (this.userConn == null){
                UserConnection.close(conn);
            }
        }
        return userList;
    }
}
