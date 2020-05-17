package com.jdbc;

import java.sql.*;

public class ConnectionUse {
    //Ruta de la clase que usaremos para realiza la conexion a MySQL
    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String JDBC_URL = "jdbc:mysql://localhost/products?useSSL=false&serverTimezone=UTC";
    private static String JDBC_USER = "root";
    private static String JDBC_PASS = "";
    private static Driver driver = null;

    //La palabra reservada synchronized se usa para que un solo hilo pueda ejecutar este metodo por vez
    public static synchronized Connection getConnection() throws SQLException{
        if (driver == null){
            //En este bloque lo que haremos es cargar en memoria el objeto de tipo Driver que ejecutara nuestras consultas
            try{
                Class jdbcDriverClass = Class.forName(JDBC_DRIVER);
                //Nos creara un objeto de tipo Driver
                driver = (Driver) jdbcDriverClass.newInstance();
                //Luego la registramos
                DriverManager.registerDriver(driver);
            }catch (Exception e){
                System.out.println("Falla al cargar el Driver");
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
    }

    public static void close(Connection c){
        try {
            if (c != null){
                c.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
