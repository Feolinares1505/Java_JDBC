package com.transactions.management;

import com.transactions.data.UserConnection;
import com.transactions.data.UserJDBC;

import java.sql.Connection;
import java.sql.SQLException;

public class DDBBConnection {

    public static void main(String[] args) {

        UserJDBC user = new UserJDBC();

        Connection conn = null;

        try{
            conn = UserConnection.getConnection();
            //Esto es para que la transaccion no se guarde de manera automatica, con eso solo en caso de que todos los
            //query sean ejecutados correctamente se genere el commit y se guarde la información en la DDBB, y dado el
            //caso en que algo haya fallado, se pueda hacer un rollBack de la transacción
            if (conn.getAutoCommit()){
                conn.setAutoCommit(false);
            }
            UserJDBC userJDBC = new UserJDBC(conn);

            //Las transacciones agrupan varias sentencias SQL y si algo falla, no se deben hacer los cambios en la DDBB
            userJDBC.update("Miguel Feo", "1010", 1);
            userJDBC.insert("Miguel", "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");

            conn.commit();

        }catch (SQLException e){
            try{
                System.out.println("Use the rollback");
                e.printStackTrace(System.out);
                conn.rollback();
            }catch (SQLException e2){
                e.printStackTrace(System.out);
            }
        }
    }
     
}
