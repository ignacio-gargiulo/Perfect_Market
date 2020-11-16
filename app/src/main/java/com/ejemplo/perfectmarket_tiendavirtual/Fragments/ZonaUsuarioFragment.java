package com.ejemplo.perfectmarket_tiendavirtual.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ejemplo.perfectmarket_tiendavirtual.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ZonaUsuarioFragment extends Fragment {

    View view;
    EditText edtEmail, edtContrasegna, edtNombreR, edtApellidoR,
            edtEmailR, edtEdadR, edtContrasegnaR;
    String email, contrasegna, nombreR, apellidoR, emailR, edadR, contrasegnaR;
    String nomDU, apeDU, emailDU, edadDU, idDU;
    Button btnIniciarSesion, btnRegistrarse, btnCerrarSesion;

    TextView txtNombre, txtApellido, txtEdad,txtEmail;

    String URLRegistro,URLValidarUsuario, URLValidarRegistro, URLBuscarUsuarioLogeado;
    RequestQueue requestQueue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_zona_usuario, container, false);

        edtEmail = view.findViewById(R.id.edtEmail);
        edtContrasegna = view.findViewById(R.id.edtContrasegna);
        edtNombreR = view.findViewById(R.id.edtNombreR);
        edtApellidoR = view.findViewById(R.id.edtApellidoR);
        edtEmailR = view.findViewById(R.id.edtEmailR);
        edtEdadR = view.findViewById(R.id.edtEdadR);
        edtContrasegnaR = view.findViewById(R.id.edtContrasegnaR);
        txtNombre = view.findViewById(R.id.txtNombre);
        txtApellido = view.findViewById(R.id.txtApellido);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtEdad = view.findViewById(R.id.txtEdad);
        btnIniciarSesion = view.findViewById(R.id.btnIniciarSesion);
        btnRegistrarse = view.findViewById(R.id.btnRegistrarse);
        btnCerrarSesion = view.findViewById(R.id.btnCerrarSesion);
        URLRegistro = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market2/insertar_usuarios.php";
        URLValidarUsuario = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market2/validar_usuario.php";
        URLValidarRegistro = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market2/validar_registro.php";
        URLBuscarUsuarioLogeado = "https://servidorperfectmarket.000webhostapp.com/conexion_a_perfect_market2/buscarDatosUsuarioLogueado.php?email=";
        requestQueue = Volley.newRequestQueue(getContext());

        recuperarPreferenciasLogin();
        recuperarPreferenciasDU();

        if (txtEmail.getText().toString().equalsIgnoreCase("Desconocido")){
            edtEmail.setEnabled(true);
            edtContrasegna.setEnabled(true);
            btnIniciarSesion.setEnabled(true);
            btnCerrarSesion.setEnabled(false);
        }
        else {
            edtEmail.setEnabled(false);
            edtContrasegna.setEnabled(false);
            btnIniciarSesion.setEnabled(false);
            btnCerrarSesion.setEnabled(true);
        }

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString();
                contrasegna = edtContrasegna.getText().toString();
                if (email.isEmpty() || contrasegna.isEmpty()){
                    Toast.makeText(getContext(), "No se permíten valores vacíos", Toast.LENGTH_SHORT).show();
                }
                else {
                    validarUsuario(URLValidarUsuario);
                }
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              SharedPreferences preferences = getContext().getSharedPreferences("preferenciasLogin",
                      Context.MODE_PRIVATE);
              preferences.edit().clear().commit();
              SharedPreferences preferencesDU = getContext().getSharedPreferences("preferenciasDU",
                      Context.MODE_PRIVATE);
              preferencesDU.edit().clear().commit();
              recuperarPreferenciasDU();
              Toast.makeText(getContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();
                edtEmail.setEnabled(true);
                edtContrasegna.setEnabled(true);
                btnIniciarSesion.setEnabled(true);
                btnCerrarSesion.setEnabled(false);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreR = edtNombreR.getText().toString();
                contrasegnaR = edtContrasegnaR.getText().toString();
                nombreR = edtNombreR.getText().toString();
                apellidoR = edtApellidoR.getText().toString();
                emailR = edtEmailR.getText().toString();
                edadR = edtEdadR.getText().toString();
                contrasegnaR =edtContrasegnaR.getText().toString();
                validarRegistro(URLValidarRegistro);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void registrarUsuario(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Usuario Registrado Correctamente", Toast.LENGTH_SHORT).show();
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
                parametros.put("nombre", nombreR);
                parametros.put("apellido", apellidoR);
                parametros.put("email", emailR.toLowerCase());
                parametros.put("edad", edadR);
                parametros.put("contrasegna", contrasegnaR);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void validarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), "Response: " + response, Toast.LENGTH_SHORT).show();
                if(response.isEmpty()){
                    Toast.makeText(getContext(), "Usuario y/o contraseña incorrecta/as", Toast.LENGTH_SHORT).show();
                }
                else {
                    guardarPreferenciasLogin();
                    Toast.makeText(getContext(), "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show();
                    datos_usuario(URLBuscarUsuarioLogeado + email);
                    edtEmail.setText("");
                    edtContrasegna.setText("");
                    edtEmail.setEnabled(false);
                    edtContrasegna.setEnabled(false);
                    btnIniciarSesion.setEnabled(false);
                    btnCerrarSesion.setEnabled(true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString() , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("email", email.trim());
                parametros.put("contrasegna", contrasegna);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void validarRegistro(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getContext(), "Response: " +response , Toast.LENGTH_SHORT).show();
                if(response.isEmpty()){
                    if (nombreR.isEmpty() || apellidoR.isEmpty() || emailR.isEmpty() || edadR.isEmpty()
                    || contrasegnaR.isEmpty()) {
                        Toast.makeText(getContext(), "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        registrarUsuario(URLRegistro);
                    }
                }
                else {
                    Toast.makeText(getContext(), "El Usuario ya existe", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString() , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("email", emailR.trim());
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void guardarPreferenciasLogin(){
        SharedPreferences preferences = getContext().getSharedPreferences("preferenciasLogin",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.putString("contrasegna", contrasegna);
        editor.putBoolean("sesion", true);
        editor.commit();
    }

    private void recuperarPreferenciasLogin(){
        SharedPreferences preferences = getContext().getSharedPreferences("preferenciasLogin",
                Context.MODE_PRIVATE);
        //edtEmail.setText(preferences.getString("email", "Desconocido"));
        //edtContrasegna.setText(preferences.getString("contrasegna", "Desconocida"));
    }

    private void guardarPreferenciasDU(String id, String n, String a, String em, String ed){
        SharedPreferences preferencesDU = getContext().getSharedPreferences("preferenciasDU",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesDU.edit();
        editor.putString("id", id);
        editor.putString("nombre", n);
        editor.putString("apellido", a);
        editor.putString("email", em);
        editor.putString("edad", ed);
        editor.commit();
    }

    private void recuperarPreferenciasDU(){
        SharedPreferences preferences = getContext().getSharedPreferences("preferenciasDU",
                Context.MODE_PRIVATE);
        Toast.makeText(getContext(), "nnnnn: " + preferences.getString("id", "1"), Toast.LENGTH_SHORT).show();
        txtNombre.setText(preferences.getString("nombre", "Desconocido"));
        txtApellido.setText(preferences.getString("apellido", "Desconocido"));
        txtEmail.setText(preferences.getString("email", "Desconocido"));
        txtEdad.setText(preferences.getString("edad", "18"));
        //edtEmail.setText(preferences.getString("email", "Desconocido"));
        //edtContrasegna.setText(preferences.getString("contrasegna", "Desconocida"));
    }

    private void datos_usuario(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        idDU = jsonObject.getString("id");
                        nomDU = jsonObject.getString("nombre");
                        apeDU = jsonObject.getString("apellido");
                        emailDU = jsonObject.getString("email");
                        edadDU = jsonObject.getString("edad");
                        guardarPreferenciasDU(idDU, nomDU, apeDU, emailDU, edadDU);
                        recuperarPreferenciasDU();
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "ERROR DE CONEXIÓN", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }
}