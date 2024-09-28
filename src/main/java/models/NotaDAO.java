package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotaDAO {
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
                al.setInscripcion(rs.getInt("InscripcionID"));
                al.setPadre(rs.getInt("PadreID"));
                al.setGrado(rs.getInt("GradoID"));
                listAlum.add(al);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los alumnos: " + e.toString());
        }
        return listAlum;
    }
    
    public List listarNotas() {
        List<Nota> listNotas = new ArrayList();
        String sql = "SELECT * FROM Nota";

        try (PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Nota nt = new Nota();
                
                nt.setId(rs.getInt("ID"));
                nt.setNombre(rs.getString("nombre"));
                nt.setCalificacion(rs.getFloat("calificacion"));
                nt.setFechaRegistro(rs.getDate("fecha_registro"));
                nt.setAlumnoID(rs.getInt("AlumnoID"));
                nt.setCursoID(rs.getInt("CursoID"));
                listNotas.add(nt);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar las notas: " + e.toString());
        }
        return listNotas;
    }
    
    public List<Curso> listarCursos() {
        List<Curso> listCurs = new ArrayList<>();
        String sql = "SELECT * FROM Curso";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Curso cs= new Curso();
                cs.setId(rs.getInt("ID"));
                cs.setNombre(rs.getString("nombre"));
                cs.setDescripcion(rs.getString("descripcion"));
                cs.setNivel(rs.getString("nivel"));
                cs.setProfesorID(rs.getInt("ProfesorID"));
                cs.setGradoID(rs.getInt("GradoID"));
                cs.setSeccionID(rs.getInt("SeccionID"));
                listCurs.add(cs);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los alumnos: " + e.toString());
        }
        return listCurs;
    }
    
    public String columnaAlumno(int notaID) {
        String nombreAlumno = null;
        String sql = "SELECT alm.nombre, alm.apellido " +
                     "FROM Alumno alm " +
                     "INNER JOIN Nota nt ON alm.ID = nt.AlumnoID " +
                     "WHERE nt.ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, notaID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nombreAlumno = rs.getString("nombre") + " " + rs.getString("apellido");
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar el nombre del Alumno: " + e.getMessage());
        }

        return nombreAlumno;
    }
    
    public String columnaCurso(int notaID) {
        String nombreCurso = null;
        String sql = "SELECT cr.nombre " +
                     "FROM Curso cr " +
                     "INNER JOIN Nota nt ON cr.ID = nt.CursoID " +
                     "WHERE nt.ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, notaID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nombreCurso = rs.getString(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar el nombre del Curso: " + e.getMessage());
        }

        return nombreCurso;
    }
    
    public Nota consultarDatos(int id) {
        String sql = "SELECT * FROM Nota WHERE ID = ?";
        Nota nota = new Nota();

        try (
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nota.setId(rs.getInt("ID"));
                    nota.setNombre(rs.getString("nombre"));
                    nota.setCalificacion(rs.getFloat("calificacion"));
                    nota.setFechaRegistro(rs.getDate("fecha_registro"));
                    nota.setAlumnoID(rs.getInt("AlumnoID"));
                    nota.setCursoID(rs.getInt("CursoID"));
                    
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los datos de la Nota: " + e.toString());
        }

        return nota;
    }
    
    public int guardarNota(Nota nota) {
        String sql = "INSERT INTO Nota (nombre, calificacion, fecha_registro, AlumnoID, CursoID) VALUES (?, ?, ?, ?, ?)";
        int idGenerado = -1;
        
        try (
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nota.getNombre());
            ps.setFloat(2, nota.getCalificacion());
            ps.setDate(3, new java.sql.Date(nota.getFechaRegistro().getTime()));
            ps.setInt(4, nota.getAlumnoID());
            ps.setInt(5, nota.getCursoID());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar la nota: " + e.toString());
        }
        
        return idGenerado;
    }
    
    public boolean eliminarNota(int id) {
        String sql = "DELETE FROM Nota WHERE ID = ?";
        try (
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar la nota: " + e.toString());
            return false;
        }
    }
    
    public boolean editarNota(Nota nota) {
        String sql = "UPDATE Nota SET nombre = ?, calificacion = ?, fecha_registro = ?, AlumnoID = ?, CursoID = ? WHERE ID = ?";

        try (
            PreparedStatement ps = con.prepareStatement(sql)) {
            
            System.out.println("Nombre: " + nota.getNombre());
            System.out.println("Calificación: " + nota.getCalificacion());
            System.out.println("Fecha de registro: " + nota.getFechaRegistro());
            System.out.println("AlumnoID: " + nota.getAlumnoID());
            System.out.println("CursoID: " + nota.getCursoID());
            System.out.println("ID: " + nota.getId());

            ps.setString(1, nota.getNombre());
            ps.setFloat(2, nota.getCalificacion());
            ps.setDate(3, new java.sql.Date(nota.getFechaRegistro().getTime()));
            ps.setInt(4, nota.getAlumnoID());
            ps.setInt(5, nota.getCursoID());
            ps.setInt(6, nota.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar la nota: " + e.toString());
            return false;
        }
    }
    
//    public boolean tieneDependencias(int idNota) {
//        String sql = "SELECT COUNT(*) FROM Nota WHERE AlumnoID = ?";
//        try (PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, idNota);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getInt(1) > 0; // Si hay registros relacionados
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}