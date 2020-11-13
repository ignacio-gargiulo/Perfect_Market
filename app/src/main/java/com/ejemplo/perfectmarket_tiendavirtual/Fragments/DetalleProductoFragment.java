package com.ejemplo.perfectmarket_tiendavirtual.Fragments;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
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

    ImageView imageView;
    String val1, val2, val3, val4, val5, val6, val7, val8, val9, val10, val11, val12, val13, val14, val15, val16;
    TextView nombre, informacion, otrosDatos, precio, txtValoracion, precioTotal,txtNumProductosEnCesta, txtNumCom, txtSumaEstrellas;
    RatingBar ratingBar;
    ImageView imagen;
    String opcion, numComentarios;
    String URL, URL2, URL3, URL4, URL5, URL6, URL7, URL8,URL9,URL10,URL11,URL12,URL13,URL14,URL15,URL16,URL17, URL18, URL19, URL20, URL21, URL22, URL23;
    String numCom1,numCom2,numCom3,numCom4,numCom5,numCom6,numCom7,numCom8,numCom9,numCom10,numCom11,numCom12,numCom13,numCom14,numCom15,numCom16;
    EditText edtUsuario, edtComentario, edtCantidad;
    RequestQueue requestQueue;
    Button btnEnviarDatos, btnAñadirCesta, btnSumarCantidad, btnRestarCantidad, btnComprarYa, btnActualizarComentarios;
    String numeroProductosCesta, comentario1,comentario2, comentario3, comentario4, comentario5, valoracion1, valoracion2,
    valoracion3, valoracion4, valoracion5, usuario1, usuario2, usuario3, usuario4, usuario5;
    FloatingActionButton fab, fabCompartir, fabRetroceder, fabPrincipal;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    StringBuilder sb;
    ArrayList<ComentariosProductos> listaComentarios;
    AdapterRecyclerComentarios adapterRecyclerComentarios;
    ViewFlipper viewFlipperP;
    int num;
    int cantidad = 0;
    DecimalFormat decimalFormat;
    double precioT;
    View view;
    ImageButton btnSiguienteP, btnAnteriorP;
    RecyclerView recyclerViewComentarios;
    int imagenId;
    String valProd;
    TextView txtValGen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detalle_producto,container, false);

        direccionesURL();
        direccionesURLNumCom();
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
       // imagen = view.findViewById(R.id.imagen_producto_detalle);
        txtValoracion = view.findViewById(R.id.valoracion_detalle2);
        ratingBar = view.findViewById(R.id.ratingBar);
        edtUsuario = view.findViewById(R.id.edtNombreDeUsuario);
        edtComentario = view.findViewById(R.id.edtComentario);
        btnEnviarDatos = view.findViewById(R.id.btnEnviarDatos);
        btnAñadirCesta = view.findViewById(R.id.btnAñadirCesta);
        precioTotal = view.findViewById(R.id.precio_detalle_producto_total);
        edtCantidad = view.findViewById(R.id.edtCantidad);
        btnSumarCantidad = view.findViewById(R.id.btnSumarCantidad);
        btnRestarCantidad = view.findViewById(R.id.btnRestarCantidad);
        btnComprarYa = view.findViewById(R.id.btnComprarYa);
        btnActualizarComentarios = view.findViewById(R.id.btnActualizarComentarios);
        txtNumProductosEnCesta = view.findViewById(R.id.txtNumProductosEnCesta);
        fab = view.findViewById(R.id.fab);
        fabCompartir = view.findViewById(R.id.fabCompartir);
        fabRetroceder = view.findViewById(R.id.fabRetrocederProd);
        fabPrincipal = view.findViewById(R.id.fabPrincipalProd);
        txtNumCom = view.findViewById(R.id.txtNumCom);
        recyclerViewComentarios = view.findViewById(R.id.rv_Comentarios);
        listaComentarios = new ArrayList<>();
        decimalFormat = new DecimalFormat("#.00");

        obtenerDatosDeBBDD(URL18);
        direccionesValoraciones();



        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                txtValoracion.setText("" + v );
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
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

        fabCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);

        //change the type of data you need to share,
        //for image use "image/*"
                intent.setType("text/plain");
                if (cantidad != 0){
                    intent.putExtra(Intent.EXTRA_TEXT, "BUSCA ESTO EN PERFECT MARKET\n============================\n" +
                            "Producto: " + opcion + "\n" +
                            "Precio: " + precioTotal.getText() + "\nCantidad: " + cantidad + " productos\n"
                            + "PVP por unidad: " + precio.getText());
                    startActivity(Intent.createChooser(intent, "Compartir información del producto"));
                }
                else {
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
                fm.popBackStack();
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

        btnSumarCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtCantidad.getText().toString().equalsIgnoreCase("")){
                    cantidad = 0;
                    cantidad = cantidad + 1;
                    edtCantidad.setText(cantidad + "");
                }
                else {
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
                if (edtCantidad.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getContext(), "No se puede restar la cantidad", Toast.LENGTH_SHORT).show();
                }
                else {
                    cantidad = Integer.valueOf(edtCantidad.getText().toString());
                    if (cantidad == 0){
                        cantidad = 2;
                    }
                }

                if (cantidad > 1) {
                    cantidad = cantidad - 1;
                    edtCantidad.setText(cantidad + "");
                }
                else {
                    Toast.makeText(getContext(), "No puede comprar menos de 1 producto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnComprarYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (edtCantidad.getText().toString().equalsIgnoreCase("")){
                    Snackbar.make(view,"Debes introducir una cantidad a comprar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
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
                            Snackbar.make(view,"Compra cancelada", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                    AlertDialog tituloComprarProducto = alertDialog.create();
                    tituloComprarProducto.setTitle("Comprar " + opcion);
                    tituloComprarProducto.show();
                }
            }
        });

        btnActualizarComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerNumComentarios2();
                if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
                    obtencionValoracionesGenerales(val1);
                }
                else if (opcion.equalsIgnoreCase("asus zenbook 14")){
                    obtencionValoracionesGenerales(val2);
                }
                else if (opcion.equalsIgnoreCase("macbook pro 2020")){
                    obtencionValoracionesGenerales(val3);
                }
                else if (opcion.equalsIgnoreCase("irobot roomba 671")){
                    obtencionValoracionesGenerales(val4);
                }
                else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
                    obtencionValoracionesGenerales(val5);
                }
                else if (opcion.equalsIgnoreCase("ua - pantalones")){
                    obtencionValoracionesGenerales(val6);
                }
                else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
                    obtencionValoracionesGenerales(val7);
                }
                else if (opcion.equalsIgnoreCase("adidas superstar")){
                    obtencionValoracionesGenerales(val8);
                }
                else if (opcion.equalsIgnoreCase("A. claro calvo")){
                    obtencionValoracionesGenerales(val9);
                }
                else if (opcion.equalsIgnoreCase("Maizena harina")){
                    obtencionValoracionesGenerales(val10);
                }
                else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
                    obtencionValoracionesGenerales(val11);
                }
                else if (opcion.equalsIgnoreCase("crash team racing nf")){
                    obtencionValoracionesGenerales(val12);
                }
                else if (opcion.equalsIgnoreCase("mis planes son amarte")){
                    obtencionValoracionesGenerales(val13);
                }
                else if (opcion.equalsIgnoreCase("por primera vez")){
                    obtencionValoracionesGenerales(val14);
                }
                else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
                    obtencionValoracionesGenerales(val15);
                }
                else if (opcion.equalsIgnoreCase("canva")){
                    obtencionValoracionesGenerales(val16);
                }
                listaComentarios.clear();
                adapterRecyclerComentarios.notifyDataSetChanged();
            }
        });

        //Crear objeto bundle para recibir el objeto envado por argumentos
        //Bundle objetoProducto = getArguments();
        Bundle objetoProducto = getArguments();

        //Categoria categoria = null;
        Categoria mainModel = null;
        ProductosDetalleCategoria mainModel2 = null;

        //validacion para verificar si existen argumentos enviados para mostrar
        if (objetoProducto != null){
            mainModel = (Categoria) objetoProducto.getSerializable("producto");
            mainModel2 = (ProductosDetalleCategoria) objetoProducto.getSerializable("productos");

            if (mainModel2 == null){
                opcion = mainModel.getNombre();
                //Establecer los datos en la vista
                nombre.setText(mainModel.getNombre());
                informacion.setText(mainModel.getInformacion());
                otrosDatos.setText(mainModel.getOtrosDatos());
                precio.setText(mainModel.getPrecio());
                imagenId =mainModel.getImagenId();
                //imagen.setImageResource(mainModel.getImagenId());
                String[] precio_total = precio.getText().toString().split(" ");
                precioTotal.setText(precio_total[0] + " €");
            }
            else {
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



        obtenerNumComentarios2();

        edtCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (edtCantidad.getText().toString().equalsIgnoreCase("") || edtCantidad.getText().toString().equalsIgnoreCase("0")){
                    Toast.makeText(getActivity(), "Debes escoger una cantidad", Toast.LENGTH_SHORT).show();
                    edtCantidad.setText("1");
                    String[] pr = precio.getText().toString().split(" ");
                    precioT = 1 * Double.valueOf(pr[0]);
                    precioTotal.setText(decimalFormat.format(precioT) + " €");
                }
                else {
                    String[] pr = precio.getText().toString().split(" ");
                    precioT = Double.valueOf(edtCantidad.getText().toString()) * Double.valueOf(pr[0]);
                    precioTotal.setText(decimalFormat.format(precioT) + " €");
                }
            }
        });


        btnEnviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (edtUsuario.getText().toString().equalsIgnoreCase("") || edtComentario.getText().toString().equalsIgnoreCase("")){
                    Snackbar.make(view, "Debe rellenar el campo de Usuario y Comentario" + edtUsuario.getText().toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setMessage("¿Seguro que quiere enviar el comentario sobre el producto " + opcion + "?").setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Snackbar.make(view, "Gracias por su comentario " + edtUsuario.getText().toString() + " :)", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
                                        ejecutarServicio(URL);
                                    }
                                    else if (opcion.equalsIgnoreCase("asus zenbook 14")){
                                        ejecutarServicio(URL2);
                                    }
                                    else if (opcion.equalsIgnoreCase("macbook pro 2020")){
                                        ejecutarServicio(URL3);
                                    }
                                    else if (opcion.equalsIgnoreCase("irobot roomba 671")){
                                        ejecutarServicio(URL4);
                                    }
                                    else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
                                        ejecutarServicio(URL5);
                                    }
                                    else if (opcion.equalsIgnoreCase("ua - pantalones")){
                                        ejecutarServicio(URL6);
                                    }
                                    else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
                                        ejecutarServicio(URL7);
                                    }
                                    else if (opcion.equalsIgnoreCase("adidas superstar")){
                                        ejecutarServicio(URL8);
                                    }
                                    else if (opcion.equalsIgnoreCase("A. claro calvo")){
                                        ejecutarServicio(URL9);
                                    }
                                    else if (opcion.equalsIgnoreCase("Maizena harina")){
                                        ejecutarServicio(URL10);
                                    }
                                    else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
                                        ejecutarServicio(URL11);
                                    }
                                    else if (opcion.equalsIgnoreCase("crash team racing nf")){
                                        ejecutarServicio(URL12);
                                    }
                                    else if (opcion.equalsIgnoreCase("mis planes son amarte")){
                                        ejecutarServicio(URL13);
                                    }
                                    else if (opcion.equalsIgnoreCase("por primera vez")){
                                        ejecutarServicio(URL14);
                                    }
                                    else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
                                        ejecutarServicio(URL15);
                                    }
                                    else if (opcion.equalsIgnoreCase("canva")){
                                        ejecutarServicio(URL16);
                                    }
                                    int numComSum = Integer.valueOf(numComentarios) + 1;
                                    txtNumCom.setText(numComSum + " comentarios");
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            Snackbar.make(view,"No se ha enviado el comentario", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    });
                    AlertDialog tituloEnviarDatos = alertDialog.create();
                    tituloEnviarDatos.setTitle("Enviar Comentario del Producto " + opcion);
                    tituloEnviarDatos.show();
                }

            }
        });

        btnAñadirCesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String num1[] = txtNumProductosEnCesta.getText().toString().split("/");
                num = Integer.valueOf(num1[0]);

                if(num >= 16){
                    Snackbar.make(view, "No se pueden añadir más de 16 productos en la cesta", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if (edtCantidad.getText().toString().equalsIgnoreCase("")) {
                        Snackbar.make(view, "Debes introducir una cantidad a añadir a la cesta", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //Toast.makeText(getContext(), "Debes introducir una cantidad", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setMessage("¿Seguro que quiere añadir " + edtCantidad.getText().toString() + " " + opcion + " a la cesta" +
                            " con un precio de " + precioTotal.getText().toString() + "?")
                            .setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            insertarProductoEnCesta(URL17);
                            num = num + 1;
                            txtNumProductosEnCesta.setText(num + "/16 productos en la cesta");
                            Snackbar.make(view, "Añadido " + opcion + " a la cesta...", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
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


        int imagenes[] = new int[0];

        if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
            imagenes = new int[]{imagenId, R.drawable.img1_2, R.drawable.img1_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            obtencionValoracionesGenerales(val1);*/
        }
        else if (opcion.equalsIgnoreCase("asus zenbook 14")){
            imagenes = new int[]{imagenId, R.drawable.img2_2, R.drawable.img2_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            obtencionValoracionesGenerales(val2);*/

        }
        else if (opcion.equalsIgnoreCase("macbook pro 2020")){
            imagenes = new int[]{imagenId, R.drawable.img3_2, R.drawable.img3_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                obtencionValoracionesGenerales(val3);*/

        }
        else if (opcion.equalsIgnoreCase("irobot roomba 671")){
            imagenes = new int[]{imagenId, R.drawable.img7_2, R.drawable.img7_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                obtencionValoracionesGenerales(val4);*/

        }
        else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
            imagenes = new int[]{imagenId, R.drawable.img4_2, R.drawable.img4_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            obtencionValoracionesGenerales(val5);*/

        }
        else if (opcion.equalsIgnoreCase("ua - pantalones")){
            imagenes = new int[]{imagenId, R.drawable.img5_2, R.drawable.img5_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

                obtencionValoracionesGenerales(val6);*/

        }
        else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
            imagenes = new int[]{imagenId, R.drawable.img6_2, R.drawable.img6_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

                obtencionValoracionesGenerales(val7);*/

        }
        else if (opcion.equalsIgnoreCase("adidas superstar")){
            imagenes = new int[]{imagenId, R.drawable.img8_2, R.drawable.img8_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

                obtencionValoracionesGenerales(val8);*/

        }
        else if (opcion.equalsIgnoreCase("A. claro calvo")){
            imagenes = new int[]{imagenId, R.drawable.img9_2, R.drawable.img9_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

                obtencionValoracionesGenerales(val9);*/

        }
        else if (opcion.equalsIgnoreCase("Maizena harina")){
            imagenes = new int[]{imagenId, R.drawable.img10_2, R.drawable.img10_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

                obtencionValoracionesGenerales(val10);*/

        }
        else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
            imagenes = new int[]{imagenId, R.drawable.img11_2, R.drawable.img11_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                obtencionValoracionesGenerales(val11);*/

        }
        else if (opcion.equalsIgnoreCase("crash team racing nf")){
            imagenes = new int[]{imagenId, R.drawable.img12_2, R.drawable.img12_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                obtencionValoracionesGenerales(val12);*/

        }
        else if (opcion.equalsIgnoreCase("mis planes son amarte")){
            imagenes = new int[]{imagenId, R.drawable.img13_2, R.drawable.img13_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                obtencionValoracionesGenerales(val13);*/

        }
        else if (opcion.equalsIgnoreCase("por primera vez")){
            imagenes = new int[]{imagenId, R.drawable.img14_2, R.drawable.img14_3};
           /* try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                obtencionValoracionesGenerales(val14);*/

        }
        else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
            imagenes = new int[]{imagenId, R.drawable.img15_2, R.drawable.img15_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                obtencionValoracionesGenerales(val15);*/

        }
        else if (opcion.equalsIgnoreCase("canva")){
            imagenes = new int[]{imagenId, R.drawable.img16_2, R.drawable.img16_3};
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                obtencionValoracionesGenerales(val16);*/

        }

        // en bucle
       for(int i = 0 ; i < imagenes.length; i++){
           flipperImagenes(imagenes[i]);
        }

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

        //Toast.makeText(getContext(), "Nom: " + opcion, Toast.LENGTH_SHORT).show();


        return view;
    }

    public void flipperImagenes(int imagen){
        imageView = new ImageView(getContext());
        imageView.setBackgroundResource(imagen);

        viewFlipperP.addView(imageView);
        //viewFlipperP.setFlipInterval(4000);
        //viewFlipperP.setAutoStart(true);

        //animacion
        //viewFlipperP.setInAnimation(getContext(), android.R.anim.slide_in_left);
        //viewFlipperP.setOutAnimation(getContext(), android.R.anim.slide_out_right);
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

    public void direccionesURL(){
        URL = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP1.php";
        URL2 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP2.php";
        URL3 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP3.php";
        URL4 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP4.php";
        URL5 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP5.php";
        URL6 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP6.php";
        URL7 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP7.php";
        URL8 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP8.php";
        URL9 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP9.php";
        URL10 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP10.php";
        URL11 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP11.php";
        URL12 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP12.php";
        URL13 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP13.php";
        URL14 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP14.php";
        URL15 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP15.php";
        URL16 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_comYvalP16.php";
        URL17 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/insertar_producto_en_cesta.php";
        URL18= "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/pruebaNumRegistros.php";
    }

    public void direccionesURLComentarios(){
        int nCom = Integer.valueOf(numComentarios);

        if (nCom > 5){
            for (int i = nCom; i >= nCom - 4 ; i--) {
                if (i == nCom){
                    if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("asus zenbook 14")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("macbook pro 2020")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("irobot roomba 671")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("ua - pantalones")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("adidas superstar")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("A. claro calvo")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("Maizena harina")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("crash team racing nf")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("mis planes son amarte")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("por primera vez")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("canva")){
                        URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=" + String.valueOf(i);
                    }
                    obtencionComentarios(URL19);
                }
                else if (i == nCom - 1) {
                    if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("asus zenbook 14")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("macbook pro 2020")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("irobot roomba 671")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("ua - pantalones")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("adidas superstar")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("A. claro calvo")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("Maizena harina")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("crash team racing nf")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("mis planes son amarte")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("por primera vez")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("canva")){
                        URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=" + String.valueOf(i);
                    }
                    //URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=" + String.valueOf(i);
                    obtencionComentarios(URL20);
                }
                else if (i == nCom - 2) {
                    if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("asus zenbook 14")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("macbook pro 2020")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("irobot roomba 671")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("ua - pantalones")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("adidas superstar")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("A. claro calvo")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("Maizena harina")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("crash team racing nf")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("mis planes son amarte")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("por primera vez")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("canva")){
                        URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=" + String.valueOf(i);
                    }
                    //URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=" + String.valueOf(i);
                    obtencionComentarios(URL21);
                }
                else if (i == nCom - 3) {
                    if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("asus zenbook 14")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("macbook pro 2020")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("irobot roomba 671")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("ua - pantalones")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("adidas superstar")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("A. claro calvo")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("Maizena harina")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("crash team racing nf")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("mis planes son amarte")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("por primera vez")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("canva")){
                        URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=" + String.valueOf(i);
                    }
                    //URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=" + String.valueOf(i);
                    obtencionComentarios(URL22);
                }
                else if (i == nCom - 4) {
                    if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("asus zenbook 14")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("macbook pro 2020")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("irobot roomba 671")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("ua - pantalones")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("adidas superstar")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("A. claro calvo")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("Maizena harina")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("crash team racing nf")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("mis planes son amarte")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("por primera vez")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=" + String.valueOf(i);
                    }
                    else if (opcion.equalsIgnoreCase("canva")){
                        URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=" + String.valueOf(i);
                    }
                    //URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=" + String.valueOf(i);
                    obtencionComentarios(URL23);
                }
        }
        }

        else {
            if(nCom == 5){
                if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("asus zenbook 14")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("macbook pro 2020")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("irobot roomba 671")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("ua - pantalones")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("adidas superstar")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("A. claro calvo")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("Maizena harina")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("crash team racing nf")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("mis planes son amarte")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("por primera vez")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=5";
                }
                else if (opcion.equalsIgnoreCase("canva")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=4";
                    URL23 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=5";
                }
                obtencionComentarios(URL19);
                obtencionComentarios(URL20);
                obtencionComentarios(URL21);
                obtencionComentarios(URL22);
                obtencionComentarios(URL23);
            }
            else if(nCom == 4){
                if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("asus zenbook 14")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("macbook pro 2020")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("irobot roomba 671")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("ua - pantalones")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("adidas superstar")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("A. claro calvo")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("Maizena harina")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("crash team racing nf")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("mis planes son amarte")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("por primera vez")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=4";
                }
                else if (opcion.equalsIgnoreCase("canva")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=3";
                    URL22 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=4";
                }
                obtencionComentarios(URL19);
                obtencionComentarios(URL20);
                obtencionComentarios(URL21);
                obtencionComentarios(URL22);
            }
            else if(nCom == 3){
                if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("asus zenbook 14")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("macbook pro 2020")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("irobot roomba 671")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("ua - pantalones")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("adidas superstar")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("A. claro calvo")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("Maizena harina")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("crash team racing nf")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("mis planes son amarte")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("por primera vez")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=3";
                }
                else if (opcion.equalsIgnoreCase("canva")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=2";
                    URL21 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=3";
                }
                obtencionComentarios(URL19);
                obtencionComentarios(URL20);
                obtencionComentarios(URL21);
            }
            else if(nCom == 2){
                if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("asus zenbook 14")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("macbook pro 2020")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("irobot roomba 671")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("ua - pantalones")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("adidas superstar")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("A. claro calvo")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("Maizena harina")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("crash team racing nf")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("mis planes son amarte")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("por primera vez")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=2";
                }
                else if (opcion.equalsIgnoreCase("canva")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=1";
                    URL20 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=2";
                }
                obtencionComentarios(URL19);
                obtencionComentarios(URL20);
            }
            else if(nCom == 1){
                if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP1.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("asus zenbook 14")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP2.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("macbook pro 2020")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP3.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("irobot roomba 671")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP4.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP5.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("ua - pantalones")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP6.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP7.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("adidas superstar")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP8.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("A. claro calvo")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP9.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("Maizena harina")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP10.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP11.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("crash team racing nf")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP12.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("mis planes son amarte")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP13.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("por primera vez")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP14.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP15.php?id=1";
                }
                else if (opcion.equalsIgnoreCase("canva")){
                    URL19 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/verComYValP16.php?id=1";
                }
                obtencionComentarios(URL19);
            }
            else if(nCom == 0){
                Toast.makeText(getContext(), "NO HAY COMENTARIOS", Toast.LENGTH_SHORT).show();
            }
        }
    }



    public void direccionesURLNumCom(){
        numCom1 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom1.php";
        numCom2 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom2.php";
        numCom3 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom3.php";
        numCom4 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom4.php";
        numCom5 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom5.php";
        numCom6 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom6.php";
        numCom7 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom7.php";
        numCom8 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom8.php";
        numCom9 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom9.php";
        numCom10 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom10.php";
        numCom11 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom11.php";
        numCom12 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom12.php";
        numCom13 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom13.php";
        numCom14 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom14.php";
        numCom15 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom15.php";
        numCom16 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/numCom16.php";
    }

    private void ejecutarServicio(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Datos enviados correctamente", Toast.LENGTH_SHORT).show();
                edtUsuario.setText("");
                edtComentario.setText("");
                ratingBar.setRating(0);
                txtValoracion.setText("0.0");
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
                parametros.put("usuario",edtUsuario.getText().toString());
                parametros.put("comentario", edtComentario.getText().toString());
                parametros.put("valoracion", txtValoracion.getText().toString());
                return parametros;
            }
        };
        //requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void insertarProductoEnCesta(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Snackbar.make(view, opcion + " añadido con éxito a la cesta", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //Toast.makeText(getContext(), opcion + " añadido con éxito", Toast.LENGTH_SHORT).show();
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
                parametros.put("nombre_producto", opcion);
                parametros.put("precio", String.valueOf(precioT));
                parametros.put("cantidad", edtCantidad.getText().toString());
                return parametros;
            }
        };
        //requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void obtenerDatosDeBBDD(final String url) {
        sb = new StringBuilder();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                      if (url.equalsIgnoreCase(URL18)) {
                            numeroProductosCesta = jsonObject.getString("0");
                            sb.append(numeroProductosCesta);
                            //Toast.makeText(getContext(), "Hola: " + numeroProductosCesta, Toast.LENGTH_SHORT).show();
                            show();
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

    public void obtenerComentariosDeBBDD(final String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        numComentarios = jsonObject.getString("0");
                        txtNumCom.setText(numComentarios + " comentarios");
                        direccionesURLComentarios();
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

    public void obtenerNumComentarios2(){
        if (opcion.equalsIgnoreCase("lenovo s145-15ast")){
            obtenerComentariosDeBBDD(numCom1);
        }
        else if (opcion.equalsIgnoreCase("asus zenbook 14")){
            obtenerComentariosDeBBDD(numCom2);
        }
        else if (opcion.equalsIgnoreCase("macbook pro 2020")){
            obtenerComentariosDeBBDD(numCom3);
        }
        else if (opcion.equalsIgnoreCase("irobot roomba 671")){
            obtenerComentariosDeBBDD(numCom4);
        }
        else if (opcion.equalsIgnoreCase("ua - sudadera hombre")){
            obtenerComentariosDeBBDD(numCom5);
        }
        else if (opcion.equalsIgnoreCase("ua - pantalones")){
            obtenerComentariosDeBBDD(numCom6);
        }
        else if (opcion.equalsIgnoreCase("Vans Classic Po Hoodie")){
            obtenerComentariosDeBBDD(numCom7);
        }
        else if (opcion.equalsIgnoreCase("adidas superstar")){
            obtenerComentariosDeBBDD(numCom8);
        }
        else if (opcion.equalsIgnoreCase("A. claro calvo")){
            obtenerComentariosDeBBDD(numCom9);
        }
        else if (opcion.equalsIgnoreCase("Maizena harina")){
            obtenerComentariosDeBBDD(numCom10);
        }
        else if (opcion.equalsIgnoreCase("harry potter y la piedra f.")){
            obtenerComentariosDeBBDD(numCom11);
        }
        else if (opcion.equalsIgnoreCase("crash team racing nf")){
            obtenerComentariosDeBBDD(numCom12);
        }
        else if (opcion.equalsIgnoreCase("mis planes son amarte")){
            obtenerComentariosDeBBDD(numCom13);
        }
        else if (opcion.equalsIgnoreCase("por primera vez")){
            obtenerComentariosDeBBDD(numCom14);
        }
        else if (opcion.equalsIgnoreCase("PUMA Laliga 1 Hybrid")){
            obtenerComentariosDeBBDD(numCom15);
        }
        else if (opcion.equalsIgnoreCase("canva")){
            obtenerComentariosDeBBDD(numCom16);
        }
    }

    public void obtencionComentarios(final String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        if (url.equalsIgnoreCase(URL19)) {
                            comentario1 = jsonObject.getString("comentario");
                            valoracion1 = jsonObject.getString("valoracion");
                            usuario1 = jsonObject.getString("usuario");
                            listaComentarios.add(new ComentariosProductos(usuario1 ,  comentario1, "Valoración: " + valoracion1 + " ⭐"));
                        }
                        else if (url.equalsIgnoreCase(URL20)) {
                            comentario2 = jsonObject.getString("comentario");
                            valoracion2 = jsonObject.getString("valoracion");
                            usuario2 = jsonObject.getString("usuario");
                            listaComentarios.add(new ComentariosProductos(usuario2, comentario2, "Valoración: " +valoracion2 + " ⭐"));
                        }
                        else if (url.equalsIgnoreCase(URL21)) {
                            comentario3 = jsonObject.getString("comentario");
                            valoracion3 = jsonObject.getString("valoracion");
                            usuario3 = jsonObject.getString("usuario");
                            listaComentarios.add(new ComentariosProductos(usuario3, comentario3, "Valoración: " +valoracion3 + " ⭐"));
                        }
                        else if (url.equalsIgnoreCase(URL22)) {
                            comentario4 = jsonObject.getString("comentario");
                            valoracion4 = jsonObject.getString("valoracion");
                            usuario4 = jsonObject.getString("usuario");
                            listaComentarios.add(new ComentariosProductos(usuario4, comentario4, "Valoración: " +valoracion4 + " ⭐"));
                        }
                        else if (url.equalsIgnoreCase(URL23)) {
                            comentario5 = jsonObject.getString("comentario");
                            valoracion5 = jsonObject.getString("valoracion");
                            usuario5 = jsonObject.getString("usuario");
                            listaComentarios.add(new ComentariosProductos(usuario5, comentario5, "Valoración: " +valoracion5 + " ⭐"));
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
                recyclerViewComentarios.setLayoutManager(new LinearLayoutManager(getContext()));
                adapterRecyclerComentarios = new AdapterRecyclerComentarios(getContext(), listaComentarios);
                recyclerViewComentarios.setAdapter(adapterRecyclerComentarios);
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

    private void show(){
        txtNumProductosEnCesta.setText(sb.toString() + "/16 productos en la cesta");
       // Toast.makeText(getActivity(), "" + sb.toString(), Toast.LENGTH_SHORT).show();
    }

    public void obtencionValoracionesGenerales(final String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        valProd = jsonObject.getString("0");
                        Double valGeneral = ((Double.parseDouble(valProd) * 5.00)/(Double.parseDouble(numComentarios) * 5.00));
                        txtValGen.setText("" + decimalFormat.format(valGeneral));
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


    public void direccionesValoraciones(){
        val1 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion1.php";
        val2 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion2.php";
        val3 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion3.php";
        val4 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion4.php";
        val5 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion5.php";
        val6 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion6.php";
        val7 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion7.php";
        val8 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion8.php";
        val9 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion9.php";
        val10 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion10.php";
        val11 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion11.php";
        val12 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion12.php";
        val13 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion13.php";
        val14 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion14.php";
        val15 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion15.php";
        val16 = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market/suma_valoracion16.php";
    }

    
}
