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

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Producto> model;

    //listener
    private View.OnClickListener listener;

    public AdapterRecycler(Context context, ArrayList<Producto> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_recycler, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = model.get(position).getNombre();
        String dato_adicional = model.get(position).getInformacion();
        int imagen = model.get(position).getImagenId();

        holder.nombre.setText(nombre);
        holder.dato_adicional.setText(dato_adicional);
        holder.imagen.setImageResource(imagen);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, dato_adicional;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_categoria);
            dato_adicional = itemView.findViewById(R.id.dato_adicional);
            imagen = itemView.findViewById(R.id.imagenCategoria);
        }
    }
}
