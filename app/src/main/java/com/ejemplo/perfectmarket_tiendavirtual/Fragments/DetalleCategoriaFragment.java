package com.ejemplo.perfectmarket_tiendavirtual.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.Producto;
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
    RecyclerView recyclerProducto1;
    ArrayList<ProductosDetalleCategoria> listaCategorias1;
    AdapterRecyclerDetalleCategoria adapterRecyclerDetalleCategoria;
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;
    FloatingActionButton fabRetroceder, fabPrincipal, fabCesta;
    String opcion, nombreProducto1 = "", descripcion1, otrosDatos1, precio1, URLProductosCategoria;
    RequestQueue requestQueue;
    LinearLayout linearFabdetprodcat;
    FragmentTransaction fragmentTransaction;
    FragmentManager fm;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle_producto_categoria, container, false);

        fm = getActivity().getSupportFragmentManager();
        fabRetroceder = view.findViewById(R.id.fabDRetrocederCat);
        fabPrincipal = view.findViewById(R.id.fabDPrincipalCat);
        fabCesta = view.findViewById(R.id.fabCestaDCat);
        linearFabdetprodcat = view.findViewById(R.id.linearFabdetprodcat);
        URLProductosCategoria = "https://perfectmarket.000webhostapp.com/perfect_market/buscarProductosDetalleCategorias.php";
        recyclerProducto1 = view.findViewById(R.id.recyclerProductosCategoria1);
        listaCategorias1 = new ArrayList<>();
        nombre_detalle = view.findViewById(R.id.nombre_categoria1);
        dato_detalle = view.findViewById(R.id.dato_adicional1);
        imagenDetalle = view.findViewById(R.id.imagenCategoria1);
        requestQueue = Volley.newRequestQueue(getContext());


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
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        fabPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "" + fm.getBackStackEntryCount(), Toast.LENGTH_SHORT).show();
                if (fm.getBackStackEntryCount() == 1) {
                    fm.popBackStack();
                } else {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });


        //Crear objeto bundle para recibir el objeto envado por argumentos
        Bundle objetoCategoria = getArguments();
        Producto producto = null;

        //validacion para verificar si existen argumentos enviados para mostrar
        if (objetoCategoria != null) {
            producto = (Producto) objetoCategoria.getSerializable("categoria");
            opcion = producto.getNombre();

            //Establecer los datos en la vista
            nombre_detalle.setText(producto.getNombre());
            dato_detalle.setText(producto.getInformacion());
            imagenDetalle.setImageResource(producto.getImagenId());
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (opcion.equalsIgnoreCase("electronica e informatica")) {

                    for (int i = 1; i < 17; i++) {
                        obtenerDatosProductosCategoria1(URLProductosCategoria + "?id=" + String.valueOf(i)
                                + "&categoria_producto=" + opcion);
                    }
                } else if (opcion.equalsIgnoreCase("moda")) {

                    for (int i = 1; i < 17; i++) {
                        obtenerDatosProductosCategoria2(URLProductosCategoria + "?id=" + String.valueOf(i)
                                + "&categoria_producto=" + opcion);
                    }
                } else if (opcion.equalsIgnoreCase("comida")) {

                    for (int i = 1; i < 17; i++) {
                        obtenerDatosProductosCategoria3(URLProductosCategoria + "?id=" + String.valueOf(i)
                                + "&categoria_producto=" + opcion);
                    }
                } else if (opcion.equalsIgnoreCase("libros")) {

                    for (int i = 1; i < 17; i++) {
                        obtenerDatosProductosCategoria4(URLProductosCategoria + "?id=" + String.valueOf(i)
                                + "&categoria_producto=" + opcion);
                    }
                } else if (opcion.equalsIgnoreCase("videojuegos")) {

                    for (int i = 1; i < 17; i++) {
                        obtenerDatosProductosCategoria5(URLProductosCategoria + "?id=" + String.valueOf(i)
                                + "&categoria_producto=" + opcion);
                    }
                } else if (opcion.equalsIgnoreCase("música")) {

                    for (int i = 1; i < 17; i++) {
                        obtenerDatosProductosCategoria6(URLProductosCategoria + "?id=" + String.valueOf(i)
                                + "&categoria_producto=" + opcion);
                    }
                } else if (opcion.equalsIgnoreCase("deporte")) {

                    for (int i = 1; i < 17; i++) {
                        obtenerDatosProductosCategoria7(URLProductosCategoria + "?id=" + String.valueOf(i)
                                + "&categoria_producto=" + opcion);
                    }
                } else if (opcion.equalsIgnoreCase("apps móviles")) {

                    for (int i = 1; i < 17; i++) {
                        obtenerDatosProductosCategoria8(URLProductosCategoria + "?id=" + String.valueOf(i)
                                + "&categoria_producto=" + opcion);
                    }
                }
            }
        }, 500);

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


    public void obtenerDatosProductosCategoria1(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        nombreProducto1 = jsonObject.getString("nombre_producto");
                        precio1 = jsonObject.getString("precio_producto");
                        descripcion1 = jsonObject.getString("descripcion_producto");
                        otrosDatos1 = jsonObject.getString("otros_datos_producto");
                        if (id.equalsIgnoreCase("1")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img1, descripcion1, otrosDatos1));
                        } else if (id.equalsIgnoreCase("2")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img2, descripcion1, otrosDatos1));
                        } else if (id.equalsIgnoreCase("3")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img3, descripcion1, otrosDatos1));
                        } else if (id.equalsIgnoreCase("4")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img4, descripcion1, otrosDatos1));
                        }
                        //Toast.makeText(actividad, "cat: " + opcion + " id: " + i, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProducto1.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias1);
                    recyclerProducto1.setAdapter(adapterRecyclerDetalleCategoria);
                    adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nombre = listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)).getNombre();
                            //Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.enviarProducto2(listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)));
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

    public void obtenerDatosProductosCategoria2(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        nombreProducto1 = jsonObject.getString("nombre_producto");
                        precio1 = jsonObject.getString("precio_producto");
                        descripcion1 = jsonObject.getString("descripcion_producto");
                        otrosDatos1 = jsonObject.getString("otros_datos_producto");
                        if (id.equalsIgnoreCase("5")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img5, descripcion1, otrosDatos1));
                        } else if (id.equalsIgnoreCase("6")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img6, descripcion1, otrosDatos1));
                        } else if (id.equalsIgnoreCase("7")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img7, descripcion1, otrosDatos1));
                        } else if (id.equalsIgnoreCase("8")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img8, descripcion1, otrosDatos1));
                        }
                        //Toast.makeText(actividad, "cat: " + opcion + " id: " + i, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProducto1.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias1);
                    recyclerProducto1.setAdapter(adapterRecyclerDetalleCategoria);
                    adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nombre = listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)).getNombre();
                            //Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.enviarProducto2(listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)));
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

    public void obtenerDatosProductosCategoria3(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        nombreProducto1 = jsonObject.getString("nombre_producto");
                        precio1 = jsonObject.getString("precio_producto");
                        descripcion1 = jsonObject.getString("descripcion_producto");
                        otrosDatos1 = jsonObject.getString("otros_datos_producto");
                        if (id.equalsIgnoreCase("9")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img9, descripcion1, otrosDatos1));
                        } else if (id.equalsIgnoreCase("10")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img10, descripcion1, otrosDatos1));
                        }
                        //Toast.makeText(actividad, "cat: " + opcion + " id: " + i, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProducto1.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias1);
                    recyclerProducto1.setAdapter(adapterRecyclerDetalleCategoria);
                    adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nombre = listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)).getNombre();
                            //Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.enviarProducto2(listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)));
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

    public void obtenerDatosProductosCategoria4(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        nombreProducto1 = jsonObject.getString("nombre_producto");
                        precio1 = jsonObject.getString("precio_producto");
                        descripcion1 = jsonObject.getString("descripcion_producto");
                        otrosDatos1 = jsonObject.getString("otros_datos_producto");
                        if (id.equalsIgnoreCase("11")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img11, descripcion1, otrosDatos1));
                        }
                        //Toast.makeText(actividad, "cat: " + opcion + " id: " + i, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProducto1.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias1);
                    recyclerProducto1.setAdapter(adapterRecyclerDetalleCategoria);
                    adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nombre = listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)).getNombre();
                            //Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.enviarProducto2(listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)));
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

    public void obtenerDatosProductosCategoria5(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        nombreProducto1 = jsonObject.getString("nombre_producto");
                        precio1 = jsonObject.getString("precio_producto");
                        descripcion1 = jsonObject.getString("descripcion_producto");
                        otrosDatos1 = jsonObject.getString("otros_datos_producto");
                        if (id.equalsIgnoreCase("12")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img12, descripcion1, otrosDatos1));
                        }
                        //Toast.makeText(actividad, "cat: " + opcion + " id: " + i, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProducto1.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias1);
                    recyclerProducto1.setAdapter(adapterRecyclerDetalleCategoria);
                    adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nombre = listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)).getNombre();
                            //Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.enviarProducto2(listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)));
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

    public void obtenerDatosProductosCategoria6(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        nombreProducto1 = jsonObject.getString("nombre_producto");
                        precio1 = jsonObject.getString("precio_producto");
                        descripcion1 = jsonObject.getString("descripcion_producto");
                        otrosDatos1 = jsonObject.getString("otros_datos_producto");
                        if (id.equalsIgnoreCase("13")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img13, descripcion1, otrosDatos1));
                        } else if (id.equalsIgnoreCase("14")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img14, descripcion1, otrosDatos1));
                        }
                        //Toast.makeText(actividad, "cat: " + opcion + " id: " + i, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProducto1.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias1);
                    recyclerProducto1.setAdapter(adapterRecyclerDetalleCategoria);
                    adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nombre = listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)).getNombre();
                            //Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.enviarProducto2(listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)));
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

    public void obtenerDatosProductosCategoria7(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        nombreProducto1 = jsonObject.getString("nombre_producto");
                        precio1 = jsonObject.getString("precio_producto");
                        descripcion1 = jsonObject.getString("descripcion_producto");
                        otrosDatos1 = jsonObject.getString("otros_datos_producto");
                        if (id.equalsIgnoreCase("15")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img15, descripcion1, otrosDatos1));
                        }
                        //Toast.makeText(actividad, "cat: " + opcion + " id: " + i, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProducto1.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias1);
                    recyclerProducto1.setAdapter(adapterRecyclerDetalleCategoria);
                    adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nombre = listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)).getNombre();
                            //Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.enviarProducto2(listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)));
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

    public void obtenerDatosProductosCategoria8(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        nombreProducto1 = jsonObject.getString("nombre_producto");
                        precio1 = jsonObject.getString("precio_producto");
                        descripcion1 = jsonObject.getString("descripcion_producto");
                        otrosDatos1 = jsonObject.getString("otros_datos_producto");
                        if (id.equalsIgnoreCase("16")) {
                            listaCategorias1.add(new ProductosDetalleCategoria(nombreProducto1, precio1 + " €", R.drawable.img16, descripcion1, otrosDatos1));
                        }
                        //Toast.makeText(actividad, "cat: " + opcion + " id: " + i, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                try {
                    recyclerProducto1.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterRecyclerDetalleCategoria = new AdapterRecyclerDetalleCategoria(getContext(), listaCategorias1);
                    recyclerProducto1.setAdapter(adapterRecyclerDetalleCategoria);
                    adapterRecyclerDetalleCategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String nombre = listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)).getNombre();
                            //Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
                            interfaceComunicaFragments.enviarProducto2(listaCategorias1.get(recyclerProducto1.getChildAdapterPosition(view)));
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
