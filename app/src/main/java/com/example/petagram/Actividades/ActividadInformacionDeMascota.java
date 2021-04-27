package com.example.petagram.Actividades;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petagram.Modelo.Mascota;
import com.example.petagram.R;
import com.example.petagram.Utilidades.Datos;
import com.example.petagram.Utilidades.FormateadorDeImagenes;
import com.example.petagram.Utilidades.SesionDeUsuario;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class ActividadInformacionDeMascota extends AppCompatActivity {

    TextView TvNombreMascota, TvUsuario, TvEspecieMascota, TvRazaMascota, TvDescripcionMascota, TvEdadMascota, TvGeneroMascota, TvColorMascota, TvRecompensaMascota, TvFechaYHora, TvUltimaConocida, TvTamanoMascota, TvTelefono;
    ImageView ImvMascota;
    Button EditarMascota;
    Mascota mascota;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_de_mascota);
        InicializarViews();
        Intent intent = getIntent();
        int pk = intent.getIntExtra("pk", 0);


        EditarMascota = findViewById(R.id.btnEditarMascota);

        for (Mascota m : Datos.getTodasLasMascotas()) {
            if(m.getPk() == pk){
                mascota = m;
            }
        }

        if (mascota.getUsuario().equals(SesionDeUsuario.ConseguirEmailDeSesion(this))) {
            EditarMascota.setVisibility(View.VISIBLE);
            EditarMascota.setEnabled(true);

            EditarMascota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActividadInformacionDeMascota.this, ActividadPostearAnimal.class);
                    intent.putExtra("Modo" , true);
                    intent.putExtra("pk" , mascota.getPk());
                    startActivity(intent);
                }
            });
        } else {
            EditarMascota.setVisibility(View.INVISIBLE);
            EditarMascota.setEnabled(false);
        }


        RellenarCampos(mascota);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void RellenarCampos(Mascota mascota) {
        TvNombreMascota.setText(mascota.getNombre());
        TvUsuario.setText(mascota.getUsuario());
        TvEdadMascota.setText(String.valueOf(mascota.getEdad()));
        TvGeneroMascota.setText(mascota.getGenero());
        TvColorMascota.setText(mascota.getColor());
        TvRecompensaMascota.setText(String.valueOf(mascota.getRecompensa()));
        TvDescripcionMascota.setText(mascota.getDescripcion());
        TvRazaMascota.setText(mascota.getRaza());
        TvEspecieMascota.setText(mascota.getEspecie());
        TvTelefono.setText(mascota.getUsuariotelefono());


        /////Las fechas se resisten, por que son asi?
        TemporalAccessor date = null;
        DateTimeFormatter output = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter input;
        try {
            input = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
            date = input.parse(mascota.getFecha_denuncia());

        } catch (Exception e) {
            input = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            date = input.parse(mascota.getFecha_denuncia());
        }
        ///////////////////////////////////////////////

        TvFechaYHora.setText(output.format(date));
        TvTamanoMascota.setText(mascota.getTamano());
        TvUltimaConocida.setText(mascota.getUltima_posicion_conocida());
        ImvMascota.setImageBitmap(FormateadorDeImagenes.DesdeBase64(mascota.getImagen()));
    }

    private void InicializarViews() {
        TvNombreMascota = findViewById(R.id.TvNombreMascota);
        TvUsuario = findViewById(R.id.TVusuario);
        TvEdadMascota = findViewById(R.id.TvEdadMascota);
        TvGeneroMascota = findViewById(R.id.TvGeneroMascota);
        TvColorMascota = findViewById(R.id.TvColorMascota);
        TvRecompensaMascota = findViewById(R.id.TvRecompensaMascota);
        TvDescripcionMascota = findViewById(R.id.TvDescripcionMascota);
        TvRazaMascota = findViewById(R.id.TvRazaMascota);
        TvEspecieMascota = findViewById(R.id.TvEspecieMascota);
        TvFechaYHora = findViewById(R.id.TvFechaYHora);
        TvTamanoMascota = findViewById(R.id.TvTamanoMascota);
        TvUltimaConocida = findViewById(R.id.TvUltimaPosicionConocida);
        ImvMascota = findViewById(R.id.ImvMascota);
        TvTelefono = findViewById(R.id.TvTelefono);
    }
}