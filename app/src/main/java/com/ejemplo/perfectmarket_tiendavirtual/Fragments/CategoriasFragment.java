package com.ejemplo.perfectmarket_tiendavirtual.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ejemplo.perfectmarket_tiendavirtual.Adaptadores.AdapterRecycler;
import com.ejemplo.perfectmarket_tiendavirtual.Entidades.Categoria;
import com.ejemplo.perfectmarket_tiendavirtual.R;
import com.ejemplo.perfectmarket_tiendavirtual.iComunicaFragments;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

public class CategoriasFragment extends Fragment {

    AdapterRecycler adapterCategorias;
    RecyclerView recyclerCategorias;
    ArrayList<Categoria> listaCategorias;

    FloatingActionButton fabRetroceder, fabPrincipal, fabCesta;

    FragmentTransaction fragmentTransaction;
    FragmentManager fm;

    //referencias para comunicar fragments
    Activity actividad;
    iComunicaFragments interfaceComunicaFragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);
        recyclerCategorias = view.findViewById(R.id.recyclerCategorias);
        listaCategorias = new ArrayList<>();
        fm = getActivity().getSupportFragmentManager();
        fabRetroceder = view.findViewById(R.id.fabRetrocederCat);
        fabPrincipal = view.findViewById(R.id.fabPrincipalCat);
        fabCesta = view.findViewById(R.id.fabCestaCat);

        //cargar lista
        cargarLista();
        //mostrar datos
        mostrarDatosLista();

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
                //fragmentManager.popBackStack();
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
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

        return view;
    }



    public void cargarLista(){
        listaCategorias.add(new Categoria("Apps Móviles", R.drawable.apps));
        listaCategorias.add(new Categoria("Comida", R.drawable.comida));
        listaCategorias.add(new Categoria("Deporte", R.drawable.deporte));
        listaCategorias.add(new Categoria("Electronica e informatica",
                "Aquí encontraras productos de tecnología",R.drawable.electronica));
        listaCategorias.add(new Categoria("Libros", R.drawable.libros));
        listaCategorias.add(new Categoria("Moda", R.drawable.moda));
        listaCategorias.add(new Categoria("Música", R.drawable.musica));
        listaCategorias.add(new Categoria("Videojuegos", R.drawable.videojuegos));
    }

    public void mostrarDatosLista(){
        recyclerCategorias.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCategorias = new AdapterRecycler(getContext(), listaCategorias);
        recyclerCategorias.setAdapter(adapterCategorias);

        adapterCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre_categoria = listaCategorias.get(recyclerCategorias.getChildAdapterPosition(view)).getNombre();
                Toast.makeText(getContext(), "Selecciono: " + nombre_categoria, Toast.LENGTH_SHORT).show();
                interfaceComunicaFragments.enviarCategoria(listaCategorias.get(recyclerCategorias.getChildAdapterPosition(view)));
            }
        });
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
}
