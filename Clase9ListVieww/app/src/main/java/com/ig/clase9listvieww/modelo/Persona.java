package com.ig.clase9listvieww.modelo;

import java.io.Serializable;

//Primero creamos una persona
public class Persona implements Serializable {
    private String apellido;
    private String nombre;

    private String correo;

    //Android indexa el elmento a traves de un id
    private int foto;

    public Persona(String apellido, String nombre, String correo, int foto) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.correo = correo;
        this.foto = foto;
    }

    public Persona() {
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
