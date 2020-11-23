package com.ejemplo.perfectmarket_tiendavirtual.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.Producto;
import com.ejemplo.perfectmarket_tiendavirtual.R;

import java.util.ArrayList;

public class AdapterRecycler2 extends RecyclerView.Adapter<AdapterRecycler2.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Producto> producto;

    //listener
    private View.OnClickListener listener;

    public AdapterRecycler2(Context context, ArrayList<Producto> model){
        this.inflater = LayoutInflater.from(context);
        this.producto = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_item, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = producto.get(position).getNombre();
        String precio = producto.get(position).getPrecio();
        int imagen = producto.get(position).getImagenId();

        holder.nombre.setText(nombre);
        holder.precio.setText(precio);
        holder.imagen.setImageResource(imagen);
    }

    @Override
    public int getItemCount() {
        return producto.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, precio;
        ImageView imagen;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.texto_producto);
            imagen = itemView.findViewById(R.id.imagen_producto);
            precio = itemView.findViewById(R.id.precio_producto);
        }
    }
}
