package com.ejemplo.perfectmarket_tiendavirtual.Entidades;

import java.io.Serializable;

public class ProductosDetalleCategoria implements Serializable {

    private String nombre;
    private String precio;
    private String informacion;
    private String otrosDatos;
    int imagen;

    /*public ProductosDetalleCategoria(String nombre, String precio, int imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }*/

    public ProductosDetalleCategoria(String nombre, String precio, int imagen,String informacion, String otrosDatos) {
        this.nombre = nombre;
        this.precio = precio;
        this.informacion = informacion;
        this.otrosDatos = otrosDatos;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public int getImagen() {
        return imagen;
    }

    public String getInformacion() {
        return informacion;
    }

    public String getOtrosDatos() {
        return otrosDatos;
    }
}
