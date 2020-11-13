package com.ejemplo.perfectmarket_tiendavirtual.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ejemplo.perfectmarket_tiendavirtual.Entidades.ComentariosProductos;
import com.ejemplo.perfectmarket_tiendavirtual.R;

import java.util.ArrayList;

public class AdapterRecyclerComentarios extends RecyclerView.Adapter<AdapterRecyclerComentarios.ViewHolderComentarios> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<ComentariosProductos> listaComentariosProductos;
    //listener
    private View.OnClickListener listener;

    public AdapterRecyclerComentarios(Context context, ArrayList<ComentariosProductos> listaComentariosProductos) {
        this.inflater = LayoutInflater.from(context);
        this.listaComentariosProductos = listaComentariosProductos;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    @NonNull
    @Override
    public ViewHolderComentarios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_recycler_comentarios, parent, false);
        view.setOnClickListener(this);
        return new ViewHolderComentarios(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderComentarios holder, int position) {
       // String id = listaComentariosProductos.get(position).getId();
        String nombre = listaComentariosProductos.get(position).getNombreUsuario();
        String comentario = listaComentariosProductos.get(position).getComentario();
        String valoracion = listaComentariosProductos.get(position).getValoracion();
       // int imagen = listaComentariosProductos.get(position).getImagenUsuario();

        //holder.id.setText(id);
        holder.nombre.setText(nombre);
        holder.comentario.setText(comentario);
        holder.valoracion.setText(valoracion);
        //holder.imagen.setImageResource(imagen);
    }

    @Override
    public int getItemCount() {
        return listaComentariosProductos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public class ViewHolderComentarios extends RecyclerView.ViewHolder{
        TextView nombre, comentario, valoracion, id;
        ImageView imagen;

        public ViewHolderComentarios(@NonNull View itemView) {
            super(itemView);
            //id = itemView.findViewById(R.id.idComentarioUsuario);
            nombre = itemView.findViewById(R.id.nombreUsuario);
            comentario = itemView.findViewById(R.id.comentarioUsuario);
            valoracion = itemView.findViewById(R.id.valoracionUsuario);
            //imagen = itemView.findViewById(R.id.imagenUsuario);
        }
    }
}
