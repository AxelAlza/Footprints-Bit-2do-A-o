package com.example.petagram.Adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petagram.ActividadInformacionDeMascota;
import com.example.petagram.Modelo.Mascota;
import com.example.petagram.R;
import com.example.petagram.Utilidades.Datos;
import com.example.petagram.Utilidades.FormateadorDeImagenes;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

public class MascotaAdaptador extends RecyclerView.Adapter<MascotaAdaptador.ContenedorDeViews> {

    Activity contexto;
    ArrayList<Mascota> mascotas;

    public MascotaAdaptador(Activity contexto) {
        this.mascotas = Datos.getMascotas();
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ContenedorDeViews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mascota, parent, false);
        return new ContenedorDeViews(v);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final ContenedorDeViews contenedorDeViews, final int position) {

        final Mascota mascota = mascotas.get(position);

        contenedorDeViews.TvNombreMascota.setText(mascota.getNombre());
        contenedorDeViews.TvUsuario.setText(mascota.getUsuario());

        String recompensa = "$" + mascota.getRecompensa();
        contenedorDeViews.TvRecompensaMascota.setText(recompensa);

        contenedorDeViews.TvDescripcionMascota.setText(mascota.getDescripcion());

        ///A partir de hoy odio trabajar con fechas en java
        final DateTimeFormatter input = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DateTimeFormatter output = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        TemporalAccessor date = input.parse(mascota.getFecha_y_hora());
        contenedorDeViews.TvFechaYHora.setText(output.format(date));

        contenedorDeViews.ImvMascota.setImageBitmap(FormateadorDeImagenes.DesdeBase64(mascota.getImagen()));

        contenedorDeViews.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, ActividadInformacionDeMascota.class);
                intent.putExtra("position" , position);
                contexto.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { //Cantidad de elementos de la lista
        return mascotas.size();
    }



    public static class ContenedorDeViews extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView TvNombreMascota, TvUsuario, TvDescripcionMascota, TvRecompensaMascota, TvFechaYHora;
        ImageView ImvMascota;

        public ContenedorDeViews(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewMascota);
            TvNombreMascota = itemView.findViewById(R.id.TvNombreMascota);
            TvUsuario = itemView.findViewById(R.id.TVusuario);
            TvRecompensaMascota = itemView.findViewById(R.id.TvRecompensaMascota);
            TvDescripcionMascota = itemView.findViewById(R.id.TvDescripcionMascota);
            TvFechaYHora = itemView.findViewById(R.id.TvFechaYHora);
            ImvMascota = itemView.findViewById(R.id.ImvMascota);

        }
    }
}
