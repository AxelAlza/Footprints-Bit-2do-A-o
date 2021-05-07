package com.example.BuscandoMiMascota.Adaptadores;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.BuscandoMiMascota.Actividades.FragmentListadoMascotas;
import com.example.BuscandoMiMascota.Actividades.FragmentMapa;

public class AdaptadorDePagina extends FragmentStateAdapter {


    public AdaptadorDePagina(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fr = null;
        switch (position) {
            case 0:
                fr = new FragmentListadoMascotas();
                break;
            case 1:
                fr = new FragmentMapa();
        }
        return fr;
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}



