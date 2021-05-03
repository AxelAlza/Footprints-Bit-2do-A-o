package com.example.BuscandoMiMascota.Actividades;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.BuscandoMiMascota.Adaptadores.MascotaAdaptador;
import com.example.BuscandoMiMascota.R;
import com.example.BuscandoMiMascota.Utilidades.Datos;



public class FragmentListadoMascotas extends Fragment {
    RecyclerView recyclerView;
    public MascotaAdaptador mascotaAdaptador;
    boolean onResume;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listado_mascotas, container, false);
        recyclerView = v.findViewById(R.id.RecyclerView);
        mascotaAdaptador = new MascotaAdaptador(getActivity());
        Datos.InicializarDataSet(getActivity());
        onResume = false;
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d("Milog", "onResume: " + Datos.ArrayEnUso.size());
        if (Datos.TodasLasMascotas == null && onResume) {
            Datos.InicializarDataSet(getActivity());
        }
        if (mascotaAdaptador == null && Datos.TodasLasMascotas != null) {
            mascotaAdaptador = new MascotaAdaptador(getActivity());
            mascotaAdaptador.setArrayListUsado(Datos.getTodasLasMascotas());
            InicializarAdaptador();
            onResume = true;
        }
    }


    public void InicializarAdaptador() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mascotaAdaptador);
    }


}
