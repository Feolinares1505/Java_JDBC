package com.laboratory.data;

import java.sql.ResultSet;

import java.sql.*;

public class UserConnection {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost/person?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";
    private static Driver driver = null;

    //Synchronized is a reserved word in Java, this mean "Only one thread can use this method at a time"
    public static synchronized Connection getConnection() throws SQLException {
        if (driver == null){
            try{
                Class JdbcDriverClass = Class.forName(JDBC_DRIVER);

                driver = (Driver) JdbcDriverClass.newInstance();
                DriverManager.registerDriver(driver);
            }catch (Exception e){
                System.out.println("Wrong loading Driver");
                e.printStackTrace();
            }
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    public static void close(ResultSet rs){
        try{
            if (rs != null){
                rs.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void close(PreparedStatement ps){
        try{
            if (ps != null){
                ps.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }public static void close(Connection c){
        try{
            if (c != null){
                c.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
