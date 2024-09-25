package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {
    
       // Variables para la conexión
    Connection conectar = null;

    String usuario = "sa";
    String contrasena = "123456789";  // Asegúrate de que esta contraseña sea correcta
    String bd = "dbcolegio";
    String ip = "localhost";  // Usa "localhost" o la IP correcta si es remota
    String puerto = "1433";  // Verifica que este puerto sea el correcto

    // Define la cadena de conexión
    String cadena = "jdbc:sqlserver://" + ip + ":" + puerto + ";databaseName=" + bd + ";encrypt=false;";

    // Método para establecer la conexión
    public Connection establecerConexion() {
        try {
            // Intenta conectarte con la cadena definida y las credenciales
            conectar = DriverManager.getConnection(cadena, usuario, contrasena);
            System.out.println("Conectado a la base de datos.");
        } catch (SQLException e) {
            // Imprime más detalles del error
            System.out.println("Error al conectar a la base de datos, error: " + e.getMessage());
            e.printStackTrace();
        }
        return conectar;
    }

    public static void main(String[] args) {
        // Crear instancia y establecer la conexión
        Conexion conexion = new Conexion();
        conexion.establecerConexion();
    }
}