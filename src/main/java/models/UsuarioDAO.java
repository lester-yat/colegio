/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 *to edit this template
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuarioDAO  {
    
        public boolean validarUsuario(String usuario, String contraseña) {
        Conexion conexion = new Conexion();
    Connection con = conexion.establecerConexion();
        String sql = "SELECT * FROM inicio WHERE nombre_usuario = ? AND contraseña = ?";
        
        String contraseñaEncriptada = Segurdad.encriptarSHA256(contraseña); // Encriptar la contraseña ingresada

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, contraseñaEncriptada); // Comparar con la contraseña encriptada
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true; // Usuario válido
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false; // Usuario o contraseña incorrectos
    }
    
    
}
