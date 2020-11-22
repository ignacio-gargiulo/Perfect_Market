package com.ejemplo.perfectmarket_tiendavirtual.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.ejemplo.perfectmarket_tiendavirtual.Adaptadores.AdapterRecyclerComentarios;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.Categoria;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.ComentariosProductos;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.ProductosDetalleCategoria;
import com.ejemplo.perfectmarket_tiendavirtual.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetalleProductoFragment extends Fragment {

    private FragmentManager fm;
    private FragmentTransaction fragmentTransaction;
    private RequestQueue requestQueue;
    private RatingBar ratingBar;
    private ImageView imageView;
    private TextView txtComUser, txtIdProducto, nombre, informacion, otrosDatos, precio, txtValoracion, precioTotal,
            txtNumProductosEnCesta, txtNumCom, txtValGen, txtNomUser;
    private String URLObtenerComentarios, opcion, numComentarios, idProducto, URLinsertarProductoEnCesta,
            URLNumCom, URLDatosUsuario, URLidProducto, URLinsertarComentarios, comentario1, valoracion1,
            valoracion2, numeroTotalOpiniones, numProdCestaUsuario, usuario1, URLSumaValoracionP,
            URLvalidarComentarios, URLValidarProductosCesta, URLNumTOpiniones, URLNumProductosCesta;
    private EditText edtComentario, edtCantidad;
    private Button btnEnviarDatos, btnAñadirCesta, btnSumarCantidad, btnRestarCantidad,
            btnComprarYa, btnActualizarComentarios;
    private FloatingActionButton fab, fabCompartir, fabRetroceder, fabPrincipal;
    private ArrayList<ComentariosProductos> listaComentarios, listaComentarioUsuario;
    private AdapterRecyclerComentarios adapterRecyclerComentarios;
    private ViewFlipper viewFlipperP;
    private DecimalFormat decimalFormat;
    private ImageButton btnSiguienteP, btnAnteriorP;
    private RecyclerView recyclerViewComentarios, recyclerViewComentarioUsuario;
    private View view;
    private SharedPreferences preferences;
    private int num, imagenId;
    private int cantidad = 0;
    private double precioT;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        direccionesURL();
        view = inflater.inflate(R.layout.fragment_detalle_producto, container, false);
        edtComentario = view.findViewById(R.id.edtComentario);
        txtComUser = view.findViewById(R.id.txtComUser);
        txtIdProducto = view.findViewById(R.id.txtIdProducto);
        txtValGen = view.findViewById(R.id.txtValProducto);
        btnSiguienteP = view.findViewById(R.id.btnImagenSiguienteProductos);
        btnAnteriorP = view.findViewById(R.id.btnImagenAnteriorProductos);
        viewFlipperP = view.findViewById(R.id.viewFlipperProductos);
        requestQueue = Volley.newRequestQueue(getContext());
        fm = getActivity().getSupportFragmentManager();
        nombre = view.findViewById(R.id.nombre_producto_detalle);
        informacion = view.findViewById(R.id.dato_adicional_detalle2);
        otrosDatos = view.findViewById(R.id.dato_adicional_detalle4);
        precio = view.findViewById(R.id.precio_detalle_producto);
        txtValoracion = view.findViewById(R.id.valoracion_detalle2);
        ratingBar = view.findViewById(R.id.ratingBar);
        btnEnviarDatos = view.findViewById(R.id.btnEnviarDatos);
        btnAñadirCesta = view.findViewById(R.id.btnAñadirCesta);
        txtNomUser = view.findViewById(R.id.txtNomUser);
        precioTotal = view.findViewById(R.id.precio_detalle_producto_total);
        edtCantidad = view.findViewById(R.id.edtCantidad);
        btnSumarCantidad = view.findViewById(R.id.btnSumarCantidad);
        btnRestarCantidad = view.findViewById(R.id.btnRestarCantidad);
        btnComprarYa = view.findViewById(R.id.btnComprarYa);
        txtNumProductosEnCesta = view.findViewById(R.id.txtNumProductosEnCesta);
        fab = view.findViewById(R.id.fab);
        fabCompartir = view.findViewById(R.id.fabCompartir);
        fabRetroceder = view.findViewById(R.id.fabRetrocederProd);
        fabPrincipal = view.findViewById(R.id.fabPrincipalProd);
        txtNumCom = view.findViewById(R.id.txtNumCom);
        recyclerViewComentarios = view.findViewById(R.id.rv_Comentarios);
        recyclerViewComentarioUsuario = view.findViewById(R.id.rv_ComentarioUsuario);
        listaComentarios = new ArrayList<>();
        listaComentarioUsuario = new ArrayList<>();
        decimalFormat = new DecimalFormat("#.00");
        preferences = getContext().getSharedPreferences("preferenciasDU",
                Context.MODE_PRIVATE);

        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                txtValoracion.setText("" + v);
            }
        });

        txtNomUser.setText(preferences.getString("nombre", "Desconocido")
        + " " + preferences.getString("apellido", ""));

        //Crear objeto bundle para recibir el objeto envado por argumentos
        Bundle objetoProducto = getArguments();
        Categoria mainModel = null;
        ProductosDetalleCategoria mainModel2 = null;

        //validacion para verificar si existen argumentos enviados para mostrar
        if (objetoProducto != null) {
            mainModel = (Categoria) objetoProducto.getSerializable("producto");
            mainModel2 = (ProductosDetalleCategoria) objetoProducto.getSerializable("productos");

            if (mainModel2 == null) {
                opcion = mainModel.getNombre();
                //Establecer los datos en la vista
                nombre.setText(mainModel.getNombre());
                informacion.setText(mainModel.getInformacion());
                otrosDatos.setText(mainModel.getOtrosDatos());
                precio.setText(mainModel.getPrecio());
                imagenId = mainModel.getImagenId();
                //imagen.setImageResource(mainModel.getImagenId());
                String[] precio_total = precio.getText().toString().split(" ");
                precioTotal.setText(precio_total[0] + " €");
            } else {
                opcion = mainModel2.getNombre();
                //Establecer los datos en la vista
                nombre.setText(mainModel2.getNombre());
                informacion.setText(mainModel2.getInformacion());
                otrosDatos.setText(mainModel2.getOtrosDatos());
                precio.setText(mainModel2.getPrecio());
                imagenId = mainModel2.getImagen();
                //imagen.setImageResource(mainModel2.getImagen());
                String[] precio_total = precio.getText().toString().split(" ");
                precioTotal.setText(precio_total[0] + " €");
            }
        }


        obtenerNumProdCestaUsuario(URLNumProductosCesta);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                obtenerNumComentariosDeBBDD(URLNumCom + txtIdProducto.getText().toString());

            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                obtenerValoracionesG(URLSumaValoracionP + txtIdProducto.getText().toString());
            }
        }, 1500);


        obtener_id_producto(URLidProducto + opcion);
        edtCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtCantidad.getText().toString().equalsIgnoreCase("") || edtCantidad.getText().toString().equalsIgnoreCase("0")) {
                    edtCantidad.setText("1");
                    String[] pr = precio.getText().toString().split(" ");
                    precioT = 1 * Double.valueOf(pr[0]);
                    precioTotal.setText(decimalFormat.format(precioT) + " €");
                } else {
                    String[] pr = precio.getText().toString().split(" ");
                    precioT = Double.valueOf(edtCantidad.getText().toString()) * Double.valueOf(pr[0]);
                    precioTotal.setText(decimalFormat.format(precioT) + " €");
                }
            }
        });


        imagenesEnProductos();
        botones();

        return view;
    }

    private void imagenesEnProductos() {
        int imagenes[] = new int[0];

        if (opcion.equalsIgnoreCase("lenovo s145-15ast")) {
            imagenes = new int[]{imagenId, R.drawable.img1_2, R.drawable.img1_3};
        } else if (opcion.equalsIgnoreCase("asus zenbook 14")) {
            imagenes = new int[]{imagenId, R.drawable.img2_2, R.drawable.img2_3};
        } else if (opcion.equalsIgnoreCase("macbook pro 2020")) {
            imagenes = new int[]{imagenId, R.drawable.img3_2, R.drawable.img3_3};
        } else if (opcion.equalsIgnoreCase("irobot roomba 671")) {
            imagenes = new int[]{imagenId, R.drawable.img7_2, R.drawable.img7_3};
        } else if (opcion.equalsIgnoreCase("ua - sudadera hombre")) {
            imagenes = new int[]{imagenId, R.drawable.img4_2, R.drawable.img4_3};
        } else if (opcion.equalsIgnoreCase("ua - pantalones")) {
            imagenes = new int[]{imagenId, R.drawable.img5_2, R.drawable.img5_3};
        } else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")) {
            imagenes = new int[]{imagenId, R.drawable.img6_2, R.drawable.img6_3};
        } else if (opcion.equalsIgnoreCase("adidas superstar")) {
            imagenes = new int[]{imagenId, R.drawable.img8_2, R.drawable.img8_3};
        } else if (opcion.equalsIgnoreCase("A. claro calvo")) {
            imagenes = new int[]{imagenId, R.drawable.img9_2, R.drawable.img9_3};
        } else if (opcion.equalsIgnoreCase("Maizena harina")) {
            imagenes = new int[]{imagenId, R.drawable.img10_2, R.drawable.img10_3};
        } else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")) {
            imagenes = new int[]{imagenId, R.drawable.img11_2, R.drawable.img11_3};
        } else if (opcion.equalsIgnoreCase("crash team racing nf")) {
            imagenes = new int[]{imagenId, R.drawable.img12_2, R.drawable.img12_3};
        } else if (opcion.equalsIgnoreCase("mis planes son amarte")) {
            imagenes = new int[]{imagenId, R.drawable.img13_2, R.drawable.img13_3};
        } else if (opcion.equalsIgnoreCase("por primera vez")) {
            imagenes = new int[]{imagenId, R.drawable.img14_2, R.drawable.img14_3};
        } else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")) {
            imagenes = new int[]{imagenId, R.drawable.img15_2, R.drawable.img15_3};
        } else if (opcion.equalsIgnoreCase("canva")) {
            imagenes = new int[]{imagenId, R.drawable.img16_2, R.drawable.img16_3};
        }

        // en bucle
        for (int i = 0; i < imagenes.length; i++) {
            flipperImagenes(imagenes[i]);
        }
    }

    private void botones(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getActivity().getSupportFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, new CestaFragment());
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        fabCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);

                //change the type of data you need to share,
                //for image use "image/*
                intent.setType("text/plain");
                if (cantidad != 0) {
                    intent.putExtra(Intent.EXTRA_TEXT, "BUSCA ESTO EN PERFECT MARKET\n============================\n" +
                            "Producto: " + opcion + "\n" +
                            "Precio: " + precioTotal.getText() + "\nCantidad: " + cantidad + " productos\n"
                            + "PVP por unidad: " + precio.getText());
                    startActivity(Intent.createChooser(intent, "Compartir información del producto"));
                } else {
                    intent.putExtra(Intent.EXTRA_TEXT, "BUSCA ESTO EN PERFECT MARKET\n============================\n" +
                            "Producto: " + opcion + "\n" +
                            "Precio: " + precioTotal.getText() + "\nCantidad: 1 productos\n"
                            + "PVP por unidad: " + precio.getText());
                    startActivity(Intent.createChooser(intent, "Compartir información del producto"));
                }

            }
        });


        fabRetroceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fm.popBackStack();
                } catch (Exception e) {
                }
            }
        });

        fabPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "" + fm.getBackStackEntryCount(), Toast.LENGTH_SHORT).show();
                if (fm.getBackStackEntryCount() == 1) {
                    fm.popBackStack();
                } else {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });

        btnSumarCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtCantidad.getText().toString().equalsIgnoreCase("")) {
                    cantidad = 0;
                    cantidad = cantidad + 1;
                    edtCantidad.setText(cantidad + "");
                } else {
                    cantidad = Integer.valueOf(edtCantidad.getText().toString());
                    cantidad = cantidad + 1;
                    edtCantidad.setText(cantidad + "");
                }
            }
        });

        btnRestarCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cantidad = 1;
                if (edtCantidad.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "No se puede restar la cantidad", Toast.LENGTH_SHORT).show();
                } else {
                    cantidad = Integer.valueOf(edtCantidad.getText().toString());
                    if (cantidad == 0) {
                        cantidad = 2;
                    }
                }

                if (cantidad > 1) {
                    cantidad = cantidad - 1;
                    edtCantidad.setText(cantidad + "");
                } else {
                    Toast.makeText(getContext(), "No puede comprar menos de 1 producto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnComprarYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (edtCantidad.getText().toString().equalsIgnoreCase("")) {
                    Snackbar.make(view, "Debes introducir una cantidad a comprar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setMessage("¿Seguro que quiere comprar " + edtCantidad.getText().toString() + " " + opcion + " por " + precioTotal.getText().toString() + "?").setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Snackbar.make(view, edtCantidad.getText().toString() + " " + opcion + " comprado/os con éxito", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            Snackbar.make(view, "Compra cancelada", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                    AlertDialog tituloComprarProducto = alertDialog.create();
                    tituloComprarProducto.setTitle("Comprar " + opcion);
                    tituloComprarProducto.show();
                }
            }
        });

        btnEnviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String em = preferences.getString("email", "Desconocido");


                if (em.equalsIgnoreCase("Desconocido")) {
                    Snackbar.make(view, "Debes iniciar sesión en 'Zona Usuario' para realizar esta acción", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (edtComentario.getText().toString().equalsIgnoreCase("f")) {
                    Snackbar.make(view, "Debe rellenar el campo del Comentario", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setMessage("¿Seguro que quiere enviar el comentario sobre el producto " + opcion + "?").setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    validarComentarios(URLvalidarComentarios);
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            Snackbar.make(view, "No se ha enviado el comentario", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                    AlertDialog tituloEnviarDatos = alertDialog.create();
                    tituloEnviarDatos.setTitle("Enviar Comentario del Producto " + opcion);
                    tituloEnviarDatos.show();
                }
                Toast.makeText(getContext(), "" + edtComentario.getText().toString() , Toast.LENGTH_SHORT).show();
            }
        });

        btnAñadirCesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String num1[] = txtNumProductosEnCesta.getText().toString().split(" ");
                num = Integer.valueOf(num1[0]);

                if (preferences.getString("email", "Desconocido").equalsIgnoreCase("Desconocido")) {
                    Snackbar.make(view, "Debes iniciar sesión en 'Zona Usuario' para realizar esta acción'", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }  else if (edtCantidad.getText().toString().equalsIgnoreCase("")) {
                    Snackbar.make(view, "Debes introducir una cantidad a añadir a la cesta", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    //Toast.makeText(getContext(), "Debes introducir una cantidad", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setMessage("¿Seguro que quiere añadir " + edtCantidad.getText().toString() + " " + opcion + " a la cesta" +
                            " con un precio de " + precioTotal.getText().toString() + "?")
                            .setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            validarProductosCesta(URLValidarProductosCesta);
                            //Toast.makeText(getContext(), "Añadido " + opcion + " a la cesta...", Toast.LENGTH_SHORT).show();

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            Snackbar.make(view, "No se añadirá " + opcion + " a la cesta", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });

                    AlertDialog tituloAñadirCesta = alertDialog.create();
                    tituloAñadirCesta.setTitle("Añadir a la cesta");
                    tituloAñadirCesta.show();
                }
            }
        });

        btnSiguienteP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextView(view);
            }
        });

        btnAnteriorP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousView(view);
            }
        });
    }

    public void mostrarComentarios() {
        try {
            obtenerNumOpinionesTotales(URLNumTOpiniones);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 1; i < Integer.parseInt(numeroTotalOpiniones); i++) {
                            obtenerComentarios(URLObtenerComentarios, String.valueOf(i), txtIdProducto.getText().toString());
                        }
                        obtenerComentarioUsuario(URLObtenerComentarios, preferences.getString("id", "0"), txtIdProducto.getText().toString());
                    } catch (Exception e) {

                    }
                }
            }, 500);
        } catch (Exception e) {}
    }

    public void flipperImagenes(int imagen) {
        imageView = new ImageView(getContext());
        imageView.setBackgroundResource(imagen);
        viewFlipperP.addView(imageView);
    }

    public void previousView(View v) {
        viewFlipperP.setAutoStart(false);
        viewFlipperP.setInAnimation(getContext(), R.anim.slide_in_right);
        viewFlipperP.setOutAnimation(getContext(), R.anim.slide_out_left);
        viewFlipperP.showPrevious();
    }

    public void nextView(View v) {
        viewFlipperP.setAutoStart(false);
        viewFlipperP.setInAnimation(getContext(), android.R.anim.slide_in_left);
        viewFlipperP.setOutAnimation(getContext(), android.R.anim.slide_out_right);
        viewFlipperP.showNext();
    }

    public void direccionesURL() {
        URLinsertarProductoEnCesta = "https://perfectmarket.000webhostapp.com/perfect_market/insertar_productos_en_cesta.php";
        URLValidarProductosCesta = "https://perfectmarket.000webhostapp.com/perfect_market/validarProductosCesta.php";
        URLNumTOpiniones = "https://perfectmarket.000webhostapp.com/perfect_market/numOpinionesTotales.php";
        URLNumProductosCesta = "https://perfectmarket.000webhostapp.com/perfect_market/numProductosCesta.php";
        URLidProducto = "https://perfectmarket.000webhostapp.com/perfect_market/buscar_Id_producto.php?nombre_producto=";
        URLinsertarComentarios = "https://perfectmarket.000webhostapp.com/perfect_market/insertar_comentarios.php";
        URLDatosUsuario = "https://perfectmarket.000webhostapp.com/perfect_market/buscar_nombre_usuario.php?id_usuario=";
        URLvalidarComentarios = "https://perfectmarket.000webhostapp.com/perfect_market/validarComentario.php";
        URLSumaValoracionP = "https://perfectmarket.000webhostapp.com/perfect_market/sumaValoraciones.php?id_producto=";
        URLObtenerComentarios = "https://perfectmarket.000webhostapp.com/perfect_market/obtenerComentarios.php";
        URLNumCom = "https://perfectmarket.000webhostapp.com/perfect_market/numOpiniones.php?id_producto=";
    }

    private void insertarComentarios(String url) {
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Snackbar.make(view, "Gracias por su comentario " + preferences.getString("nombre", "Desconocido") + " "
                            + preferences.getString("apellido", "Desconocido") + " :)", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    edtComentario.setText("");
                    ratingBar.setRating(0);
                    txtValoracion.setText("0.0");
                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    if (listaComentarios.isEmpty()) {
                        mostrarComentarios();
                    } else {
                        listaComentarios.clear();
                        adapterRecyclerComentarios.notifyDataSetChanged();
                        mostrarComentarios();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("id_usuario", preferences.getString("id", "0"));
                    parametros.put("id_producto", txtIdProducto.getText().toString());
                    parametros.put("comentario", edtComentario.getText().toString());
                    parametros.put("valoracion", txtValoracion.getText().toString());
                    return parametros;
                }
            };
            requestQueue.add(stringRequest);
        } catch (Exception e) {

        }
    }

    private void validarComentarios(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.isEmpty()) {
                    insertarComentarios(URLinsertarComentarios);
                    int numComSum = Integer.valueOf(numComentarios) + 1;
                    txtNumCom.setText(numComSum + " comentarios");
                    obtenerValoracionesG(URLSumaValoracionP + txtIdProducto.getText().toString());
                } else {
                    Snackbar.make(view, "El usuario " + preferences.getString("nombre", "Desconocido") + " "
                            + preferences.getString("apellido", "Desconocido") + " ya " +
                            "ha hecho un comentario en este producto.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
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
                parametros.put("id_producto", txtIdProducto.getText().toString());
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void validarProductosCesta(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.isEmpty()) {
                    insertarProductoEnCesta(URLinsertarProductoEnCesta);
                    num = num + 1;
                    txtNumProductosEnCesta.setText(num + " Productos en la Cesta");
                    Snackbar.make(view, "Añadiiendo " + opcion + " a la cesta...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "El usuario " + preferences.getString("nombre", "Desconocido") + " "
                            + preferences.getString("apellido", "Desconocido") + " ya " +
                            "ha añadido este producto a la cesta.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
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
                parametros.put("id_producto", txtIdProducto.getText().toString());
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void obtenerComentarios(String URL, String id_usuario, String idProducto) {
        URL = URL + "?id_usuario=" + id_usuario + "&id_producto=" + idProducto;
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            usuario1 = jsonObject.getString("nombre") + " " + jsonObject.getString("apellido");
                            comentario1 = jsonObject.getString("comentario");
                            valoracion1 = jsonObject.getString("valoracion");
                            listaComentarios.add(new ComentariosProductos(usuario1, comentario1, valoracion1));
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    try {
                        recyclerViewComentarios.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapterRecyclerComentarios = new AdapterRecyclerComentarios(getContext(), listaComentarios);
                        recyclerViewComentarios.setAdapter(adapterRecyclerComentarios);
                    } catch (Exception e) {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonArrayRequest);
        } catch (Exception e) {
        }
    }

    private void obtenerValoracionesG(String URL) {
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            valoracion2 = jsonObject.getString("0");
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    String nC[] = txtNumCom.getText().toString().split(" ");
                    if (!nC[0].equalsIgnoreCase("0")) {
                        Double vv = Double.parseDouble(valoracion2) / Double.parseDouble(nC[0]);
                        txtValGen.setText("" + decimalFormat.format(vv));
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            try {
                requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(jsonArrayRequest);
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    private void obtenerNumOpinionesTotales(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        numeroTotalOpiniones = jsonObject.getString("0");
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void obtenerNumProdCestaUsuario(String URL) {
        URL = URL + "?id_usuario=" + preferences.getString("id", "0");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        numProdCestaUsuario = jsonObject.getString("0");
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                txtNumProductosEnCesta.setText(numProdCestaUsuario +" Productos en la Cesta");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }

    private void obtenerComentarioUsuario(String URL, String id_usuario, String idProducto) {
        URL = URL + "?id_usuario=" + id_usuario + "&id_producto=" + idProducto;
        try {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            usuario1 = jsonObject.getString("nombre") + " " + jsonObject.getString("apellido");
                            comentario1 = jsonObject.getString("comentario");
                            valoracion1 = jsonObject.getString("valoracion");
                            listaComentarioUsuario.add(new ComentariosProductos(usuario1, comentario1, valoracion1));
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    try {
                        txtComUser.setVisibility(View.INVISIBLE);
                        recyclerViewComentarioUsuario.setLayoutManager(new LinearLayoutManager(getContext()));
                        adapterRecyclerComentarios = new AdapterRecyclerComentarios(getContext(), listaComentarioUsuario);
                        recyclerViewComentarioUsuario.setAdapter(adapterRecyclerComentarios);
                    } catch (Exception e) {}
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {}
            });
            requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonArrayRequest);
        } catch (Exception e) {}
    }

    private void obtener_id_producto(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        idProducto = jsonObject.getString("id");
                        txtIdProducto.setText(idProducto);
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }


    private void insertarProductoEnCesta(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Snackbar.make(view, opcion + " añadido con éxito a la cesta", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
                parametros.put("id_producto", txtIdProducto.getText().toString());
                parametros.put("id_usuario", preferences.getString("id", "0"));
                parametros.put("cantidad_comprada", edtCantidad.getText().toString());
                String pT[] = precioTotal.getText().toString().replace(",", ".").split(" ");
                parametros.put("precio_total", pT[0]);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void obtenerNumComentariosDeBBDD(final String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        numComentarios = jsonObject.getString("0");
                        txtNumCom.setText(numComentarios + " comentarios");
                        mostrarComentarios();
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        requestQueue.add(jsonArrayRequest);
    }
}
