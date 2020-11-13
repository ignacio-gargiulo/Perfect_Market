package com.ejemplo.perfectmarket_tiendavirtual.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ejemplo.perfectmarket_tiendavirtual.R;
import com.github.clans.fab.FloatingActionButton;


public class InformacionFragment extends Fragment {

    FloatingActionButton fabRetroceder, fabPrincipal, fabCesta;

    FragmentTransaction fragmentTransaction;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informacion, container, false);
        fm = getActivity().getSupportFragmentManager();

        fabRetroceder = view.findViewById(R.id.fabRetrocederInf);
        fabPrincipal = view.findViewById(R.id.fabPrincipalInf);
        fabCesta = view.findViewById(R.id.fabCestaInf);

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
}
