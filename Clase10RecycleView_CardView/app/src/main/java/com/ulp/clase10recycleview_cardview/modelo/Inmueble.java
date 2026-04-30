package com.ulp.clase10recycleview_cardview.modelo;

public class Inmueble {
    private String direccion;
    private double precio;
    private double superficie;

    //Es de tipo entero porque rastrea la imagen por el id dentro del
    //proyecto. Cuando las imagenes no esten el proyecto la buscaremos mediante
    //una uri
    private int foto;

    public Inmueble() {
    }

    public Inmueble(String direccion, double precio, double superficie, int foto) {
        this.direccion = direccion;
        this.precio = precio;
        this.superficie = superficie;
        this.foto = foto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
