package com.ejemplo.perfectmarket_tiendavirtual.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ejemplo.perfectmarket_tiendavirtual.Entidades.ProductoCesta;
import com.ejemplo.perfectmarket_tiendavirtual.R;

import java.util.ArrayList;

public class AdapterRecyclerCesta extends RecyclerView.Adapter<AdapterRecyclerCesta.ViewHolderCesta> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<ProductoCesta> listaProductosCesta;
    //listener
    private View.OnClickListener listener;

    public AdapterRecyclerCesta(Context context, ArrayList<ProductoCesta> listaProductosCesta) {
        this.inflater = LayoutInflater.from(context);
        this.listaProductosCesta = listaProductosCesta;
    }

    @NonNull
    @Override
    public ViewHolderCesta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_recycler_productos_cesta, parent, false);
        view.setOnClickListener(this);
        return new ViewHolderCesta(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCesta holder, int position) {
        //String id = listaProductosCesta.get(position).getId();
        String nombre = listaProductosCesta.get(position).getNombre();
        String precio = listaProductosCesta.get(position).getPrecio();
        String cantidad = listaProductosCesta.get(position).getCantidad();
        //int imagen = listaProductosCesta.get(position).getImagen();

        //holder.id.setText(id);
        holder.nombre.setText(nombre);
        holder.precio.setText(precio + "€");
        holder.cantidad.setText(cantidad);
        //holder.imagen.setImageResource(imagen);
    }

    @Override
    public int getItemCount() {
        return listaProductosCesta.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }



    public class ViewHolderCesta extends RecyclerView.ViewHolder{
        TextView nombre, precio, cantidad, id;
        //ImageView imagen;

        public ViewHolderCesta(@NonNull View itemView) {
            super(itemView);
            //id = itemView.findViewById(R.id.id_producto_cesta);
            nombre = itemView.findViewById(R.id.nombre_producto_cesta);
            precio = itemView.findViewById(R.id.precio_producto_cesta);
            cantidad = itemView.findViewById(R.id.cantidad_producto_cesta);
            //imagen = itemView.findViewById(R.id.imagenProductoCesta);
        }
    }
}
