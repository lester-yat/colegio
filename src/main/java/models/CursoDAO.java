package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CursoDAO {
    
    Conexion conexion = new Conexion();
    Connection con = conexion.establecerConexion();
    
    public String columnaProfesores(int idCurso) {
        StringBuilder nombresProfesores = new StringBuilder();
        String sql = "SELECT p.nombre, p.apellido " +
                     "FROM Profesor p " +
                     "INNER JOIN Curso cr ON p.ID = cr.ProfesorID " +
                     "WHERE cr.ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
            ResultSet rs = ps.executeQuery();
            
            if (rs.isBeforeFirst()) {
                Set<String> profesoresSet = new HashSet<>();
                while (rs.next()) {
                    String nombreCompleto = rs.getString("nombre") + " " + rs.getString("apellido");
                    profesoresSet.add(nombreCompleto);
                }
                nombresProfesores.append(String.join(", ", profesoresSet));
            } else {
                nombresProfesores.append("No hay profesores asignados a este curso.");
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar los nombres de los profesores: " + e.getMessage());
            return "Error al consultar los profesores.";
        }

        return nombresProfesores.toString();
    }
    
    public String columnaGrados(int idCurso) {
        StringBuilder nombresGrados = new StringBuilder();
        String sql = "SELECT g.nombre " +
                     "FROM Grado g " +
                     "INNER JOIN Curso cr ON g.ID = cr.GradoID " +
                     "WHERE cr.ID = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
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
    
    public String columnaSecciones(int idCurso) {
        StringBuilder nombresSecciones = new StringBuilder();
        String sql = "SELECT s.nombre " +
                     "FROM Seccion s " +
                     "INNER JOIN Curso cr ON s.ID = cr.SeccionID " +
                     "WHERE cr.ID = ?";
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCurso);
            ResultSet rs = ps.executeQuery();
            
            Set<String> seccionesSet = new HashSet<>();
            while (rs.next()) {
                seccionesSet.add(rs.getString("nombre"));
            }
            
            nombresSecciones.append(String.join(", ", seccionesSet));
            
        } catch (SQLException e) {
            System.out.println("Error al consultar los nombres de las secciones: " + e.getMessage());
        }
        
        return nombresSecciones.toString();
    }
    
    public List listarCursos() {
        List<Curso> listCur = new ArrayList();
        String sql = "SELECT * FROM Curso";

        try (PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Curso cr = new Curso();
                
                cr.setId(rs.getInt("ID"));
                cr.setNombre(rs.getString("nombre"));
                cr.setDescripcion(rs.getString("descripcion"));
                cr.setNivel(rs.getString("nivel"));
                cr.setProfesorID(rs.getInt("ProfesorID"));
                cr.setGradoID(rs.getInt("GradoID"));
                cr.setSeccionID(rs.getInt("SeccionID"));
                listCur.add(cr);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los alumnos: " + e.toString());
        }
        return listCur;
    }
    
    public List<Profesor> listarProfesores() {
        List<Profesor> listProf = new ArrayList<>();
        String sql = "SELECT * FROM Profesor";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Profesor pr = new Profesor();
                pr.setId(rs.getInt("ID"));
                pr.setNombre(rs.getString("nombre"));
                pr.setApellido(rs.getString("apellido"));
                pr.setEdad(rs.getInt("edad"));
                pr.setIdentificacion(rs.getString("identificacion"));
                pr.setTipo_identificacion(rs.getString("tipo_identificacion"));
                pr.setTelefono(rs.getString("telefono"));
                pr.setEmail(rs.getString("email"));
                pr.setGenero(rs.getString("genero"));
                pr.setDireccion(rs.getString("direccion"));
                pr.setFecha_nacimiento(rs.getDate("fecha_nacimiento"));
                pr.setEstado_civil(rs.getString("estado_civil"));
                pr.setEspecialidad(rs.getString("especialidad"));
                pr.setSalario(rs.getInt("salario"));
                pr.setFecha_contratacion(rs.getDate("fecha_contratacion"));
                pr.setFecha_terminacion_contrato(rs.getDate("fecha_terminacion_contrato"));
                pr.setEstado_contrato(rs.getString("estado_contrato"));
                pr.setTipo_contrato(rs.getString("tipo_contrato"));
                listProf.add(pr);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los profesores: " + e.toString());
        }
        
        return listProf;
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
                gr.setNivel(rs.getString("anio"));
                gr.setJornada(rs.getString("jornada"));
                gr.setCantidadMaxEstudiantes(rs.getInt("cantidad_max_estudiantes"));
                listGrad.add(gr);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar los grados: " + e.toString());
        }
        
        return listGrad;
    }
    
    public List<Grado> listarGrados(int profesorId) {
        List<Grado> listGrad = new ArrayList<>();
        String sqlGradoIDs = "SELECT GradoID FROM Profesor_grado WHERE ProfesorID = ?";

        try {
            // Obtener los IDs de Grado relacionados con el profesor
            PreparedStatement psGradoIDs = con.prepareStatement(sqlGradoIDs);
            psGradoIDs.setInt(1, profesorId);
            ResultSet rsGradoIDs = psGradoIDs.executeQuery();

            // Recorrer los IDs de Grado obtenidos
            while (rsGradoIDs.next()) {
                int gradoId = rsGradoIDs.getInt("GradoID");

                // Verificar si el grado ya existe en la lista
                boolean gradoExiste = listGrad.stream().anyMatch(g -> g.getId() == gradoId);

                // Si el grado no existe, consultarlo y agregarlo a la lista
                if (!gradoExiste) {
                    String sqlGrado = "SELECT * FROM Grado WHERE ID = ?";
                    PreparedStatement psGrado = con.prepareStatement(sqlGrado);
                    psGrado.setInt(1, gradoId);
                    ResultSet rsGrado = psGrado.executeQuery();

                    if (rsGrado.next()) {
                        Grado gr = new Grado();
                        gr.setId(rsGrado.getInt("ID"));
                        gr.setNombre(rsGrado.getString("nombre"));
                        gr.setSalon(rsGrado.getString("salon"));
                        gr.setNivel(rsGrado.getString("nivel"));
                        gr.setAnio(rsGrado.getDate("anio"));
                        gr.setJornada(rsGrado.getString("jornada"));
                        gr.setCantidadMaxEstudiantes(rsGrado.getInt("cantidad_max_estudiantes"));
                        listGrad.add(gr);
                    }
                    psGrado.close();
                }
            }

            psGradoIDs.close();
        } catch (SQLException e) {
            System.out.println("Error al listar los grados: " + e.toString());
        }

        return listGrad;
    }
    
    public List<Seccion> listarSecciones() {
        List<Seccion> listSecc = new ArrayList<>();
        String sql = "SELECT * FROM Seccion";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Seccion sc = new Seccion();
                sc.setId(rs.getInt("ID"));
                sc.setNombre(rs.getString("nombre"));
                sc.setHorarioInicio(rs.getTime("horario_inicio"));
                sc.setHorarioFinal(rs.getTime("horario_final"));
                sc.setGrado(rs.getInt("GradoID"));
                listSecc.add(sc);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar las secciones: " + e.toString());
        }
        
        return listSecc;
    }
    
    public List listarSecciones(int idGrado, int idProfesor) {
        List<Seccion> listSecc = new ArrayList<>();
        String sqlSeccionIDs = "SELECT SeccionID FROM Profesor_grado WHERE GradoID = ? AND ProfesorID = ?";

        try {
            PreparedStatement psSeccionIDs = con.prepareStatement(sqlSeccionIDs);
            psSeccionIDs.setInt(1, idGrado);
            psSeccionIDs.setInt(2, idProfesor);
            ResultSet rsSeccionIDs = psSeccionIDs.executeQuery();
            
            while (rsSeccionIDs.next()) {
                int seccionId = rsSeccionIDs.getInt("SeccionID");
                
                boolean seccionExiste = listSecc.stream().anyMatch(g -> g.getId() == seccionId);
                
                if (!seccionExiste) {
                    String sqlSeccion = "SELECT * FROM Seccion WHERE ID = ?";
                    PreparedStatement psGrado = con.prepareStatement(sqlSeccion);
                    psGrado.setInt(1, seccionId);
                    ResultSet rsSeccion = psGrado.executeQuery();

                    if (rsSeccion.next()) {
                        Seccion sc = new Seccion();
                        sc.setId(rsSeccion.getInt("ID"));
                        sc.setNombre(rsSeccion.getString("nombre"));
                        sc.setHorarioInicio(rsSeccion.getTime("horario_inicio"));
                        sc.setHorarioFinal(rsSeccion.getTime("horario_final"));
                        sc.setGrado(rsSeccion.getInt("GradoID"));
                        listSecc.add(sc);
                    }
                    psGrado.close();
                }
            }

            psSeccionIDs.close();
        } catch (SQLException e) {
            System.out.println("Error al listar las secciones: " + e.toString());
        }

        return listSecc;
    }
    
    public Curso consultarDatos(int id) {
        String sql = "SELECT * FROM Curso WHERE ID = ?";
        Curso curso = new Curso();

        try (
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    curso.setId(rs.getInt("ID"));
                    curso.setNombre(rs.getString("nombre"));
                    curso.setDescripcion(rs.getString("descripcion"));
                    curso.setNivel(rs.getString("nivel"));
                    curso.setProfesorID(rs.getInt("ProfesorID"));
                    curso.setGradoID(rs.getInt("GradoID"));
                    curso.setSeccionID(rs.getInt("SeccionID"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los datos del curso: " + e.toString());
        }

        return curso;
    }
    
    public int guardarCurso(Curso curso) {
        String sql = "INSERT INTO Curso (nombre, descripcion, nivel, ProfesorID, GradoID, SeccionID) VALUES (?, ?, ?, ?, ?, ?)";
        int idGenerado = -1;
        
        try (
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, curso.getNombre());
            ps.setString(2, curso.getDescripcion());
            ps.setString(3, curso.getNivel());
            ps.setInt(4, curso.getProfesorID());
            ps.setInt(5, curso.getGradoID());
            ps.setInt(6, curso.getSeccionID());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar el curso: " + e.toString());
        }
        
        return idGenerado;
    }
    
    public boolean editarCurso(Curso curso) {
        String sql = "UPDATE Curso SET nombre = ?, descripcion = ?, nivel = ?, ProfesorID = ?, GradoID = ?, SeccionID=? WHERE ID = ?";

        try (
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, curso.getNombre());
            ps.setString(2, curso.getDescripcion());
            ps.setString(3, curso.getNivel());
            ps.setInt(4, curso.getProfesorID());
            ps.setInt(5, curso.getGradoID());
            ps.setInt(6, curso.getSeccionID());
            ps.setInt(7, curso.getId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el curso: " + e.toString());
            return false;
        }
    }
    
    public boolean eliminarCurso(int id) {
        String sql = "DELETE FROM Curso WHERE ID = ?";
        try (
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar el curso: " + e.toString());
            return false;
        }
    }
    
//    public boolean tieneDependencias(int idCurso) {
//        String sql = "SELECT COUNT(*) FROM Nota WHERE CursoID = ?";
//        try (PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, idCurso);
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
