package models;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {
        Connection conectar = null;

        String usuario = "SA";
        String contrasena = "<Strong@Pass1>";
        String bd = "dbcolegio";
        String ip = "localhost";
        String puerto = "1433";

        String cadena = "jdbc:sqlserver://"+ip+":"+puerto+"/"+bd;

        public Connection establecerConexion(){
            try {
                String cadena = "jdbc:sqlserver://localhost:"+puerto+";"+"databaseName="+bd+";encrypt=false;";
                conectar=DriverManager.getConnection(cadena, usuario, contrasena);
//                System.out.println("CONTECTADO");
            } catch (Exception e) {
                System.out.println("Error al conectar a la base de datos, error: "+e.toString());
            }
            return conectar;
        }
    }