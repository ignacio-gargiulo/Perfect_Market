package com.ejemplo.perfectmarket_tiendavirtual;


import com.ejemplo.perfectmarket_tiendavirtual.Entidades.Producto;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.ProductosDetalleCategoria;

public interface iComunicaFragments {

    //Esto es enviarProductos
    public void enviarCategoria(Producto producto);
    public void enviarProducto(Producto producto);
    public void enviarProducto2(ProductosDetalleCategoria productosDetalleCategoria);

}
