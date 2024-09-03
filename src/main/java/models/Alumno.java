package models;

import java.util.Date;

public class Alumno {
    int ID;
    String nombre;
    String apellido;
    int edad;
    int inscripcion;
    int padre;
    int grado;
    Date fechaResgistro;

    public Alumno() {
    }

    public Alumno(int ID, String nombre, String apellido, int edad, int inscripcion, int padre, int grado, Date fechaResgistro) {
        this.ID = ID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.inscripcion = inscripcion;
        this.padre = padre;
        this.grado = grado;
        this.fechaResgistro = fechaResgistro;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(int inscripcion) {
        this.inscripcion = inscripcion;
    }

    public int getPadre() {
        return padre;
    }

    public void setPadre(int padre) {
        this.padre = padre;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    public Date getFechaResgistro() {
        return fechaResgistro;
    }

    public void setFechaResgistro(Date fechaResgistro) {
        this.fechaResgistro = fechaResgistro;
    }
}
