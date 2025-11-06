package es.erlantzg.modelos;

import java.time.LocalDate;

public class Persona {

    private int id;
    private String nombre;
    private String apellidos;
    private LocalDate cumpleanos;

    public Persona(int id, String nombre, String apellidos, LocalDate cumpleanos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.cumpleanos = cumpleanos;
    }

    public Persona(){
        this(0, "", "", null);
    }

    /* Id */
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    /* Nombre */
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    /* Apellidos */
    public String getApellidos(){
        return apellidos;
    }
    public void setId(String apellidos){
        this.apellidos = apellidos;
    }

    /* Cumpleaños */
    public LocalDate getCumpleanos(){
        return cumpleanos;
    }
    public void setCumpleanos(LocalDate cumpleanos){
        this.cumpleanos = cumpleanos;
    }

    @Override
    public String toString(){
        return "Id: " + id + ", nombre: " + nombre + ", apellidos: " + apellidos + ", cumpleaños: " + cumpleanos;
    }
}
