package models;

public class Curso {
    private int id;
    private String nombre;
    private String descripcion;
    private String nivel;
    private int profesorID;
    private int gradoID;
    private int seccionID;

    public Curso() {
    }

    public Curso(int id, String nombre, String descripcion, String nivel, int profesorID, int gradoID, int seccionID) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.profesorID = profesorID;
        this.gradoID = gradoID;
        this.seccionID = seccionID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public int getProfesorID() {
        return profesorID;
    }

    public void setProfesorID(int profesorID) {
        this.profesorID = profesorID;
    }

    public int getGradoID() {
        return gradoID;
    }

    public void setGradoID(int gradoID) {
        this.gradoID = gradoID;
    }

    public int getSeccionID() {
        return seccionID;
    }

    public void setSeccionID(int seccionID) {
        this.seccionID = seccionID;
    }
}
