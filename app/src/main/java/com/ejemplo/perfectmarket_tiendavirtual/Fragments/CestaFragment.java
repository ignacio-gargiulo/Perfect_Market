package com.ejemplo.perfectmarket_tiendavirtual.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.Categoria;
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


        direccionesURL();
        obtenerDatos();
        new CargarProductosCesta().execute("");

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

    public void cargarListaProductosCesta() {
        if (numeroProductosCesta.equalsIgnoreCase("17")) {
            Toast.makeText(getContext(), "Se ha alcanzado el límite de 16 productos en la cesta", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "Se muestran 16/" + numeroProductosCesta + " productos en la cesta", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Hay " + numeroProductosCesta + "/16 productos en la cesta", Toast.LENGTH_SHORT).show();
        }
        if (id1 == null) {
            Toast.makeText(getContext(), "Cesta Vacía", Toast.LENGTH_SHORT).show();
        } else if (id2 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id3 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id4 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id5 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id6 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id5, nombreProducto5, "Precio: " + precio5 + " €", "Cantidad: " + cantidad5 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id7 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id5, nombreProducto5, "Precio: " + precio5 + " €", "Cantidad: " + cantidad5 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id6, nombreProducto6, "Precio: " + precio6 + " €", "Cantidad: " + cantidad6 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id8 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id5, nombreProducto5, "Precio: " + precio5 + " €", "Cantidad: " + cantidad5 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id6, nombreProducto6, "Precio: " + precio6 + " €", "Cantidad: " + cantidad6 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id7, nombreProducto7, "Precio: " + precio7 + " €", "Cantidad: " + cantidad7 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id9 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id5, nombreProducto5, "Precio: " + precio5 + " €", "Cantidad: " + cantidad5 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id6, nombreProducto6, "Precio: " + precio6 + " €", "Cantidad: " + cantidad6 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id7, nombreProducto7, "Precio: " + precio7 + " €", "Cantidad: " + cantidad7 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id8, nombreProducto8, "Precio: " + precio8 + " €", "Cantidad: " + cantidad8 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id10 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id5, nombreProducto5, "Precio: " + precio5 + " €", "Cantidad: " + cantidad5 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id6, nombreProducto6, "Precio: " + precio6 + " €", "Cantidad: " + cantidad6 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id7, nombreProducto7, "Precio: " + precio7 + " €", "Cantidad: " + cantidad7 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id8, nombreProducto8, "Precio: " + precio8 + " €", "Cantidad: " + cantidad8 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id9, nombreProducto9, "Precio: " + precio9 + " €", "Cantidad: " + cantidad9 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id11 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id5, nombreProducto5, "Precio: " + precio5 + " €", "Cantidad: " + cantidad5 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id6, nombreProducto6, "Precio: " + precio6 + " €", "Cantidad: " + cantidad6 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id7, nombreProducto7, "Precio: " + precio7 + " €", "Cantidad: " + cantidad7 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id8, nombreProducto8, "Precio: " + precio8 + " €", "Cantidad: " + cantidad8 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id9, nombreProducto9, "Precio: " + precio9 + " €", "Cantidad: " + cantidad9 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id10, nombreProducto10, "Precio: " + precio10 + " €", "Cantidad: " + cantidad10 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id12 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id5, nombreProducto5, "Precio: " + precio5 + " €", "Cantidad: " + cantidad5 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id6, nombreProducto6, "Precio: " + precio6 + " €", "Cantidad: " + cantidad6 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id7, nombreProducto7, "Precio: " + precio7 + " €", "Cantidad: " + cantidad7 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id8, nombreProducto8, "Precio: " + precio8 + " €", "Cantidad: " + cantidad8 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id9, nombreProducto9, "Precio: " + precio9 + " €", "Cantidad: " + cantidad9 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id10, nombreProducto10, "Precio: " + precio10 + " €", "Cantidad: " + cantidad10 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id11, nombreProducto11, "Precio: " + precio11 + " €", "Cantidad: " + cantidad11 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id13 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id5, nombreProducto5, "Precio: " + precio5 + " €", "Cantidad: " + cantidad5 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id6, nombreProducto6, "Precio: " + precio6 + " €", "Cantidad: " + cantidad6 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id7, nombreProducto7, "Precio: " + precio7 + " €", "Cantidad: " + cantidad7 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id8, nombreProducto8, "Precio: " + precio8 + " €", "Cantidad: " + cantidad8 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id9, nombreProducto9, "Precio: " + precio9 + " €", "Cantidad: " + cantidad9 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id10, nombreProducto10, "Precio: " + precio10 + " €", "Cantidad: " + cantidad10 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id11, nombreProducto11, "Precio: " + precio11 + " €", "Cantidad: " + cantidad11 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id12, nombreProducto12, "Precio: " + precio12 + " €", "Cantidad: " + cantidad12 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id14 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id5, nombreProducto5, "Precio: " + precio5 + " €", "Cantidad: " + cantidad5 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id6, nombreProducto6, "Precio: " + precio6 + " €", "Cantidad: " + cantidad6 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id7, nombreProducto7, "Precio: " + precio7 + " €", "Cantidad: " + cantidad7 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id8, nombreProducto8, "Precio: " + precio8 + " €", "Cantidad: " + cantidad8 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id9, nombreProducto9, "Precio: " + precio9 + " €", "Cantidad: " + cantidad9 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id10, nombreProducto10, "Precio: " + precio10 + " €", "Cantidad: " + cantidad10 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id11, nombreProducto11, "Precio: " + precio11 + " €", "Cantidad: " + cantidad11 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id12, nombreProducto12, "Precio: " + precio12 + " €", "Cantidad: " + cantidad12 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id13, nombreProducto13, "Precio: " + precio13 + " €", "Cantidad: " + cantidad13 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id15 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id5, nombreProducto5, "Precio: " + precio5 + " €", "Cantidad: " + cantidad5 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id6, nombreProducto6, "Precio: " + precio6 + " €", "Cantidad: " + cantidad6 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id7, nombreProducto7, "Precio: " + precio7 + " €", "Cantidad: " + cantidad7 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id8, nombreProducto8, "Precio: " + precio8 + " €", "Cantidad: " + cantidad8 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id9, nombreProducto9, "Precio: " + precio9 + " €", "Cantidad: " + cantidad9 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id10, nombreProducto10, "Precio: " + precio10 + " €", "Cantidad: " + cantidad10 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id11, nombreProducto11, "Precio: " + precio11 + " €", "Cantidad: " + cantidad11 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id12, nombreProducto12, "Precio: " + precio12 + " €", "Cantidad: " + cantidad12 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id13, nombreProducto13, "Precio: " + precio13 + " €", "Cantidad: " + cantidad13 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id14, nombreProducto14, "Precio: " + precio14 + " €", "Cantidad: " + cantidad14 + " producto/os", R.drawable.ic_launcher_background));
        } else if (id16 == null) {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id5, nombreProducto5, "Precio: " + precio5 + " €", "Cantidad: " + cantidad5 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id6, nombreProducto6, "Precio: " + precio6 + " €", "Cantidad: " + cantidad6 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id7, nombreProducto7, "Precio: " + precio7 + " €", "Cantidad: " + cantidad7 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id8, nombreProducto8, "Precio: " + precio8 + " €", "Cantidad: " + cantidad8 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id9, nombreProducto9, "Precio: " + precio9 + " €", "Cantidad: " + cantidad9 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id10, nombreProducto10, "Precio: " + precio10 + " €", "Cantidad: " + cantidad10 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id11, nombreProducto11, "Precio: " + precio11 + " €", "Cantidad: " + cantidad11 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id12, nombreProducto12, "Precio: " + precio12 + " €", "Cantidad: " + cantidad12 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id13, nombreProducto13, "Precio: " + precio13 + " €", "Cantidad: " + cantidad13 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id14, nombreProducto14, "Precio: " + precio14 + " €", "Cantidad: " + cantidad14 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id15, nombreProducto15, "Precio: " + precio15 + " €", "Cantidad: " + cantidad15 + " producto/os", R.drawable.ic_launcher_background));
        } else {
            listaProductosCesta.add(new ProductoCesta("Id: " + id1, nombreProducto1, "Precio: " + precio1 + " €", "Cantidad: " + cantidad1 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id2, nombreProducto2, "Precio: " + precio2 + " €", "Cantidad: " + cantidad2 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id3, nombreProducto3, "Precio: " + precio3 + " €", "Cantidad: " + cantidad3 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id4, nombreProducto4, "Precio: " + precio4 + " €", "Cantidad: " + cantidad4 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id5, nombreProducto5, "Precio: " + precio5 + " €", "Cantidad: " + cantidad5 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id6, nombreProducto6, "Precio: " + precio6 + " €", "Cantidad: " + cantidad6 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id7, nombreProducto7, "Precio: " + precio7 + " €", "Cantidad: " + cantidad7 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id8, nombreProducto8, "Precio: " + precio8 + " €", "Cantidad: " + cantidad8 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id9, nombreProducto9, "Precio: " + precio9 + " €", "Cantidad: " + cantidad9 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id10, nombreProducto10, "Precio: " + precio10 + " €", "Cantidad: " + cantidad10 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id11, nombreProducto11, "Precio: " + precio11 + " €", "Cantidad: " + cantidad11 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id12, nombreProducto12, "Precio: " + precio12 + " €", "Cantidad: " + cantidad12 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id13, nombreProducto13, "Precio: " + precio13 + " €", "Cantidad: " + cantidad13 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id14, nombreProducto14, "Precio: " + precio14 + " €", "Cantidad: " + cantidad14 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id15, nombreProducto15, "Precio: " + precio15 + " €", "Cantidad: " + cantidad15 + " producto/os", R.drawable.ic_launcher_background));
            listaProductosCesta.add(new ProductoCesta("Id: " + id16, nombreProducto16, "Precio: " + precio16 + " €", "Cantidad: " + cantidad16 + " producto/os", R.drawable.ic_launcher_background));

        }
    }

    public void mostrarDatosCesta() {
        recyclerProductosCesta.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterProductosCesta = new AdapterRecyclerCesta(getContext(), listaProductosCesta);
        recyclerProductosCesta.setAdapter(adapterProductosCesta);
    }

    public void obtenerDatosDeBBDD(final String url) {
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        if (url.equalsIgnoreCase(URL)) {
                            id1 = jsonObject.getString("id");
                            nombreProducto1 = jsonObject.getString("nombre_producto");
                            precio1 = jsonObject.getString("precio");
                            cantidad1 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL2)) {
                            id2 = jsonObject.getString("id");
                            nombreProducto2 = jsonObject.getString("nombre_producto");
                            precio2 = jsonObject.getString("precio");
                            cantidad2 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL3)) {
                            id3 = jsonObject.getString("id");
                            nombreProducto3 = jsonObject.getString("nombre_producto");
                            precio3 = jsonObject.getString("precio");
                            cantidad3 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL4)) {
                            id4 = jsonObject.getString("id");
                            nombreProducto4 = jsonObject.getString("nombre_producto");
                            precio4 = jsonObject.getString("precio");
                            cantidad4 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL5)) {
                            id5 = jsonObject.getString("id");
                            nombreProducto5 = jsonObject.getString("nombre_producto");
                            precio5 = jsonObject.getString("precio");
                            cantidad5 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL6)) {
                            id6 = jsonObject.getString("id");
                            nombreProducto6 = jsonObject.getString("nombre_producto");
                            precio6 = jsonObject.getString("precio");
                            cantidad6 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL7)) {
                            id7 = jsonObject.getString("id");
                            nombreProducto7 = jsonObject.getString("nombre_producto");
                            precio7 = jsonObject.getString("precio");
                            cantidad7 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL8)) {
                            id8 = jsonObject.getString("id");
                            nombreProducto8 = jsonObject.getString("nombre_producto");
                            precio8 = jsonObject.getString("precio");
                            cantidad8 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL9)) {
                            id9 = jsonObject.getString("id");
                            nombreProducto9 = jsonObject.getString("nombre_producto");
                            precio9 = jsonObject.getString("precio");
                            cantidad9 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL10)) {
                            id10 = jsonObject.getString("id");
                            nombreProducto10 = jsonObject.getString("nombre_producto");
                            precio10 = jsonObject.getString("precio");
                            cantidad10 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL11)) {
                            id11 = jsonObject.getString("id");
                            nombreProducto11 = jsonObject.getString("nombre_producto");
                            precio11 = jsonObject.getString("precio");
                            cantidad11 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL12)) {
                            id12 = jsonObject.getString("id");
                            nombreProducto12 = jsonObject.getString("nombre_producto");
                            precio12 = jsonObject.getString("precio");
                            cantidad12 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL13)) {
                            id13 = jsonObject.getString("id");
                            nombreProducto13 = jsonObject.getString("nombre_producto");
                            precio13 = jsonObject.getString("precio");
                            cantidad13 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL14)) {
                            id14 = jsonObject.getString("id");
                            nombreProducto14 = jsonObject.getString("nombre_producto");
                            precio14 = jsonObject.getString("precio");
                            cantidad14 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL15)) {
                            id15 = jsonObject.getString("id");
                            nombreProducto15 = jsonObject.getString("nombre_producto");
                            precio15 = jsonObject.getString("precio");
                            cantidad15 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL16)) {
                            id16 = jsonObject.getString("id");
                            nombreProducto16 = jsonObject.getString("nombre_producto");
                            precio16 = jsonObject.getString("precio");
                            cantidad16 = jsonObject.getString("cantidad");
                        } else if (url.equalsIgnoreCase(URL17)) {
                            numeroProductosCesta = jsonObject.getString("0");
                            numProdC(numeroProductosCesta);
                        }
                        else if (url.equalsIgnoreCase(URLprecioTotalCesta)){
                            sumaPrecioCesta = jsonObject.getString("0");
                            precioTotal(sumaPrecioCesta);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), "ERROR DE CONEXIÓN: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonArrayRequest);
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

            cargarListaProductosCesta();
            mostrarDatosCesta();

            Toast.makeText(getActivity(), "DATOS CARGADOS", Toast.LENGTH_SHORT).show();
        }
    }

    public void direccionesURL() {
        URL = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=1";
        URL2 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=2";
        URL3 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=3";
        URL4 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=4";
        URL5 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=5";
        URL6 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=6";
        URL7 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=7";
        URL8 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=8";
        URL9 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=9";
        URL10 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=10";
        URL11 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=11";
        URL12 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=12";
        URL13 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=13";
        URL14 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=14";
        URL15 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=15";
        URL16 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/buscar_producto_cesta.php?id=16";
        URL17 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/pruebaNumRegistros.php";
        URLeliminarTodo = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/eliminar_todos_productos_Cesta.php";
        URLprecioTotalCesta = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_precio_cesta2.php";
    }

    public void obtenerDatos() {
        obtenerDatosDeBBDD(URL17);
        obtenerDatosDeBBDD(URLprecioTotalCesta);
        obtenerDatosDeBBDD(URL);
        obtenerDatosDeBBDD(URL2);
        obtenerDatosDeBBDD(URL3);
        obtenerDatosDeBBDD(URL4);
        obtenerDatosDeBBDD(URL5);
        obtenerDatosDeBBDD(URL6);
        obtenerDatosDeBBDD(URL7);
        obtenerDatosDeBBDD(URL8);
        obtenerDatosDeBBDD(URL9);
        obtenerDatosDeBBDD(URL10);
        obtenerDatosDeBBDD(URL11);
        obtenerDatosDeBBDD(URL12);
        obtenerDatosDeBBDD(URL13);
        obtenerDatosDeBBDD(URL14);
        obtenerDatosDeBBDD(URL15);
        obtenerDatosDeBBDD(URL16);
    }
}
