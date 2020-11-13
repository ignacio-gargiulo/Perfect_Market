package com.ejemplo.perfectmarket_tiendavirtual.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ejemplo.perfectmarket_tiendavirtual.Adaptadores.AdapterRecycler2;
import com.ejemplo.perfectmarket_tiendavirtual.Adaptadores.AdapterRecyclerDetalleCategoria;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.Categoria;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.ProductosDetalleCategoria;
import com.ejemplo.perfectmarket_tiendavirtual.R;
import com.ejemplo.perfectmarket_tiendavirtual.iComunicaFragments;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DetalleCategoriaFragment extends Fragment {

    TextView nombre_detalle, dato_detalle;
    ImageView imagenDetalle;

    RecyclerView recyclerProducto1, recyclerProducto2, recyclerProducto3, recyclerProducto4;
    RecyclerView recyclerProducto5, recyclerProducto6, recyclerProducto7, recyclerProducto8;
    ArrayList<ProductosDetalleCategoria> listaCategorias1;
    ArrayList<ProductosDetalleCategoria> listaCategorias2;
    ArrayList<ProductosDetalleCategoria> listaCategorias3;
    ArrayList<ProductosDetalleCategoria> listaCategorias4;
    ArrayList<ProductosDetalleCategoria> listaCategorias5;
    ArrayList<ProductosDetalleCategoria> listaCategorias6;
    ArrayList<ProductosDetalleCategoria> listaCategorias7;
    ArrayList<ProductosDetalleCategoria> listaCategorias8;

    AdapterRecyclerDetalleCategoria adapterRecyclerDetalleCategoria;

    //referencias para comunicar fragments
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;

    FloatingActionButton fabRetroceder, fabPrincipal, fabCesta;

    String opcion;

    String URL, URL2, URL3, URL4, URL5, URL6, URL7, URL8,URL9,URL10,URL11,URL12,URL13,URL14,URL15,URL16;

    String nombreProducto1 = "", descripcion1, otrosDatos1, precio1;
    String nombreProducto2, descripcion2, otrosDatos2, precio2;
    String nombreProducto3,  descripcion3, otrosDatos3, precio3;
    String nombreProducto4, descripcion4, otrosDatos4, precio4;
    String nombreProducto5 = "" ,  descripcion5, otrosDatos5, precio5;
    String nombreProducto6, descripcion6, otrosDatos6, precio6;
    String nombreProducto7, descripcion7, otrosDatos7, precio7;
    String nombreProducto8,  descripcion8, otrosDatos8, precio8;
    String nombreProducto9= "",  descripcion9, otrosDatos9, precio9;
    String nombreProducto10, descripcion10, otrosDatos10, precio10;
    String nombreProducto11 = "", descripcion11, otrosDatos11, precio11;
    String nombreProducto12 = "",  descripcion12, otrosDatos12, precio12;
    String nombreProducto13 = "",  descripcion13, otrosDatos13, precio13;
    String nombreProducto14,  descripcion14, otrosDatos14, precio14;
    String nombreProducto15,  descripcion15, otrosDatos15, precio15;
    String nombreProducto16 = "",  descripcion16, otrosDatos16, precio16;
    RequestQueue requestQueue;

    ProgressBar pb2;
    LinearLayout linearCategorias, linearFabdetprodcat;
    TextView txtCargando2;

    FragmentTransaction fragmentTransaction;
    FragmentManager fm;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_producto_categoria,container, false);

        fm = getActivity().getSupportFragmentManager();
        fabRetroceder = view.findViewById(R.id.fabDRetrocederCat);
        fabPrincipal = view.findViewById(R.id.fabDPrincipalCat);
        fabCesta = view.findViewById(R.id.fabCestaDCat);
        linearFabdetprodcat = view.findViewById(R.id.linearFabdetprodcat);

        recyclerProducto1 = view.findViewById(R.id.recyclerProductosCategoria1);
        recyclerProducto2 = view.findViewById(R.id.recyclerProductosCategoria2);
        recyclerProducto3 = view.findViewById(R.id.recyclerProductosCategoria3);
        recyclerProducto4 = view.findViewById(R.id.recyclerProductosCategoria4);
        recyclerProducto5 = view.findViewById(R.id.recyclerProductosCategoria5);
        recyclerProducto6 = view.findViewById(R.id.recyclerProductosCategoria6);
        recyclerProducto7 = view.findViewById(R.id.recyclerProductosCategoria7);
        recyclerProducto8 = view.findViewById(R.id.recyclerProductosCategoria8);

        listaCategorias1 = new ArrayList<>();
        listaCategorias2 = new ArrayList<>();
        listaCategorias3 = new ArrayList<>();
        listaCategorias4 = new ArrayList<>();
        listaCategorias5 = new ArrayList<>();
        listaCategorias6 = new ArrayList<>();
        listaCategorias7 = new ArrayList<>();
        listaCategorias8 = new ArrayList<>();

        nombre_detalle = view.findViewById(R.id.nombre_categoria1);
        dato_detalle = view.findViewById(R.id.dato_adicional1);
        imagenDetalle = view.findViewById(R.id.imagenCategoria1);

        pb2 = view.findViewById(R.id.pb2);
        txtCargando2 = view.findViewById(R.id.txtCargando2);
        linearCategorias = view.findViewById(R.id.linearCategorias);

        requestQueue = Volley.newRequestQueue(getContext());

        direccionesURL();

        fabRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStack();
            }
        });

        fabCesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getActivity().getSupportFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, new CestaFragment());
                //fragmentManager.popBackStack();
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        fabPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "" + fm.getBackStackEntryCount(), Toast.LENGTH_SHORT).show();
                if (fm.getBackStackEntryCount() == 1){
                    fm.popBackStack();
                }
                else {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });


        //Crear objeto bundle para recibir el objeto envado por argumentos
        Bundle objetoCategoria = getArguments();
        Categoria categoria = null;

        //validacion para verificar si existen argumentos enviados para mostrar
        if (objetoCategoria != null){
            categoria = (Categoria) objetoCategoria.getSerializable("categoria");
            opcion = categoria.getNombre();

            //Establecer los datos en la vista
            nombre_detalle.setText(categoria.getNombre());
            dato_detalle.setText(categoria.getInformacion());
            imagenDetalle.setImageResource(categoria.getImagenId());
        }


        if (opcion.equalsIgnoreCase("apps móviles")){
            //Toast.makeText(actividad, "Hola caracola", Toast.LENGTH_SHORT).show();
            if(nombreProducto16.equalsIgnoreCase("")){
                obtenerDatosLista1();
                new CargarProductos2().execute("");
            }
            else{
                linearCategorias.setVisibility(View.VISIBLE);
                linearFabdetprodcat.setVisibility(View.VISIBLE);
                cargarLista1();
                mostrarDatos1();
            }
        }
        else if (opcion.equalsIgnoreCase("comida")){
            if(nombreProducto9.equalsIgnoreCase("")){
                obtenerDatosLista2();
                new CargarProductos2().execute("");
            }
            else{
                linearCategorias.setVisibility(View.VISIBLE);
                linearFabdetprodcat.setVisibility(View.VISIBLE);
                cargarLista2();
                mostrarDatos2();
            }
        }
        else if (opcion.equalsIgnoreCase("deporte")){
            if(nombreProducto5.equalsIgnoreCase("")){
                obtenerDatosLista3();
                new CargarProductos2().execute("");
            }
            else{
                linearCategorias.setVisibility(View.VISIBLE);
                linearFabdetprodcat.setVisibility(View.VISIBLE);
                cargarLista3();
                mostrarDatos3();
            }
        }
        else if (opcion.equalsIgnoreCase("electronica e informatica")){
            if(nombreProducto1.equalsIgnoreCase("")){
                obtenerDatosLista4();
                new CargarProductos2().execute("");
            }
            else{
                linearCategorias.setVisibility(View.VISIBLE);
                linearFabdetprodcat.setVisibility(View.VISIBLE);
                cargarLista4();
                mostrarDatos4();
            }
        }
        else if (opcion.equalsIgnoreCase("libros")){
            if(nombreProducto11.equalsIgnoreCase("")){
                obtenerDatosLista5();
                new CargarProductos2().execute("");
            }
            else{
                linearCategorias.setVisibility(View.VISIBLE);
                linearFabdetprodcat.setVisibility(View.VISIBLE);
                cargarLista5();
                mostrarDatos5();
            }
        }
        else if (opcion.equalsIgnoreCase("moda")){
            if(nombreProducto5.equalsIgnoreCase("")){
                obtenerDatosLista6();
                new CargarProductos2().execute("");
            }
            else{
                linearCategorias.setVisibility(View.VISIBLE);
                linearFabdetprodcat.setVisibility(View.VISIBLE);
                cargarLista6();
                mostrarDatos6();
            }
        }
        else if (opcion.equalsIgnoreCase("música")){
            if(nombreProducto13.equalsIgnoreCase("")){
                obtenerDatosLista7();
                new CargarProductos2().execute("");
            }
            else{
                linearCategorias.setVisibility(View.VISIBLE);
                linearFabdetprodcat.setVisibility(View.VISIBLE);
                cargarLista7();
                mostrarDatos7();
            }
        }
        else if (opcion.equalsIgnoreCase("videojuegos")){
            if(nombreProducto12.equalsIgnoreCase("")){
                obtenerDatosLista8();
                new CargarProductos2().execute("");
            }
            else{
                linearCategorias.setVisibility(View.VISIBLE);
                linearFabdetprodcat.setVisibility(View.VISIBLE);
                cargarLista8();
                mostrarDatos8();
            }
        }

        //cargarLista1();
        //mostrarDatos1();

        return view;
    }




    public void cargarLista1(){
        listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto16, precio16 + " €" , R.drawable.img16, descripcion16, otrosDatos16));
    }

    public void obtenerDatosLista1(){
        obtenerDatosDeBBDD(URL16);
    }

    public void cargarLista2(){
        listaCategorias2.add(new ProductosDetalleCategoria(nombreProducto9,  precio9 + " €" ,R.drawable.img9, descripcion9, otrosDatos9));
        listaCategorias2.add(new ProductosDetalleCategoria(nombreProducto10,  precio10 + " €" ,R.drawable.img10, descripcion10, otrosDatos10));
    }

    public void obtenerDatosLista2(){
        obtenerDatosDeBBDD(URL9);
        obtenerDatosDeBBDD(URL10);
    }

    public void cargarLista3(){
        listaCategorias3.add(new ProductosDetalleCategoria(nombreProducto5,  precio5 + " €" ,R.drawable.img5, descripcion5, otrosDatos5));
        listaCategorias3.add(new ProductosDetalleCategoria(nombreProducto6,  precio6 + " €" ,R.drawable.img6, descripcion6, otrosDatos6));
        listaCategorias3.add(new ProductosDetalleCategoria(nombreProducto7,  precio7 + " €" ,R.drawable.img7, descripcion7, otrosDatos7));
        listaCategorias3.add(new ProductosDetalleCategoria(nombreProducto8,  precio8 + " €" ,R.drawable.img8, descripcion8, otrosDatos8));
        listaCategorias3.add(new ProductosDetalleCategoria(nombreProducto15,  precio15 + " €" ,R.drawable.img15, descripcion15, otrosDatos15));
    }

    public void obtenerDatosLista3(){
        obtenerDatosDeBBDD(URL5);
        obtenerDatosDeBBDD(URL6);
        obtenerDatosDeBBDD(URL7);
        obtenerDatosDeBBDD(URL8);
        obtenerDatosDeBBDD(URL15);
    }

    public void cargarLista4(){

        listaCategorias4.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €",R.drawable.img1, descripcion1, otrosDatos1));

        listaCategorias4.add(new ProductosDetalleCategoria(nombreProducto2, precio2 + " €",R.drawable.img2, descripcion2, otrosDatos2));

        listaCategorias4.add(new ProductosDetalleCategoria(nombreProducto3, precio3 + " €",  R.drawable.img3, descripcion3, otrosDatos3));

        listaCategorias4.add(new ProductosDetalleCategoria(nombreProducto4, precio4 + " €",  R.drawable.img4, descripcion4, otrosDatos4));
    }

    public void obtenerDatosLista4(){
        obtenerDatosDeBBDD(URL);
        obtenerDatosDeBBDD(URL2);
        obtenerDatosDeBBDD(URL3);
        obtenerDatosDeBBDD(URL4);
    }

    public void cargarLista5(){
        listaCategorias5.add(new ProductosDetalleCategoria(nombreProducto11, precio11 + " €",  R.drawable.img11, descripcion11, otrosDatos11));
    }

    public void obtenerDatosLista5(){
        obtenerDatosDeBBDD(URL11);
    }

    public void cargarLista6(){
        listaCategorias6.add(new ProductosDetalleCategoria(nombreProducto5, precio5 + " €",  R.drawable.img5, descripcion5, otrosDatos5));
        listaCategorias6.add(new ProductosDetalleCategoria(nombreProducto6, precio6 + " €",  R.drawable.img6, descripcion6, otrosDatos6));
        listaCategorias6.add(new ProductosDetalleCategoria(nombreProducto7, precio7 + " €", R.drawable.img7, descripcion7, otrosDatos7));
    }

    public void obtenerDatosLista6(){
        obtenerDatosDeBBDD(URL5);
        obtenerDatosDeBBDD(URL6);
        obtenerDatosDeBBDD(URL7);
        obtenerDatosDeBBDD(URL8);
    }

    public void cargarLista7() {
        listaCategorias7.add(new ProductosDetalleCategoria(nombreProducto13, precio13 + " €", R.drawable.img13, descripcion13, otrosDatos13));
        listaCategorias7.add(new ProductosDetalleCategoria(nombreProducto14, precio14 + " €", R.drawable.img14, descripcion14, otrosDatos14));
    }

    public void obtenerDatosLista7(){
        obtenerDatosDeBBDD(URL13);
        obtenerDatosDeBBDD(URL14);
    }

    public void cargarLista8() {
        listaCategorias8.add(new ProductosDetalleCategoria(nombreProducto12, precio12 + " €",  R.drawable.img12, descripcion12, otrosDatos12));
    }

    public void obtenerDatosLista8(){
        obtenerDatosDeBBDD(URL12);
    }

    public void mostrarDatos1(){
        recyclerProducto1.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias1);
        recyclerProducto1.setAdapter(adapterRecyclerDetalleCategoria);
        adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)).getNombre();
                Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarProducto2(listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)));
            }
        });
    }

    public void mostrarDatos2(){
        recyclerProducto2.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias2);
        recyclerProducto2.setAdapter(adapterRecyclerDetalleCategoria);
        adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCategorias2.get(recyclerProducto2.getChildAdapterPosition(view)).getNombre();
                Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarProducto2(listaCategorias2.get(recyclerProducto2.getChildAdapterPosition(view)));
            }
        });
    }

    public void mostrarDatos3(){
        recyclerProducto3.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias3);
        recyclerProducto3.setAdapter(adapterRecyclerDetalleCategoria);
        adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCategorias3.get(recyclerProducto3.getChildAdapterPosition(view)).getNombre();
                Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarProducto2(listaCategorias3.get(recyclerProducto3.getChildAdapterPosition(view)));
            }
        });
    }

    public void mostrarDatos4(){
        recyclerProducto4.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias4);
        recyclerProducto4.setAdapter(adapterRecyclerDetalleCategoria);
        adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCategorias4.get(recyclerProducto4.getChildAdapterPosition(view)).getNombre();
                Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarProducto2(listaCategorias4.get(recyclerProducto4.getChildAdapterPosition(view)));
            }
        });
    }

    public void mostrarDatos5(){
        recyclerProducto5.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias5);
        recyclerProducto5.setAdapter(adapterRecyclerDetalleCategoria);
        adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCategorias5.get(recyclerProducto5.getChildAdapterPosition(view)).getNombre();
                Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarProducto2(listaCategorias5.get(recyclerProducto5.getChildAdapterPosition(view)));
            }
        });
    }

    public void mostrarDatos6(){
        recyclerProducto6.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias6);
        recyclerProducto6.setAdapter(adapterRecyclerDetalleCategoria);
        adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCategorias6.get(recyclerProducto6.getChildAdapterPosition(view)).getNombre();
                Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarProducto2(listaCategorias6.get(recyclerProducto6.getChildAdapterPosition(view)));
            }
        });
    }

    public void mostrarDatos7(){
        recyclerProducto7.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias7);
        recyclerProducto7.setAdapter(adapterRecyclerDetalleCategoria);
        adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCategorias7.get(recyclerProducto7.getChildAdapterPosition(view)).getNombre();
                Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarProducto2(listaCategorias7.get(recyclerProducto7.getChildAdapterPosition(view)));
            }
        });
    }

    public void mostrarDatos8(){
        recyclerProducto8.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias8);
        recyclerProducto8.setAdapter(adapterRecyclerDetalleCategoria);
        adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCategorias8.get(recyclerProducto8.getChildAdapterPosition(view)).getNombre();
                Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarProducto2(listaCategorias8.get(recyclerProducto8.getChildAdapterPosition(view)));
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            this.actividad = (Activity) context;
            interfaceComunicaFragments = (iComunicaFragments) this.actividad;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public void obtenerDatosDeBBDD(final String url){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        if (url.equalsIgnoreCase(URL)){
                            nombreProducto1 = jsonObject.getString("nombre_producto");
                            precio1 = jsonObject.getString("precio_producto");
                            descripcion1 = jsonObject.getString("descripcion_producto");
                            otrosDatos1 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL2)){
                            nombreProducto2 = jsonObject.getString("nombre_producto");
                            precio2 = jsonObject.getString("precio_producto");
                            descripcion2 = jsonObject.getString("descripcion_producto");
                            otrosDatos2 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL3)){
                            nombreProducto3 = jsonObject.getString("nombre_producto");
                            precio3 = jsonObject.getString("precio_producto");
                            descripcion3 = jsonObject.getString("descripcion_producto");
                            otrosDatos3 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL4)){
                            nombreProducto4 = jsonObject.getString("nombre_producto");
                            precio4 = jsonObject.getString("precio_producto");
                            descripcion4 = jsonObject.getString("descripcion_producto");
                            otrosDatos4 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL5)){
                            nombreProducto5 = jsonObject.getString("nombre_producto");
                            precio5 = jsonObject.getString("precio_producto");
                            descripcion5 = jsonObject.getString("descripcion_producto");
                            otrosDatos5 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL6)){
                            nombreProducto6 = jsonObject.getString("nombre_producto");
                            precio6 = jsonObject.getString("precio_producto");
                            descripcion6 = jsonObject.getString("descripcion_producto");
                            otrosDatos6 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL7)){
                            nombreProducto7 = jsonObject.getString("nombre_producto");
                            precio7 = jsonObject.getString("precio_producto");
                            descripcion7 = jsonObject.getString("descripcion_producto");
                            otrosDatos7 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL8)){
                            nombreProducto8 = jsonObject.getString("nombre_producto");
                            precio8 = jsonObject.getString("precio_producto");
                            descripcion8 = jsonObject.getString("descripcion_producto");
                            otrosDatos8 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL9)){
                            nombreProducto9 = jsonObject.getString("nombre_producto");
                            precio9 = jsonObject.getString("precio_producto");
                            descripcion9 = jsonObject.getString("descripcion_producto");
                            otrosDatos9 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL10)){
                            nombreProducto10 = jsonObject.getString("nombre_producto");
                            precio10 = jsonObject.getString("precio_producto");
                            descripcion10 = jsonObject.getString("descripcion_producto");
                            otrosDatos10 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL11)){
                            nombreProducto11 = jsonObject.getString("nombre_producto");
                            precio11 = jsonObject.getString("precio_producto");
                            descripcion11 = jsonObject.getString("descripcion_producto");
                            otrosDatos11 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL12)){
                            nombreProducto12 = jsonObject.getString("nombre_producto");
                            precio12 = jsonObject.getString("precio_producto");
                            descripcion12 = jsonObject.getString("descripcion_producto");
                            otrosDatos12 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL13)){
                            nombreProducto13 = jsonObject.getString("nombre_producto");
                            precio13 = jsonObject.getString("precio_producto");
                            descripcion13 = jsonObject.getString("descripcion_producto");
                            otrosDatos13 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL14)){
                            nombreProducto14 = jsonObject.getString("nombre_producto");
                            precio14 = jsonObject.getString("precio_producto");
                            descripcion14 = jsonObject.getString("descripcion_producto");
                            otrosDatos14 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL15)){
                            nombreProducto15 = jsonObject.getString("nombre_producto");
                            precio15 = jsonObject.getString("precio_producto");
                            descripcion15 = jsonObject.getString("descripcion_producto");
                            otrosDatos15 = jsonObject.getString("otros_datos_producto");
                        }
                        else if (url.equalsIgnoreCase(URL16)){
                            nombreProducto16 = jsonObject.getString("nombre_producto");
                            precio16 = jsonObject.getString("precio_producto");
                            descripcion16 = jsonObject.getString("descripcion_producto");
                            otrosDatos16 = jsonObject.getString("otros_datos_producto");
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR DE CONEXIÓN: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);

    }

    public void direccionesURL(){
        URL = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=1";
        URL2 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=2";
        URL3 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=3";
        URL4 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=4";
        URL5 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=5";
        URL6 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=6";
        URL7 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=7";
        URL8 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=8";
        URL9 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=9";
        URL10 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=10";
        URL11 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=11";
        URL12 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=12";
        URL13 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=13";
        URL14 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=14";
        URL15 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=15";
        URL16 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_pm.php?id=16";
    }

    class CargarProductos2 extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            pb2.setVisibility(View.VISIBLE);
            txtCargando2.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            pb2.setVisibility(View.INVISIBLE);
            txtCargando2.setVisibility(View.INVISIBLE);
            linearCategorias.setVisibility(View.VISIBLE);
            linearFabdetprodcat.setVisibility(View.VISIBLE);

            if (opcion.equalsIgnoreCase("apps móviles")){
                cargarLista1();
                mostrarDatos1();
            }
            else if (opcion.equalsIgnoreCase("comida")){
                cargarLista2();
                mostrarDatos2();
            }
            else if (opcion.equalsIgnoreCase("deporte")){
                cargarLista3();
                mostrarDatos3();
            }
            else if (opcion.equalsIgnoreCase("electronica e informatica")){
                cargarLista4();
                mostrarDatos4();
            }
            else if (opcion.equalsIgnoreCase("libros")){
                cargarLista5();
                mostrarDatos5();
            }
            else if (opcion.equalsIgnoreCase("moda")){
                cargarLista6();
                mostrarDatos6();
            }
            else if (opcion.equalsIgnoreCase("música")){
                cargarLista7();
                mostrarDatos7();
            }
            else if (opcion.equalsIgnoreCase("videojuegos")){
                cargarLista8();
                mostrarDatos8();
            }
            Toast.makeText(actividad, "DATOS CARGADOS", Toast.LENGTH_SHORT).show();
        }
    }
}
