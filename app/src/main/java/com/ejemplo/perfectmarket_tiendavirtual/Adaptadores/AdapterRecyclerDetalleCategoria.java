package com.ejemplo.perfectmarket_tiendavirtual.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ejemplo.perfectmarket_tiendavirtual.Entidades.ProductosDetalleCategoria;
import com.ejemplo.perfectmarket_tiendavirtual.R;

import java.util.ArrayList;

public class AdapterRecyclerDetalleCategoria extends RecyclerView.Adapter<AdapterRecyclerDetalleCategoria.ViewHolderCategoria> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<ProductosDetalleCategoria> listaProductosDetalleCategoria;
    //listener
    private View.OnClickListener listener;

    public AdapterRecyclerDetalleCategoria(Context context, ArrayList<ProductosDetalleCategoria> listaProductosDetalleCategoria) {
        this.inflater = LayoutInflater.from(context);
        this.listaProductosDetalleCategoria = listaProductosDetalleCategoria;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public ViewHolderCategoria onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_recycler_detalle_categoria, parent, false);
        view.setOnClickListener(this);
        return new ViewHolderCategoria(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategoria holder, int position) {
        String nombre = listaProductosDetalleCategoria.get(position).getNombre();
        String precio = listaProductosDetalleCategoria.get(position).getPrecio();
        int imagen = listaProductosDetalleCategoria.get(position).getImagen();

        holder.nombre.setText(nombre);
        holder.precio.setText(precio);
        holder.imagen.setImageResource(imagen);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return listaProductosDetalleCategoria.size();
    }

    public class ViewHolderCategoria extends RecyclerView.ViewHolder{
        TextView nombre, precio, descripcion, otros_datos;
        ImageView imagen;

        public ViewHolderCategoria(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_producto_categoria);
            precio = itemView.findViewById(R.id.precio_producto_categoria);
            imagen = itemView.findViewById(R.id.imagenProductoCategoria);
        }
    }
}
