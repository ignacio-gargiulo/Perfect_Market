package com.ejemplo.perfectmarket_tiendavirtual.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ejemplo.perfectmarket_tiendavirtual.Adaptadores.AdapterRecycler;
import com.ejemplo.perfectmarket_tiendavirtual.Adaptadores.AdapterRecyclerCesta;
import com.ejemplo.perfectmarket_tiendavirtual.Adaptadores.AdapterRecyclerComentarios;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.Categoria;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.ComentariosProductos;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.ProductoCesta;
import com.ejemplo.perfectmarket_tiendavirtual.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CestaFragment extends Fragment {


    ProgressBar progressBarCesta;
    TextView txtCargandoCesta;
    LinearLayout linearCesta;

    ImageView imgCestaSeparador;

    String nomProd, precioP, cantidadComprada;
    String URL, URL2, URL3, URL4, URL5, URL6, URL7, URL8, URL9, URL10, URL11, URL12, URL13, URL14, URL15, URL16, URL17,
            URLeliminarTodo, URLprecioTotalCesta;
    String id1, nombreProducto1, precio1, cantidad1;
    String id2, nombreProducto2, precio2, cantidad2;
    String id3, nombreProducto3, precio3, cantidad3;
    String id4, nombreProducto4, precio4, cantidad4;
    String id5, nombreProducto5, precio5, cantidad5;
    String id6, nombreProducto6, precio6, cantidad6;
    String id7, nombreProducto7, precio7, cantidad7;
    String id8, nombreProducto8, precio8, cantidad8;
    String id9, nombreProducto9, precio9, cantidad9;
    String id10, nombreProducto10, precio10, cantidad10;
    String id11, nombreProducto11, precio11, cantidad11;
    String id12, nombreProducto12, precio12, cantidad12;
    String id13, nombreProducto13, precio13, cantidad13;
    String id14, nombreProducto14, precio14, cantidad14;
    String id15, nombreProducto15, precio15, cantidad15;
    String id16, nombreProducto16, precio16, cantidad16;
    String numeroProductosCesta, sumaPrecioCesta;
    RequestQueue requestQueue;
    String URLObtenerDatosCesta;
    LinearLayout linearFab,linearCVCesta;

    FloatingActionButton fabPrincipal, fabRetroceder;

    TextView txtCestaVacia, txtPrecioTotal, txtNumProdCesta;
    ArrayList<ProductoCesta> listaProductosCesta;
    RecyclerView recyclerProductosCesta;
    AdapterRecyclerCesta adapterProductosCesta;
    Button btnComprar, btnEliminar;
    int cntP = 0;
    int cntP2 = 0;

    FragmentManager fm;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cesta, container, false);
        fm = getActivity().getSupportFragmentManager();
        requestQueue = Volley.newRequestQueue(getContext());
        URLObtenerDatosCesta = "https://perfectmarket.000webhostapp.com/perfect_market/obtenerProductosCestaUsuario.php";
        linearFab = view.findViewById(R.id.linearFab);
        linearCVCesta = view.findViewById(R.id.linearCVCesta);
        imgCestaSeparador = view.findViewById(R.id.imgCestaSeparador);
        fabPrincipal = view.findViewById(R.id.fabPrincipalCesta);
        fabRetroceder = view.findViewById(R.id.fabRetrocederCesta);

        progressBarCesta = view.findViewById(R.id.pbCesta);
        txtCargandoCesta = view.findViewById(R.id.txtCargandoCesta);
        linearCesta = view.findViewById(R.id.linearCesta);
        txtCestaVacia = view.findViewById(R.id.txtCestaVacia);
        txtPrecioTotal = view.findViewById(R.id.txtPrecioTotal);
        txtNumProdCesta = view.findViewById(R.id.txtNumTotalProductos);

        listaProductosCesta = new ArrayList<>();
        recyclerProductosCesta = view.findViewById(R.id.recyclerProductosCesta);

        btnComprar = view.findViewById(R.id.btnComprarProductosCesta);
        btnEliminar = view.findViewById(R.id.btnEliminarProductosCesta);

        txtCestaVacia.setVisibility(View.INVISIBLE);
        recyclerProductosCesta.setVisibility(View.VISIBLE);


        //direccionesURL();
        //obtenerDatos();
        //new CargarProductosCesta().execute("");

        mostrarDatos();
        //mostrarDatos();
        fabRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStack();
            }
        });

        fabPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fm.getBackStackEntryCount() == 1){
                    fm.popBackStack();
                }
                else {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

            }
        });

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final int numPC = Integer.valueOf(numeroProductosCesta);
                if (numPC >= 1 & cntP2 != 1 & cntP != 1){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setMessage("¿Seguro que quiere comprar todos los productos (" + numPC + ") de la cesta por un total de " + sumaPrecioCesta +"€?").setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Snackbar.make(view, "Se han comprado todos los productos (" + numPC + ") de la cesta por " + sumaPrecioCesta + "€", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                                    alertDialog.setMessage("¿Quiere mantener los productos en la cesta?")
                                            .setCancelable(false)
                                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Snackbar.make(view, "Se han mantenido todos los productos (" + numPC + ") en la cesta", Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    cntP2 = 1;
                                                    eliminarProductos(URLeliminarTodo);
                                                    listaProductosCesta.clear();
                                                    adapterProductosCesta.notifyDataSetChanged();
                                                    txtCestaVacia.setVisibility(View.VISIBLE);
                                                    recyclerProductosCesta.setVisibility(View.INVISIBLE);
                                                    Snackbar.make(view, "Se han eliminado todos los productos (" + numPC + ") de la cesta", Snackbar.LENGTH_LONG)
                                                            .setAction("Action", null).show();
                                                }
                                            });
                                    AlertDialog tituloMantenerTodosProductosCesta = alertDialog.create();
                                    tituloMantenerTodosProductosCesta.setTitle("Comprar todos los productos");
                                    tituloMantenerTodosProductosCesta.show();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            Snackbar.make(view, "Compra cancelada", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });

                    AlertDialog tituloComprarTodosProductosCesta = alertDialog.create();
                    tituloComprarTodosProductosCesta.setTitle("Comprar todos los productos");
                    tituloComprarTodosProductosCesta.show();
                }
                else {
                    Snackbar.make(view, "No hay productos en la cesta que comprar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final int numPC = Integer.valueOf(numeroProductosCesta);
                if (numPC >= 1 & cntP != 1 & cntP2 != 1){
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setMessage("¿Seguro que quiere eliminar todos los productos (" + numPC + ") de la cesta?").setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cntP = 1;
                            eliminarProductos(URLeliminarTodo);
                            listaProductosCesta.clear();
                            adapterProductosCesta.notifyDataSetChanged();
                            txtCestaVacia.setVisibility(View.VISIBLE);
                            recyclerProductosCesta.setVisibility(View.INVISIBLE);
                            Snackbar.make(view, "Se han eliminado todos los productos (" + numPC + ") de la cesta", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            Snackbar.make(view, "Eliminación cancelada", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });

                    AlertDialog tituloEliminarTodosProductosCesta = alertDialog.create();
                    tituloEliminarTodosProductosCesta.setTitle("Eliminar todos los productos");
                    tituloEliminarTodosProductosCesta.show();

                }
                else {
                    Snackbar.make(view, "No hay productos en la cesta que eliminar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });
        return view;
    }


    public void numProdC(String numPrCesta){
        txtNumProdCesta.setText(numPrCesta + "/16 productos");
        if (numPrCesta.equalsIgnoreCase("0")){
            try {
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            txtCestaVacia.setVisibility(View.VISIBLE);
            recyclerProductosCesta.setVisibility(View.INVISIBLE);
        }
        else {
            txtCestaVacia.setVisibility(View.INVISIBLE);
            recyclerProductosCesta.setVisibility(View.VISIBLE);
        }
    }

    public void precioTotal(String sumaPrecioCesta){
        if (sumaPrecioCesta.equalsIgnoreCase("")){
            txtPrecioTotal.setText("Precio total: 0€");
        }
        else {
            txtPrecioTotal.setText("Precio total: " + sumaPrecioCesta + "€");
        }
    }

    private void eliminarProductos(String URL){
        txtNumProdCesta.setText("0/16 productos");
        txtPrecioTotal.setText("Precio total: 0€");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "EL PRODUCTO FUE ELIMINADO", Toast.LENGTH_SHORT).show();
                //limpiarFormulario();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                //parametros.put("codigo", edtCodigo.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    class CargarProductosCesta extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBarCesta.setVisibility(View.VISIBLE);
            txtCargandoCesta.setVisibility(View.VISIBLE);
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
            progressBarCesta.setVisibility(View.INVISIBLE);
            txtCargandoCesta.setVisibility(View.INVISIBLE);
            linearCesta.setVisibility(View.VISIBLE);
            linearFab.setVisibility(View.VISIBLE);
            linearCVCesta.setVisibility(View.VISIBLE);
            imgCestaSeparador.setVisibility(View.VISIBLE);

            mostrarDatos();

            Toast.makeText(getActivity(), "DATOS CARGADOS", Toast.LENGTH_SHORT).show();
        }
    }

    public void direccionesURL() {
        URL17 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/pruebaNumRegistros.php";
        URLeliminarTodo = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/eliminar_todos_productos_Cesta.php";
        URLprecioTotalCesta = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_precio_cesta2.php";
    }

    public void mostrarDatos(){
        SharedPreferences preferences = getContext().getSharedPreferences("preferenciasDU",
                Context.MODE_PRIVATE);
        for (int i = 1; i < 17; i++ ) {
            obtenerProductosCesta(URLObtenerDatosCesta, preferences.getString("id", "0") , String.valueOf(i));
        }
    }




    private void obtenerProductosCesta(String URL, String id_usuario, String idProducto) {
        URL = URL + "?id_usuario=" + id_usuario + "&id_producto=" + idProducto;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        nomProd = jsonObject.getString("nombre_producto");
                        precioP = jsonObject.getString("precio_total");
                        cantidadComprada = jsonObject.getString("cantidad_comprada");
                        listaProductosCesta.add(new ProductoCesta(nomProd, "Precio: " + precioP, "Cantidad: " + cantidadComprada));
                        /*if (!valoracion1.isEmpty()){
                            valGen(Double.parseDouble(valoracion1));
                        }*/
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                recyclerProductosCesta.setLayoutManager(new LinearLayoutManager(getContext()));
                adapterProductosCesta = new AdapterRecyclerCesta(getContext(), listaProductosCesta);
                recyclerProductosCesta.setAdapter(adapterProductosCesta);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }


}
