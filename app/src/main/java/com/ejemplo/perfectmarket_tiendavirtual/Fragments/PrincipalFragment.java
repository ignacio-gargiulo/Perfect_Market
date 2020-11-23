package com.ejemplo.perfectmarket_tiendavirtual.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.Producto;
import com.ejemplo.perfectmarket_tiendavirtual.R;
import com.ejemplo.perfectmarket_tiendavirtual.iComunicaFragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PrincipalFragment extends Fragment {

    Button btnCat, btncesta, btnZonaUsuario;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    TextView txtNomUserPrincipal;
    RecyclerView recyclerProducto1, recyclerProducto2, recyclerProducto3, recyclerProducto4;
    AdapterRecycler2 adapterCategorias;
    ArrayList<Producto> listaProductos1, listaProductos2, listaProductos3, listaProductos4;
    String URL, nombreProducto = "", descripcion, otrosDatos, precio;
    RequestQueue requestQueue;
    ViewFlipper viewFlipper;
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;
    View view;
    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        preferences = getContext().getSharedPreferences("preferenciasDU", Context.MODE_PRIVATE);
        view = inflater.inflate(R.layout.fragment_principal, container, false);
        btnZonaUsuario = view.findViewById(R.id.btnZonaUsuario);
        txtNomUserPrincipal = view.findViewById(R.id.txtNomUserPrincipal);
        btnCat = view.findViewById(R.id.btnIrCategorias);
        btncesta = view.findViewById(R.id.btnIrCesta);
        recyclerProducto1 = view.findViewById(R.id.recyclerPrincipal1);
        recyclerProducto2 = view.findViewById(R.id.recyclerPrincipal2);
        recyclerProducto3 = view.findViewById(R.id.recyclerPrincipal3);
        recyclerProducto4 = view.findViewById(R.id.recyclerPrincipal4);
        listaProductos1 = new ArrayList<>();
        listaProductos2 = new ArrayList<>();
        listaProductos3 = new ArrayList<>();
        listaProductos4 = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        URL = "https://perfectmarket.000webhostapp.com/perfect_market/buscarProductosPrincipal.php?id=";
        int imagenes[] = {R.drawable.pm_banner, R.drawable.img3, R.drawable.img1, R.drawable.img2};
        viewFlipper = view.findViewById(R.id.viewFlipper);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setInAnimation(getContext(), R.anim.slide_in_right);
        viewFlipper.setOutAnimation(getContext(), R.anim.slide_out_left);


        txtNomUserPrincipal.setText(preferences.getString("nombre", "Desconocido") +
                " " + preferences.getString("apellido", ""));

        // en bucle
        for (int i = 0; i < imagenes.length; i++) {
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

        btnZonaUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new ZonaUsuarioFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        for (int i = 1; i < 5; i++) {
            obtenerDatosProductos1(URL + String.valueOf(i));
        }

        for (int i = 5; i < 9; i++) {
            obtenerDatosProductos2(URL + String.valueOf(i));
        }

        for (int i = 9; i < 13; i++) {
            obtenerDatosProductos3(URL + String.valueOf(i));
        }

        for (int i = 13; i < 17; i++) {
            obtenerDatosProductos4(URL + String.valueOf(i));
        }




        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad = (Activity) context;
            interfaceComunicaFragments = (iComunicaFragments) this.actividad;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void flipperImagenes(int imagen) {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(imagen);
        viewFlipper.addView(imageView);
    }

    private void obtenerDatosProductos1(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        nombreProducto = jsonObject.getString("nombre_producto");
                        precio = jsonObject.getString("precio_producto");
                        descripcion = jsonObject.getString("descripcion_producto");
                        otrosDatos = jsonObject.getString("otros_datos_producto");
                        if (id.equalsIgnoreCase("1")) {
                            listaProductos1.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img1));
                        } else if (id.equalsIgnoreCase("2")) {
                            listaProductos1.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img2));
                        } else if (id.equalsIgnoreCase("3")) {
                            listaProductos1.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img3));
                        } else if (id.equalsIgnoreCase("4")) {
                            listaProductos1.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img4));
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProducto1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    adapterCategorias = new AdapterRecycler2(getContext(), listaProductos1);
                    recyclerProducto1.setAdapter(adapterCategorias);
                    adapterCategorias.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nombre = listaProductos1.get(recyclerProducto1.getChildAdapterPosition(view)).getNombre();
                            // Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.enviarProducto(listaProductos1.get(recyclerProducto1.getChildAdapterPosition(view)));
                        }
                    });
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

    private void obtenerDatosProductos2(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        nombreProducto = jsonObject.getString("nombre_producto");
                        precio = jsonObject.getString("precio_producto");
                        descripcion = jsonObject.getString("descripcion_producto");
                        otrosDatos = jsonObject.getString("otros_datos_producto");
                        if (id.equalsIgnoreCase("5")) {
                            listaProductos2.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img5));
                        } else if (id.equalsIgnoreCase("6")) {
                            listaProductos2.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img6));
                        } else if (id.equalsIgnoreCase("7")) {
                            listaProductos2.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img7));
                        } else if (id.equalsIgnoreCase("8")) {
                            listaProductos2.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img8));
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProducto2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    adapterCategorias = new AdapterRecycler2(getContext(), listaProductos2);
                    recyclerProducto2.setAdapter(adapterCategorias);
                    adapterCategorias.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nombre = listaProductos2.get(recyclerProducto2.getChildAdapterPosition(view)).getNombre();
                            //Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.enviarProducto(listaProductos2.get(recyclerProducto2.getChildAdapterPosition(view)));
                        }
                    });
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

    private void obtenerDatosProductos3(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        nombreProducto = jsonObject.getString("nombre_producto");
                        precio = jsonObject.getString("precio_producto");
                        descripcion = jsonObject.getString("descripcion_producto");
                        otrosDatos = jsonObject.getString("otros_datos_producto");
                        if (id.equalsIgnoreCase("9")) {
                            listaProductos3.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img9));
                        } else if (id.equalsIgnoreCase("10")) {
                            listaProductos3.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img10));
                        } else if (id.equalsIgnoreCase("11")) {
                            listaProductos3.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img11));
                        } else if (id.equalsIgnoreCase("12")) {
                            listaProductos3.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img12));
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProducto3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    adapterCategorias = new AdapterRecycler2(getContext(), listaProductos3);
                    recyclerProducto3.setAdapter(adapterCategorias);
                    adapterCategorias.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nombre = listaProductos3.get(recyclerProducto3.getChildAdapterPosition(view)).getNombre();
                            // Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.enviarProducto(listaProductos3.get(recyclerProducto3.getChildAdapterPosition(view)));
                        }
                    });
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

    private void obtenerDatosProductos4(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        nombreProducto = jsonObject.getString("nombre_producto");
                        precio = jsonObject.getString("precio_producto");
                        descripcion = jsonObject.getString("descripcion_producto");
                        otrosDatos = jsonObject.getString("otros_datos_producto");
                        if (id.equalsIgnoreCase("13")) {
                            listaProductos4.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img13));
                        } else if (id.equalsIgnoreCase("14")) {
                            listaProductos4.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img14));
                        } else if (id.equalsIgnoreCase("15")) {
                            listaProductos4.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img15));
                        } else if (id.equalsIgnoreCase("16")) {
                            listaProductos4.add(new Producto(nombreProducto, precio + " €", descripcion, otrosDatos, R.drawable.img16));
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProducto4.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    adapterCategorias = new AdapterRecycler2(getContext(), listaProductos4);
                    recyclerProducto4.setAdapter(adapterCategorias);
                    adapterCategorias.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nombre = listaProductos4.get(recyclerProducto4.getChildAdapterPosition(view)).getNombre();
                            // Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.enviarProducto(listaProductos4.get(recyclerProducto4.getChildAdapterPosition(view)));
                        }
                    });
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
}
