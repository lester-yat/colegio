package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeccionDAO {
    Conexion conexion = new Conexion();
    Connection con = conexion.establecerConexion();

    public boolean guardarSeccion(Seccion seccion) {
        String sql = "INSERT INTO Seccion (nombre, horario_inicio, horario_final, GradoID) VALUES (?, ?, ?, ?)";

        try (
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, seccion.getNombre());
            ps.setTime(2, seccion.getHorarioInicio());
            ps.setTime(3, seccion.getHorarioFinal());
            ps.setInt(4, seccion.getGrado());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar la seccion: " + e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error: " + e.toString());
            }
        }
    }

    public List listarSecciones() {
        List<Seccion> listSec = new ArrayList();
        String sql = "SELECT * FROM Seccion";

        try (
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Seccion sc = new Seccion();
                
                sc.setId(rs.getInt("ID"));
                sc.setNombre(rs.getString("nombre"));
                sc.setHorarioInicio(rs.getTime("horario_inicio"));
                sc.setHorarioFinal(rs.getTime("horario_final"));
                sc.setGrado(rs.getInt("GradoID"));
                listSec.add(sc);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar las secciones: " + e.toString());
        }
        return listSec;
    }
    
    public boolean eliminarSeccion(int id) {
        String sql = "DELETE FROM Seccion WHERE ID = ?";
        try (
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar la seccion: " + e.toString());
            return false;
        }
    }
    
    public Seccion consultarSeccion(int id) {
        String sql = "SELECT * FROM Seccion WHERE ID = ?";
        Seccion seccion = new Seccion();

        try (
            Connection con = new Conexion().establecerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (
                ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    seccion.setId(rs.getInt("ID"));
                    seccion.setNombre(rs.getString("nombre"));
                    seccion.setHorarioInicio(rs.getTime("horario_inicio"));
                    seccion.setHorarioFinal(rs.getTime("horario_final"));
                    seccion.setGrado(rs.getInt("GradoID"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los datos de la seccion: " + e.toString());
        }

        return seccion;
    }
    
    public boolean editarSeccion(Seccion seccion) {
        String sql = "UPDATE Seccion SET nombre = ?, horario_inicio = ?, horario_final = ?, GradoID = ? WHERE id = ?";

        try (
            Connection con = new Conexion().establecerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, seccion.getNombre());
            ps.setTime(2, seccion.getHorarioInicio());
            ps.setTime(3, seccion.getHorarioFinal());
            ps.setInt(4, seccion.getGrado());
            ps.setInt(5, seccion.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar la seccion: " + e.toString());
            return false;
        }
    }
    
    public List listarGrados() {
        List<Grado> listGrad = new ArrayList();
        String sql = "SELECT * FROM Grado";

        try (
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Grado gr = new Grado();
                
                gr.setId(rs.getInt("ID"));
                gr.setNombre(rs.getString("nombre"));
                gr.setSalon(rs.getString("salon"));
                gr.setNivel(rs.getString("nivel"));
                gr.setAnio(rs.getDate("anio"));
                gr.setJornada(rs.getString("jornada"));
                gr.setCantidadMaxEstudiantes(rs.getInt("cantidad_max_estudiantes"));
                listGrad.add(gr);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los grados: " + e.toString());
        }
        return listGrad;
    }
}
