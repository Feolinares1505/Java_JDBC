package com.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase será la encargada de realizar las operaciones de tipo CRUD*/
public class ProductJDBC {
    //Utilizaremos el metodo PrepareStatement por lo que podemos hacer uso de los signos ? para sustituir los valores mas adelante
    private final String SQL_INSERT = "INSERT INTO product(name, price, quantity) VALUES(?, ?, ?)";
    private final String SQL_UPDATE = "UPDATE product SET name = ?, price = ?, quantity = ? WHERE id_product = ?";
    private final String SQL_DELETE = "DELETE FROM product WHERE id_product = ?";
    private final String SQL_SELECT = "SELECT * FROM product ORDER BY id_product";

    public int insert(String name, double price, int quantity){

        Connection connection = null;
        PreparedStatement statement = null;

        //Esta variable se usará para ver la cantidad de registros afectados
        int rows = 0;

        try{
            connection = (Connection) ConnectionUse.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);

            int index = 1; //Se usa para asignarle un indice a los parametros que vamos a reemplazar de la sentencia SQL
            statement.setString(index++, name); //Param => 1
            statement.setDouble(index++, price); //Param => 2
            statement.setInt(index, quantity); //Param => 3


            rows = statement.executeUpdate(); //Nos devolvera el numero de registros afectados

            System.out.println("Indices afectados: " + rows);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConnectionUse.close(statement);
            ConnectionUse.close(connection);
        }
        return rows;
    }

    public int update(int id_product, String name, double price, int quantity){
        Connection connection = null;
        PreparedStatement statement = null;

        int rows = 0;

        try{
            connection = (Connection) ConnectionUse.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);

            int index = 1;

            statement.setString(index++, name);
            statement.setDouble(index++, price);
            statement.setInt(index++, quantity);
            statement.setInt(index, id_product);

            rows = statement.executeUpdate();

            System.out.println("Cantidad de registros actualizados: " + rows);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConnectionUse.close(statement);
            ConnectionUse.close(connection);
        }

        return rows;
    }

    public int delete(int id_product){
        Connection connection = null;
        PreparedStatement statement = null;
        int rows = 0;

        try{
            connection = (Connection) ConnectionUse.getConnection();
            statement = connection.prepareStatement(SQL_DELETE);

            statement.setInt(1, id_product);

            rows = statement.executeUpdate();

            System.out.println("Registros eliminados: " + rows);

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConnectionUse.close(statement);
            ConnectionUse.close(connection);
        }

        return rows;
    }

    public List<Product> select(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Product product = null;

        List<Product> productList = new ArrayList<>();

        try{
            connection = (Connection) ConnectionUse.getConnection();
            statement = connection.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery(); //Este metodo nos va a retornar un resultSet con los registros de la base de datos

            //Ahora vamos a recorrer ese ResultSet
            while (resultSet.next()){

                int id_product = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Double price = resultSet.getDouble(3);
                int quantity = resultSet.getInt(4);

                product = new Product();

                product.setId_product(id_product);
                product.setName(name);
                product.setPrice(price);
                product.setQuantity(quantity);

                productList.add(product);

            }
            for (Product productRegister: productList){
                System.out.println(productRegister.getId_product());
                System.out.println(productRegister.getName());
                System.out.println(productRegister.getPrice());
                System.out.println(productRegister.getQuantity());
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            ConnectionUse.close(resultSet);
            ConnectionUse.close(statement);
            ConnectionUse.close(connection);
        }

        return productList;
    }
}
