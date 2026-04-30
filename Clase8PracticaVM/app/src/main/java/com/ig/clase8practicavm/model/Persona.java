package com.ig.clase8practicavm.model;

public class Persona {
    private String nombre;
    private String apellido;
    private String email;

    public Persona(String nombre, String email, String apellido) {
        this.nombre = nombre;
        this.email = email;
        this.apellido = apellido;
    }

    public Persona() {

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
