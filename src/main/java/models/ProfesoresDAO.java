package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfesoresDAO {
    
    Conexion conexion = new Conexion();
    Connection con = conexion.establecerConexion();

    public boolean guardarProfesor(Profesor profesor) {
        String sql = "INSERT INTO Profesor (nombre, apellido, edad, identificacion, tipo_identificacion, telefono, email, genero, direccion, fecha_nacimiento, estado_civil, especialidad, salario, fecha_contratacion, fecha_terminacion_contrato, estado_contrato, tipo_contrato) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getApellido());
            ps.setInt(3, profesor.getEdad());
            ps.setString(4, profesor.getIdentificacion());
            ps.setString(5, profesor.getTipo_identificacion());
            ps.setString(6, profesor.getTelefono());
            ps.setString(7, profesor.getEmail());
            ps.setString(8, profesor.getGenero());
            ps.setString(9, profesor.getDireccion());
            ps.setDate(10, new java.sql.Date(profesor.getFecha_nacimiento().getTime()));
            ps.setString(11, profesor.getEstado_civil());
            ps.setString(12, profesor.getEspecialidad());
            ps.setInt(13, profesor.getSalario());
            ps.setDate(14, new java.sql.Date(profesor.getFecha_contratacion().getTime()));
            ps.setDate(15, new java.sql.Date(profesor.getFecha_terminacion_contrato().getTime()));
            ps.setString(16, profesor.getEstado_contrato());
            ps.setString(17, profesor.getTipo_contrato());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar el profesor: " + e.toString());
            return false;
        } finally{
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error: " + e.toString());
            }
        }
    }

    public List listarProfesores() {
        List<Profesor> listProf = new ArrayList();
        String sql = "SELECT * FROM Profesor";

        try (
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Profesor pr = new Profesor();
                
                pr.setId(rs.getInt("ID"));
                pr.setIdentificacion(rs.getString("identificacion"));
                pr.setNombre(rs.getString("nombre"));
                pr.setApellido(rs.getString("apellido"));
                pr.setEspecialidad(rs.getString("especialidad"));
                pr.setEstado_contrato(rs.getString("estado_contrato"));
                pr.setFecha_contratacion(rs.getDate("fecha_contratacion"));
                pr.setFecha_terminacion_contrato(rs.getDate("fecha_terminacion_contrato"));
                listProf.add(pr);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los profesores: " + e.toString());
        }
        return listProf;
    }
    
    public boolean eliminarProfesor(int id) {
        String sql = "DELETE FROM Profesor WHERE ID = ?";
        try (
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el profesor: " + e.toString());
            return false;
        }
    }
    
    public Profesor consultarDatos(int id) {
        String sql = "SELECT * FROM Profesor WHERE ID = ?";
        Profesor profesor = new Profesor();

        try (Connection con = new Conexion().establecerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    profesor.setId(rs.getInt("ID"));
                    profesor.setNombre(rs.getString("nombre"));
                    profesor.setApellido(rs.getString("apellido"));
                    profesor.setEdad(rs.getInt("edad"));
                    profesor.setIdentificacion(rs.getString("identificacion"));
                    profesor.setTipo_identificacion(rs.getString("tipo_identificacion"));
                    profesor.setTelefono(rs.getString("telefono"));
                    profesor.setEmail(rs.getString("email"));
                    profesor.setGenero(rs.getString("genero"));
                    profesor.setDireccion(rs.getString("direccion"));
                    profesor.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
                    profesor.setEstado_civil(rs.getString("estado_civil"));
                    profesor.setEspecialidad(rs.getString("especialidad"));
                    profesor.setSalario(rs.getInt("salario"));
                    profesor.setFecha_contratacion(rs.getDate("fecha_contratacion"));
                    profesor.setFecha_terminacion_contrato(rs.getDate("fecha_terminacion_contrato"));
                    profesor.setEstado_contrato(rs.getString("estado_contrato"));
                    profesor.setTipo_contrato(rs.getString("tipo_contrato"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los datos del profesor: " + e.toString());
        }

        return profesor;
    }
    
    public boolean editarProfesor(Profesor profesor) {
        String sql = "UPDATE Profesor SET nombre = ?, apellido = ?, edad = ?, identificacion = ?, tipo_identificacion = ?, telefono = ?, email = ?, genero = ?, direccion = ?, fecha_nacimiento = ?, estado_civil = ?, especialidad = ?, salario = ?, fecha_contratacion = ?, fecha_terminacion_contrato = ?, estado_contrato = ?, tipo_contrato = ? WHERE id = ?";

        try (Connection con = new Conexion().establecerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getApellido());
            ps.setInt(3, profesor.getEdad());
            ps.setString(4, profesor.getIdentificacion());
            ps.setString(5, profesor.getTipo_identificacion());
            ps.setString(6, profesor.getTelefono());
            ps.setString(7, profesor.getEmail());
            ps.setString(8, profesor.getGenero());
            ps.setString(9, profesor.getDireccion());
            ps.setDate(10, new java.sql.Date(profesor.getFecha_nacimiento().getTime()));
            ps.setString(11, profesor.getEstado_civil());
            ps.setString(12, profesor.getEspecialidad());
            ps.setInt(13, profesor.getSalario());
            ps.setDate(14, new java.sql.Date(profesor.getFecha_contratacion().getTime()));
            ps.setDate(15, new java.sql.Date(profesor.getFecha_terminacion_contrato().getTime()));
            ps.setString(16, profesor.getEstado_contrato());
            ps.setString(17, profesor.getTipo_contrato());
            ps.setInt(18, profesor.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el profesor: " + e.toString());
            return false;
        }
    }
}
