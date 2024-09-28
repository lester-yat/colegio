package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    
    public List<Grado> listarGrados() {
        List<Grado> listGrad = new ArrayList<>();
        String sql = "SELECT * FROM Grado";

        try (PreparedStatement ps = con.prepareStatement(sql);
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

        System.out.println("Total de grados encontrados: " + listGrad.size()); // Depuración.
        return listGrad;
    }
    
    public List listarSecciones(int idGrado) {
        List<Seccion> listSec = new ArrayList();
        String sql = "SELECT * FROM Seccion WHERE GradoID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idGrado);
            ResultSet rs = ps.executeQuery();
            
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
    
    public String columnaGrados(int idAlumno) {
        StringBuilder nombresGrados = new StringBuilder();
        String sql = "SELECT g.nombre " +
                     "FROM Grado g " +
                     "INNER JOIN Alumno al ON g.ID = al.GradoID " +
                     "WHERE al.ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            
            Set<String> gradosSet = new HashSet<>();
            while (rs.next()) {
                gradosSet.add(rs.getString("nombre"));
            }
            
            nombresGrados.append(String.join(", ", gradosSet));

        } catch (SQLException e) {
            System.out.println("Error al consultar los nombres de los grados: " + e.getMessage());
        }

        return nombresGrados.toString();
    }
    
    //    public List listarPadres() {
//        List<Padre> listPad = new ArrayList();
//        String sql = "SELECT * FROM Padre";
//
//        try (
//            PreparedStatement ps = con.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                Padre pr = new Padre();
//                
//                pr.setId(rs.getInt("ID"));
//                pr.setNombre(rs.getString("nombre"));
//                pr.setSalon(rs.getString("apellido"));
//                pr.setNivel(rs.getString("nivel"));
//                pr.setAnio(rs.getDate("anio"));
//                pr.setJornada(rs.getString("jornada"));
//                pr.setCantidadMaxEstudiantes(rs.getInt("cantidad_max_estudiantes"));
//                listPad.add(pr);
//            }
//        } catch (SQLException e) {
//            System.out.println("Error al listar los padres: " + e.toString());
//        }
//        return listPad;
//    }
    
    
        
        
  public List<Padre> listarPadres() {
    List<Padre> padres = new ArrayList<>();
    String sql = "SELECT id, nombre, apellido FROM padre";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Padre padre = new Padre();
            padre.setId(rs.getInt("ID"));
            padre.setNombre(rs.getString("nombre"));
            padre.setApellido(rs.getString("apellido"));
            padres.add(padre);
        }
    } catch (SQLException e) {
        System.out.println("Error al listar padres: " + e.getMessage());
    }
    return padres;
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
                    alumno.setFechaResgistro(rs.getDate("fecha_registro"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los datos del alumno: " + e.toString());
        }

        return alumno;
    }
    
    public int consultarInscripcion(int idAlumno) {
        String sql = "SELECT InscripcionID FROM Alumno WHERE ID = ?";
        int idInscripcion = 0;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idInscripcion = rs.getInt("InscripcionID");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la inscripcion: " + e.getMessage());
        }

        return idInscripcion;
    }
    
    public int guardarAlumno(Alumno alumno) {
        String sql = "INSERT INTO Alumno (nombre, apellido, edad, inscripcionID, PadreID, GradoID, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int idGenerado = -1;
        
        try (
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setInt(3, alumno.getEdad());
            ps.setInt(4, alumno.getInscripcion());
            ps.setInt(5, alumno.getPadre());
            ps.setInt(6, alumno.getGrado());
            ps.setDate(7, new java.sql.Date(alumno.getFechaResgistro().getTime()));
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar el alumno: " + e.toString());
        }
        
        return idGenerado;
    }
    
    public boolean guardarAlumSecc(int idAlumno, List<Integer> listaRelaciones) {
        String sql = "INSERT INTO Alumno_seccion (AlumnoID, SeccionID) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (Integer idSeccion : listaRelaciones) {
                ps.setInt(1, idAlumno);

                if (idSeccion != -1) {
                    ps.setInt(2, idSeccion);
                } else {
                    ps.setNull(2, java.sql.Types.INTEGER);
                }

                ps.addBatch();
            }

            ps.executeBatch();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al guardar las relaciones: " + e.toString());
            return false;
        }
    }
    
    public boolean eliminarAlumno(int id) {
        String sql = "DELETE FROM Alumno WHERE ID = ?";
        try (
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el alumno: " + e.toString());
            return false;
        }
    }
    
    public boolean editarAlumno(Alumno alumno) {
        String sql = "UPDATE Alumno SET nombre = ?, apellido = ?, edad = ?, InscripcionID = ?, PadreID = ?, GradoID = ?, fecha_registro = ? WHERE ID = ?";

        try (
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setInt(3, alumno.getEdad());
            ps.setInt(4, alumno.getInscripcion());
            ps.setInt(5, alumno.getPadre());
            ps.setInt(6, alumno.getGrado());
            ps.setDate(7, new java.sql.Date(alumno.getFechaResgistro().getTime()));
            ps.setInt(8, alumno.getID());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el alumno: " + e.toString());
            return false;
        }
    }
    
    public boolean editarAlumSecc(int idAlumno, List<Integer> listaIDSecciones) {
        String sqlDelete = "DELETE FROM Alumno_seccion WHERE AlumnoID = ?";
        String sqlInsert = "INSERT INTO Alumno_seccion (AlumnoID, SeccionID) VALUES (?, ?)";

        PreparedStatement psDelete = null;
        PreparedStatement psInsert = null;

        try {
            con.setAutoCommit(false);
            
            psDelete = con.prepareStatement(sqlDelete);
            psDelete.setInt(1, idAlumno);
            psDelete.executeUpdate();
            
            psInsert = con.prepareStatement(sqlInsert);
            for (Integer idSeccion : listaIDSecciones) {
                psInsert.setInt(1, idAlumno);
                psInsert.setInt(2, idSeccion);
                psInsert.addBatch();
            }

            psInsert.executeBatch();
            con.commit();
            return true;

        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error al deshacer cambios: " + ex.getMessage());
                }
            }
            System.out.println("Error al guardar relaciones del alumno: " + e.getMessage());
            return false;
        } finally {
            try {
                if (psDelete != null) psDelete.close();
                if (psInsert != null) psInsert.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    public List<Integer> obtenerSeccionesActuales(int idAlumno) {
        List<Integer> seccionesActuales = new ArrayList<>();
        String sql = "SELECT SeccionID FROM Alumno_seccion WHERE AlumnoID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                seccionesActuales.add(rs.getInt("SeccionID"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las secciones actuales del alumno: " + e.toString());
        }
        return seccionesActuales;
    }
    
    public void eliminarSeccionesPorAlumno(int idAlumno) {
        String sql = "DELETE FROM Alumno_seccion WHERE AlumnoID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar las secciones del alumno: " + e.toString());
        }
    }

    public void insertarSeccionPorAlumno(int idAlumno, int idSeccion) {
        String sql = "INSERT INTO Alumno_seccion (AlumnoID, SeccionID) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idAlumno);
            ps.setInt(2, idSeccion);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar la sección del alumno: " + e.toString());
        }
    }
}
