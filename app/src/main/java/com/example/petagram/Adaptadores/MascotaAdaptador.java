package com.example.petagram.Adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petagram.Actividades.ActividadInformacionDeMascota;
import com.example.petagram.Modelo.Mascota;
import com.example.petagram.R;
import com.example.petagram.Utilidades.Datos;
import com.example.petagram.Utilidades.FormateadorDeImagenes;
import com.example.petagram.Utilidades.ListenerDeDatos;
import com.example.petagram.Utilidades.SesionDeUsuario;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;

public class MascotaAdaptador extends RecyclerView.Adapter<MascotaAdaptador.ContenedorDeViews> {

    Activity contexto;
    ArrayList<Mascota> mascotas;

    public MascotaAdaptador(Activity contexto) {
        this.mascotas = Datos.getTodasLasMascotas();
        this.contexto = contexto;
        Datos.setListenerDeDatos(new ListenerDeDatos() {
            @Override
            public void OnSeAgregoMascota(int position) {
                notifyItemInserted(position);
                Log.d("Milog", "Se agrego una mascota");
            }

            @Override
            public void OnSeModificoMascota(int position) {
                notifyItemChanged(position);
            }

            @Override
            public void OnSeEliminoMascota(int position) {
                notifyItemRemoved(position);
            }
        });

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
        if (mascota.getUsuario().equals(SesionDeUsuario.ConseguirEmailDeSesion(contexto))){
            contenedorDeViews.IbEliminarMascota.setVisibility(View.VISIBLE);
            contenedorDeViews.IbEliminarMascota.setEnabled(true);
            contenedorDeViews.IbEliminarMascota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Datos.EliminarMascota(mascota);
                }
            });
        } else {
            contenedorDeViews.IbEliminarMascota.setVisibility(View.INVISIBLE);
            contenedorDeViews.IbEliminarMascota.setEnabled(false);
        }

        contenedorDeViews.TvNombreMascota.setText(mascota.getNombre());
        contenedorDeViews.TvUsuario.setText(mascota.getUsuario());

        String recompensa = "$" + mascota.getRecompensa();
        contenedorDeViews.TvRecompensaMascota.setText(recompensa);

        contenedorDeViews.TvDescripcionMascota.setText(mascota.getDescripcion());

        ///A partir de hoy odio trabajar con fechas en java
        TemporalAccessor date = null;
        DateTimeFormatter output = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter input;
        try {
            input = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            date = input.parse(mascota.getFecha_y_hora());

        } catch (Exception e) {
            input = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            date = input.parse(mascota.getFecha_y_hora());
        }
        contenedorDeViews.TvFechaYHora.setText(output.format(date));
        ///////////////////////////////////////////////////////////////////////////

        contenedorDeViews.ImvMascota.setImageBitmap(FormateadorDeImagenes.DesdeBase64(mascota.getImagen()));

        contenedorDeViews.TvTelefono.setText(String.valueOf(mascota.getUsuariotelefono()));

        contenedorDeViews.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, ActividadInformacionDeMascota.class);
                intent.putExtra("position", position);
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
        TextView TvNombreMascota, TvUsuario, TvDescripcionMascota, TvRecompensaMascota, TvFechaYHora, TvTelefono;
        ImageView ImvMascota;
        ImageButton IbEliminarMascota;


        public ContenedorDeViews(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardviewMascota);
            TvNombreMascota = itemView.findViewById(R.id.TvNombreMascota);
            TvUsuario = itemView.findViewById(R.id.TVusuario);
            TvRecompensaMascota = itemView.findViewById(R.id.TvRecompensaMascota);
            TvDescripcionMascota = itemView.findViewById(R.id.TvDescripcionMascota);
            TvFechaYHora = itemView.findViewById(R.id.TvFechaYHora);
            ImvMascota = itemView.findViewById(R.id.ImvMascota);
            TvTelefono = itemView.findViewById(R.id.TvUsuarioTelefono);
            IbEliminarMascota = itemView.findViewById(R.id.ImvEliminarMascota);

        }
    }

}
