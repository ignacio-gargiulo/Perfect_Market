package com.ejemplo.perfectmarket_tiendavirtual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.ContentProvider;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.ejemplo.perfectmarket_tiendavirtual.Entidades.Categoria;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.ProductosDetalleCategoria;
import com.ejemplo.perfectmarket_tiendavirtual.Fragments.CategoriasFragment;
import com.ejemplo.perfectmarket_tiendavirtual.Fragments.CestaFragment;
import com.ejemplo.perfectmarket_tiendavirtual.Fragments.DetalleCategoriaFragment;
import com.ejemplo.perfectmarket_tiendavirtual.Fragments.DetalleProductoFragment;
import com.ejemplo.perfectmarket_tiendavirtual.Fragments.InformacionFragment;
import com.ejemplo.perfectmarket_tiendavirtual.Fragments.PrincipalFragment;
import com.ejemplo.perfectmarket_tiendavirtual.Fragments.ZonaUsuarioFragment;
import com.google.android.material.navigation.NavigationView;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, iComunicaFragments {

    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    //int count = getSupportFragmentManager().getBackStackEntryCount();
    //variables para cargar los fragments
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //variable del fragment detalle producto
    DetalleProductoFragment detalleProductoFragment;
    DetalleCategoriaFragment detalleCategoriaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        //establecer el evento onClick al NavigationView
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //Toast.makeText(this, "" + count, Toast.LENGTH_SHORT).show();
        //cargar fragment principal
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, new PrincipalFragment());
        fragmentTransaction.commit();
        //fragmentTransaction.addToBackStack(null);
    }


   /*@Override
    public void onBackPressed() {
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            finish();
        }else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (menuItem.getItemId() == R.id.nav_principal){
            if (fragmentManager.getBackStackEntryCount() == 1){
                fragmentManager.popBackStack();
            }
            else {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
        if (menuItem.getItemId() == R.id.nav_categorias){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new CategoriasFragment());
            fragmentTransaction.commit();
            fragmentTransaction.addToBackStack(null);
        }
        if (menuItem.getItemId() == R.id.nav_cesta){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new CestaFragment());
            fragmentTransaction.commit();
            fragmentTransaction.addToBackStack(null);
        }
        if (menuItem.getItemId() == R.id.nav_zona_usuario){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ZonaUsuarioFragment());
            fragmentTransaction.commit();
            fragmentTransaction.addToBackStack(null);
        }
        if (menuItem.getItemId() == R.id.nav_informacion){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new InformacionFragment());
            fragmentTransaction.commit();
            fragmentTransaction.addToBackStack(null);
        }
        return false;
    }

    @Override
    public void enviarCategoria(Categoria categoria) {
        //Aqui se realiza la lógica necesaria para poder realizar el envio
        detalleCategoriaFragment = new DetalleCategoriaFragment();
        //objeto bundle para transportar la información
        Bundle bundleEnvio = new Bundle();
        //enviar objeto con Serializable
        bundleEnvio.putSerializable("categoria", categoria);
        detalleCategoriaFragment.setArguments(bundleEnvio);

        //abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, detalleCategoriaFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void enviarProducto(Categoria categoria) {
        //Aqui se realiza la lógica necesaria para poder realizar el envio
        detalleProductoFragment = new DetalleProductoFragment();
        //objeto bundle para transportar la información
        Bundle bundleEnvio = new Bundle();
        //enviar objeto con Serializable
        bundleEnvio.putSerializable("producto", categoria);
        detalleProductoFragment.setArguments(bundleEnvio);

        //abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, detalleProductoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void enviarProducto2(ProductosDetalleCategoria productosDetalleCategoria) {
        //Aqui se realiza la lógica necesaria para poder realizar el envio
        detalleProductoFragment = new DetalleProductoFragment();
        //objeto bundle para transportar la información
        Bundle bundleEnvio = new Bundle();
        //enviar objeto con Serializable
        bundleEnvio.putSerializable("productos", productosDetalleCategoria);
        detalleProductoFragment.setArguments(bundleEnvio);

        //abrir fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, detalleProductoFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
