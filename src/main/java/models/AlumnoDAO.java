package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {
    Conexion conexion = new Conexion();
    Connection con = conexion.establecerConexion();
    
    public List listarAlumnos() {
        List<Alumno> listAlum = new ArrayList();
        String sql = "SELECT * FROM Alumno";

        try (PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alumno al = new Alumno();
                
                al.setID(rs.getInt("ID"));
                al.setNombre(rs.getString("nombre"));
                al.setApellido(rs.getString("apellido"));
                al.setEdad(rs.getInt("edad"));
                al.setInscripcion(rs.getInt("anio"));
                al.setPadre(rs.getInt("PadreID"));
                al.setGrado(rs.getInt("GradoID"));
                listAlum.add(al);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los alumnos: " + e.toString());
        }
        return listAlum;
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
    
    public Alumno consultarDatos(int id) {
        String sql = "SELECT * FROM Alumno WHERE ID = ?";
        Alumno alumno = new Alumno();

        try (
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    alumno.setID(rs.getInt("ID"));
                    alumno.setNombre(rs.getString("nombre"));
                    alumno.setApellido(rs.getString("apellido"));
                    alumno.setEdad(rs.getInt("edad"));
                    alumno.setInscripcion(rs.getInt("InscripcionID"));
                    alumno.setPadre(rs.getInt("PadreID"));
                    alumno.setGrado(rs.getInt("GradoID"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los datos del alumno: " + e.toString());
        }

        return alumno;
    }
    
    public boolean consultarInscripcion(int idInscripcion){
        return false;
    }
    
    public boolean guardarAlumno(Grado grado) {
        String sql = "INSERT INTO Grado (nombre, salon, nivel, anio, jornada, cantidad_max_estudiantes) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, grado.getNombre());
            ps.setString(2, grado.getSalon());
            ps.setString(3, grado.getNivel());
            ps.setDate(4, new java.sql.Date(grado.getAnio().getTime()));
            ps.setString(5, grado.getJornada());
            ps.setInt(6, grado.getCantidadMaxEstudiantes());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar el grado: " + e.toString());
            return false;
        } finally{
            try {
                con.close();
            } catch (Exception e) {
                System.out.println("Error: " + e.toString());
            }
        }
    }
    
    
    
    public boolean eliminarAlumno(int id) {
        String sql = "DELETE FROM Grado WHERE ID = ?";
        try (
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el grado: " + e.toString());
            return false;
        }
    }
    
    public boolean editarAlumno(Grado grado) {
        String sql = "UPDATE Grado SET nombre = ?, salon = ?, nivel = ?, anio = ?, jornada = ?, cantidad_max_estudiantes = ? WHERE id = ?";

        try (
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, grado.getNombre());
            ps.setString(2, grado.getSalon());
            ps.setString(3, grado.getNivel());
            ps.setDate(4, new java.sql.Date(grado.getAnio().getTime()));
            ps.setString(5, grado.getJornada());
            ps.setInt(6, grado.getCantidadMaxEstudiantes());
            ps.setInt(7, grado.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el grado: " + e.toString());
            return false;
        }
    }
}
