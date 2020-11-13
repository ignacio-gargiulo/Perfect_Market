package com.ejemplo.perfectmarket_tiendavirtual.Entidades;

import java.io.Serializable;

public class ComentariosProductos implements Serializable {

    String id;
    String nombreUsuario;
    String Comentario;
    String Valoracion;
    //int imagenUsuario;

    /*public ComentariosProductos(String id, String nombreUsuario, String comentario, String valoracion, int imagenUsuario) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        Comentario = comentario;
        Valoracion = valoracion;
        this.imagenUsuario = imagenUsuario;
    }

    public ComentariosProductos(String id, String nombreUsuario, String comentario, String valoracion) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        Comentario = comentario;
        Valoracion = valoracion;
    }*/

    public ComentariosProductos(String nombreUsuario, String comentario, String valoracion) {
        this.nombreUsuario = nombreUsuario;
        Comentario = comentario;
        Valoracion = valoracion;
    }

    public String getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getComentario() {
        return Comentario;
    }

    public String getValoracion() {
        return Valoracion;
    }

    /*public int getImagenUsuario() {
        return imagenUsuario;
    }*/
}
