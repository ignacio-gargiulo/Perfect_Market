package com.ejemplo.perfectmarket_tiendavirtual.Entidades;

import java.io.Serializable;

public class ProductoCesta implements Serializable {

    private String id;
    private String nombre;
    private String precio;
    private String cantidad;
    private int imagen;

    /*public ProductoCesta(String nombre, String precio, String cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public ProductoCesta(String nombre, String precio, String cantidad, int imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }*/

    public ProductoCesta(String id, String nombre, String precio, String cantidad, int imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getId() {
        return id;
    }

    /*public int getImagen() {
        return imagen;
    }*/
}
