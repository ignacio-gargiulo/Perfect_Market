package com.ejemplo.perfectmarket_tiendavirtual.Entidades;



import java.io.Serializable;

public class Producto implements Serializable {
    private String nombre;
    private String informacion;
    private String otrosDatos;
    private String precio;
    private int imagenId;

    public Producto(){}

    public Producto(String nombreCategoria, String precio, int imagenId) {
        this.nombre = nombreCategoria;
        this.precio = precio;
        this.informacion = "Sin dato adicional";
        this.imagenId = imagenId;
    }


    public Producto(String nombreCategoria, int imagenId) {
        this.nombre = nombreCategoria;
        this.informacion = "Sin dato adicional";
        this.imagenId = imagenId;
    }

    public Producto(String nombreCategoria, String precio, String informacion, String otrosDatos, int imagenId) {
        this.nombre = nombreCategoria;
        this.precio = precio;
        this.informacion = informacion;
        this.otrosDatos = otrosDatos;
        this.imagenId = imagenId;
    }


    public String getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getOtrosDatos() {
        return otrosDatos;
    }

    public String getInformacion() {
        return informacion;
    }

    public int getImagenId() {
        return imagenId;
    }


}
