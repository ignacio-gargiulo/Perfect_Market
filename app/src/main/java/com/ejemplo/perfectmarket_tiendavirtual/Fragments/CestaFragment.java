package com.ejemplo.perfectmarket_tiendavirtual.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.ejemplo.perfectmarket_tiendavirtual.Adaptadores.AdapterRecyclerCesta;
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

    private TextView txtCestaVacia, txtPrecioTotal, txtNumProdCesta, txtNomUserCesta;
    private LinearLayout linearCesta, linearFab, linearCVCesta;
    private ImageView imgCestaSeparador;
    private String nomProd, precioP, cantidadComprada, URLObtenerDatosCesta, URLSumaPreciosCesta,
            precioTotalCesta, URLNumProductosCesta, URLEliminarProdCestaUsuario,
            numProdCesta;
    private RequestQueue requestQueue;
    private FloatingActionButton fabPrincipal, fabRetroceder;
    private ArrayList<ProductoCesta> listaProductosCesta;
    private RecyclerView recyclerProductosCesta;
    private AdapterRecyclerCesta adapterProductosCesta;
    private FragmentManager fm;
    private Button btnComprar, btnEliminar;
    private SharedPreferences preferences;
    private int cntP = 0;
    private int cntP2 = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cesta, container, false);
        fm = getActivity().getSupportFragmentManager();
        requestQueue = Volley.newRequestQueue(getContext());
        URLEliminarProdCestaUsuario = "https://perfectmarket.000webhostapp.com/perfect_market/eliminar_todos_productos_Cesta.php";
        preferences = getContext().getSharedPreferences("preferenciasDU", Context.MODE_PRIVATE);
        URLSumaPreciosCesta = "https://perfectmarket.000webhostapp.com/perfect_market/sumaPreciosTotales.php?id_usuario=";
        URLObtenerDatosCesta = "https://perfectmarket.000webhostapp.com/perfect_market/obtenerProductosCestaUsuario.php";
        URLNumProductosCesta = "https://perfectmarket.000webhostapp.com/perfect_market/numProductosCesta.php?id_usuario=";
        linearFab = view.findViewById(R.id.linearFab);
        linearCVCesta = view.findViewById(R.id.linearCVCesta);
        imgCestaSeparador = view.findViewById(R.id.imgCestaSeparador);
        fabPrincipal = view.findViewById(R.id.fabPrincipalCesta);
        fabRetroceder = view.findViewById(R.id.fabRetrocederCesta);
        linearCesta = view.findViewById(R.id.linearCesta);
        txtCestaVacia = view.findViewById(R.id.txtCestaVacia);
        txtPrecioTotal = view.findViewById(R.id.txtPrecioTotal);
        txtNumProdCesta = view.findViewById(R.id.txtNumTotalProductos);
        txtNomUserCesta = view.findViewById(R.id.txtNomUserCesta);
        listaProductosCesta = new ArrayList<>();
        recyclerProductosCesta = view.findViewById(R.id.recyclerProductosCesta);

        btnComprar = view.findViewById(R.id.btnComprarProductosCesta);
        btnEliminar = view.findViewById(R.id.btnEliminarProductosCesta);

        txtCestaVacia.setVisibility(View.INVISIBLE);
        recyclerProductosCesta.setVisibility(View.VISIBLE);

        txtNomUserCesta.setText(preferences.getString("nombre", "Desconocido") +
                " " + preferences.getString("apellido", ""));

        mostrarDatos();
        obtenerSumaPreciosCesta(URLSumaPreciosCesta);
        obtenerNumProductosCesta(URLNumProductosCesta);

        fabRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStack();
            }
        });

        fabPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fm.getBackStackEntryCount() == 1) {
                    fm.popBackStack();
                } else {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final int numPC = Integer.valueOf(numProdCesta);
                if (numPC >= 1 & cntP2 != 1 & cntP != 1) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setMessage("¿Seguro que quiere comprar todos los productos (" + numPC + ") de la cesta por un total de " + precioTotalCesta + "€?").setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Snackbar.make(view, "Se han comprado todos los productos (" + numPC + ") de la cesta por " + precioTotalCesta + "€", Snackbar.LENGTH_LONG)
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
                                                    eliminarProductos(URLEliminarProdCestaUsuario);
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
                } else {
                    Snackbar.make(view, "No hay productos en la cesta que comprar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final int numPC = Integer.valueOf(numProdCesta);
                if (numPC >= 1 & cntP != 1 & cntP2 != 1) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setMessage("¿Seguro que quiere eliminar todos los productos (" + numPC + ") de la cesta?").setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    cntP = 1;
                                    eliminarProductos(URLEliminarProdCestaUsuario);
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

                } else {
                    Snackbar.make(view, "No hay productos en la cesta que eliminar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        return view;
    }

    private void eliminarProductos(String URL) {
        txtNumProdCesta.setText("0 Productos");
        txtPrecioTotal.setText("Precio total: 0€");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Los productos fueron eliminados", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id_usuario", preferences.getString("id", "0"));
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void mostrarDatos() {
        try {
            for (int i = 1; i < 17; i++) {
                obtenerProductosCesta(URLObtenerDatosCesta, preferences.getString("id", "0"), String.valueOf(i));
            }
        } catch (Exception e) {
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
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProductosCesta.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterProductosCesta = new AdapterRecyclerCesta(getContext(), listaProductosCesta);
                    recyclerProductosCesta.setAdapter(adapterProductosCesta);
                } catch (Exception e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void obtenerSumaPreciosCesta(String URL) {
        URL = URL + preferences.getString("id", "0");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        precioTotalCesta = jsonObject.getString("0");

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (precioTotalCesta.isEmpty()){
                    txtPrecioTotal.setText("Precio Total: 0€");
                }
                else {
                    txtPrecioTotal.setText("Precio Total: " + precioTotalCesta + "€");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void obtenerNumProductosCesta(String URL) {
        URL = URL + preferences.getString("id", "0");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        numProdCesta = jsonObject.getString("0");
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if (numProdCesta.equalsIgnoreCase("0")) {
                    txtCestaVacia.setVisibility(View.VISIBLE);
                }
                txtNumProdCesta.setText(numProdCesta + " Productos");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
