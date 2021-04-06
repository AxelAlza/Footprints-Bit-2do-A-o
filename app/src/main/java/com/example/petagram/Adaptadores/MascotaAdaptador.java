package com.example.petagram.Adaptadores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petagram.R;

public class MascotaAdaptador extends RecyclerView.Adapter<MascotaAdaptador.MascotaViewHolder>{

    Activity activity;

    public MascotaAdaptador(Activity activity){


        this.activity = activity;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //Darle vida a nuestro Layaout
        ///Esto infla la actividad
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mascota, parent, false);
        return new MascotaViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final MascotaViewHolder mascotaViewHolder, int position) {

        ///Aca va lo que pasa cuando se le asigna una mascota a cada tarjeta

    }

    @Override
    public int getItemCount() { //Cantidad de elementos de la lista
        return 20;
    }

    public static class MascotaViewHolder extends RecyclerView.ViewHolder {

        //Aca van las views de la tarjeta, (Likes , nombre bla bla)

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }



}
