package com.ejemplo.perfectmarket_tiendavirtual.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ejemplo.perfectmarket_tiendavirtual.Adaptadores.AdapterRecycler2;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.Categoria;
import com.ejemplo.perfectmarket_tiendavirtual.R;
import com.ejemplo.perfectmarket_tiendavirtual.iComunicaFragments;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Iterator;


public class PrincipalFragment extends Fragment {

    Button btnCat, btncesta;
    ImageButton btnImagenAnterior, btnImagenSiguiente;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    int imgBBDD;
    int imgW, imgH;


    RecyclerView recyclerProducto1;
    RecyclerView recyclerProducto2;
    RecyclerView recyclerProducto3;
    RecyclerView recyclerProducto4;

    AdapterRecycler2 adapterCategorias;
    ArrayList<Categoria> listaCategorias1;
    ArrayList<Categoria> listaCategorias2;
    ArrayList<Categoria> listaCategorias3;
    ArrayList<Categoria> listaCategorias4;

    String URL, URL2, URL3, URL4, URL5, URL6, URL7, URL8,URL9,URL10,URL11,URL12,URL13,URL14,URL15,URL16;

    String nombreProducto1 = "", descripcion1, otrosDatos1, precio1, img1;
    String nombreProducto2, descripcion2, otrosDatos2, precio2, img2;
    String nombreProducto3,  descripcion3, otrosDatos3, precio3, img3;
    String nombreProducto4, descripcion4, otrosDatos4, precio4, img4;
    String nombreProducto5,  descripcion5, otrosDatos5, precio5, img5;
    String nombreProducto6, descripcion6, otrosDatos6, precio6, img6;
    String nombreProducto7, descripcion7, otrosDatos7, precio7, img7;
    String nombreProducto8,  descripcion8, otrosDatos8, precio8, img8;
    String nombreProducto9,  descripcion9, otrosDatos9, precio9, img9;
    String nombreProducto10, descripcion10, otrosDatos10, precio10, img10;
    String nombreProducto11, descripcion11, otrosDatos11, precio11, img11;
    String nombreProducto12,  descripcion12, otrosDatos12, precio12, img12;
    String nombreProducto13,  descripcion13, otrosDatos13, precio13, img13;
    String nombreProducto14,  descripcion14, otrosDatos14, precio14, img14;
    String nombreProducto15,  descripcion15, otrosDatos15, precio15, img15;
    String nombreProducto16,  descripcion16, otrosDatos16, precio16, img16;
    RequestQueue requestQueue;

    ViewFlipper viewFlipper;

    //referencias para comunicar fragments
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;

    ImageView imagenProducto;

    ProgressBar pb;
    LinearLayout linearPrincipal;
    TextView txtCargando;

    Bitmap bitmap;
    Drawable drawable;

