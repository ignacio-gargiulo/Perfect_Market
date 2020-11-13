package com.ejemplo.perfectmarket_tiendavirtual;


import com.ejemplo.perfectmarket_tiendavirtual.Entidades.Categoria;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.ProductosDetalleCategoria;

public interface iComunicaFragments {

    //Esto es enviarProductos
    public void enviarCategoria(Categoria categoria);
    public void enviarProducto(Categoria categoria);
    public void enviarProducto2(ProductosDetalleCategoria productosDetalleCategoria);

}