    TextView txtImgId;

    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_principal, container, false);


        btnCat = view.findViewById(R.id.btnIrCategorias);
        btncesta = view.findViewById(R.id.btnIrCesta);
        btnImagenAnterior = view.findViewById(R.id.btnImagenAnterior);
        btnImagenSiguiente = view.findViewById(R.id.btnImagenSiguiente);

        imagenProducto = view.findViewById(R.id.imagen_producto);
        recyclerProducto1 = view.findViewById(R.id.recyclerPrincipal1);
        recyclerProducto2 = view.findViewById(R.id.recyclerPrincipal2);
        recyclerProducto3 = view.findViewById(R.id.recyclerPrincipal3);
        recyclerProducto4 = view.findViewById(R.id.recyclerPrincipal4);

        listaCategorias1 = new ArrayList<>();
        listaCategorias2 = new ArrayList<>();
        listaCategorias3 = new ArrayList<>();
        listaCategorias4 = new ArrayList<>();

        pb = view.findViewById(R.id.pb);
        txtCargando = view.findViewById(R.id.txtCargando);
        linearPrincipal = view.findViewById(R.id.LinearPrincipal);

        requestQueue = Volley.newRequestQueue(getContext());
        recuperarPreferencias();
        direccionesURL();
        direccionesURLImagenes();


        if(nombreProducto1.equalsIgnoreCase("")){

            obtenerDatosLista1();
            obtenerDatosLista2();
            obtenerDatosLista3();
            obtenerDatosLista4();
            new CargarProductos().execute("");
        }
        else{
            linearPrincipal.setVisibility(View.VISIBLE);
            cargarLista1();
            mostrarDatos1();

            cargarLista2();
            mostrarDatos2();

            cargarLista3();
            mostrarDatos3();

            cargarLista4();
            mostrarDatos4();

        }


        int imagenes[] =  {R.drawable.pm_banner, R.drawable.img3 ,R.drawable.img1, R.drawable.img2};
        viewFlipper = view.findViewById(R.id.viewFlipper);
        viewFlipper.setFlipInterval(4000);

        // en bucle
        for(int i = 0 ; i < imagenes.length; i++){
            flipperImagenes(imagenes[i]);
        }

        btnCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new CategoriasFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        btncesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new CestaFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        btnImagenAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousView(view);
            }
        });

        btnImagenSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextView(view);
            }
        });

        return view;
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

    private void recuperarPreferencias(){
        SharedPreferences preferences = getContext().getSharedPreferences("preferenciasLogin",
                Context.MODE_PRIVATE);
        Toast.makeText(actividad, "Email: " + preferences.getString("email", "Desconocido")
                + "\nContraseña: " + preferences.getString("contrasegna", "Desconocida"),
                Toast.LENGTH_SHORT).show();
    }
    public void flipperImagenes(int imagen){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(imagen);

        viewFlipper.addView(imageView);
        //viewFlipperP.setFlipInterval(4000);
        //viewFlipperP.setAutoStart(true);

        //animacion
        //viewFlipperP.setInAnimation(getContext(), android.R.anim.slide_in_left);
        //viewFlipperP.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }

    public void previousView(View v) {
        viewFlipper.setAutoStart(false);
        viewFlipper.setInAnimation(getContext(), R.anim.slide_in_right);
        viewFlipper.setOutAnimation(getContext(), R.anim.slide_out_left);
        viewFlipper.showPrevious();
    }

    public void nextView(View v) {
        viewFlipper.setAutoStart(false);
        viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
        viewFlipper.showNext();
    }

    public void cargarLista1(){

        listaCategorias1.add(new Categoria(nombreProducto1, precio1 + " €", descripcion1, otrosDatos1, R.drawable.img1));

        listaCategorias1.add(new Categoria(nombreProducto2, precio2 + " €", descripcion2, otrosDatos2, R.drawable.img2));

        listaCategorias1.add(new Categoria(nombreProducto3, precio3 + " €", descripcion3, otrosDatos3, R.drawable.img3));

        listaCategorias1.add(new Categoria(nombreProducto4, precio4 + " €", descripcion4, otrosDatos4, R.drawable.img4));
    }

    public void obtenerDatosLista1(){
        obtenerDatosDeBBDD(URL);
        obtenerDatosDeBBDD(URL2);
        obtenerDatosDeBBDD(URL3);
        obtenerDatosDeBBDD(URL4);
    }

    public void cargarLista2(){
        listaCategorias2.add(new Categoria(nombreProducto5,  precio5 + " €" ,descripcion5, otrosDatos5, R.drawable.img5));
        listaCategorias2.add(new Categoria(nombreProducto6,  precio6 + " €" ,descripcion6, otrosDatos6,R.drawable.img6));
        listaCategorias2.add(new Categoria(nombreProducto7, precio7 + " €" ,descripcion7, otrosDatos7,R.drawable.img7));
        listaCategorias2.add(new Categoria(nombreProducto8, precio8 + " €" ,descripcion8, otrosDatos8,R.drawable.img8));
    }

    public void obtenerDatosLista2(){
        obtenerDatosDeBBDD(URL5);
        obtenerDatosDeBBDD(URL6);
        obtenerDatosDeBBDD(URL7);
        obtenerDatosDeBBDD(URL8);
    }

    public void cargarLista3(){
        listaCategorias3.add(new Categoria(nombreProducto9,  precio9 + " €" ,descripcion9, otrosDatos9,R.drawable.img9));
        listaCategorias3.add(new Categoria(nombreProducto10,  precio10 + " €" ,descripcion10, otrosDatos10,R.drawable.img10));
        listaCategorias3.add(new Categoria(nombreProducto11, precio11 + " €" ,descripcion11, otrosDatos11,R.drawable.img11));
        listaCategorias3.add(new Categoria(nombreProducto12, precio12 + " €" ,descripcion12, otrosDatos12,R.drawable.img12));
    }

    public void obtenerDatosLista3(){
        obtenerDatosDeBBDD(URL9);
        obtenerDatosDeBBDD(URL10);
        obtenerDatosDeBBDD(URL11);
        obtenerDatosDeBBDD(URL12);
    }

    public void cargarLista4(){
        listaCategorias4.add(new Categoria(nombreProducto13,  precio13 + " €" ,descripcion13, otrosDatos13,R.drawable.img13));
        listaCategorias4.add(new Categoria(nombreProducto14,  precio14 + " €" ,descripcion14, otrosDatos14,R.drawable.img14));
        listaCategorias4.add(new Categoria(nombreProducto15, precio15 + " €" ,descripcion15, otrosDatos15,R.drawable.img15));
        listaCategorias4.add(new Categoria(nombreProducto16, precio16 + " €" ,descripcion16, otrosDatos16,R.drawable.img16));
    }

    public void obtenerDatosLista4(){
        obtenerDatosDeBBDD(URL13);
        obtenerDatosDeBBDD(URL14);
        obtenerDatosDeBBDD(URL15);
        obtenerDatosDeBBDD(URL16);
    }

    public void mostrarDatos1(){
        recyclerProducto1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterCategorias = new AdapterRecycler2(getContext(), listaCategorias1);
        recyclerProducto1.setAdapter(adapterCategorias);
        adapterCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)).getNombre();
               // Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarProducto(listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)));
            }
        });
    }

    public void mostrarDatos2(){
        recyclerProducto2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterCategorias = new AdapterRecycler2(getContext(), listaCategorias2);
        recyclerProducto2.setAdapter(adapterCategorias);
        adapterCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCategorias2.get(recyclerProducto2.getChildAdapterPosition(view)).getNombre();
                //Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarProducto(listaCategorias2.get(recyclerProducto2.getChildAdapterPosition(view)));
            }
        });
    }

    public void mostrarDatos3(){
        recyclerProducto3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterCategorias = new AdapterRecycler2(getContext(), listaCategorias3);
        recyclerProducto3.setAdapter(adapterCategorias);
        adapterCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCategorias3.get(recyclerProducto3.getChildAdapterPosition(view)).getNombre();
               // Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarProducto(listaCategorias3.get(recyclerProducto3.getChildAdapterPosition(view)));
            }
        });
    }

    public void mostrarDatos4(){
        recyclerProducto4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterCategorias = new AdapterRecycler2(getContext(), listaCategorias4);
        recyclerProducto4.setAdapter(adapterCategorias);
        adapterCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCategorias4.get(recyclerProducto4.getChildAdapterPosition(view)).getNombre();
               // Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarProducto(listaCategorias4.get(recyclerProducto4.getChildAdapterPosition(view)));
            }
        });
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

    public void direccionesURLImagenes(){
        img1 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img1.jpg";
        img2 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img2.jpg";
        img3 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img3.jpg";
        img4 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img4.jpg";
        img5 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img5.jpg";
        img6 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img6.jpg";
        img7 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img7.jpg";
        img8 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img8.jpg";
        img9 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img9.jpg";
        img10 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img10.jpg";
        img11= "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img11.jpg";
        img12= "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img12.jpg";
        img13= "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img13.jpg";
        img14= "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img14.jpg";
        img15= "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img15.jpg";
        img16= "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/imagenes/img16.jpg";
    }

    class CargarProductos extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
            txtCargando.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(3500);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {

            pb.setVisibility(View.INVISIBLE);
            txtCargando.setVisibility(View.INVISIBLE);
            linearPrincipal.setVisibility(View.VISIBLE);
            cargarLista1();
            mostrarDatos1();

            cargarLista2();
            mostrarDatos2();

            cargarLista3();
            mostrarDatos3();

            cargarLista4();
            mostrarDatos4();

            //Snackbar.make(view, "Datos cargados con éxito!!", Snackbar.LENGTH_SHORT)
             //       .setAction("Action", null).show();
            //Toast.makeText(actividad, "DATOS CARGADOS", Toast.LENGTH_SHORT).show();
        }
    }
}
