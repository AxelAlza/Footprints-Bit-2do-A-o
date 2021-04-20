package com.example.petagram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petagram.Modelo.Mascota;
import com.example.petagram.Utilidades.AsyncResponse;
import com.example.petagram.Utilidades.CheckeaPermisos;
import com.example.petagram.Utilidades.DevuelveGps;
import com.example.petagram.Utilidades.EnviarJSON;
import com.example.petagram.Utilidades.FormateadorDeImagenes;
import com.example.petagram.Utilidades.RutasUrl;
import com.example.petagram.Utilidades.SesionDeUsuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ActividadPostearAnimal extends AppCompatActivity implements AsyncResponse {

    TextInputEditText EditTextNombre, EditTextEspecie, EditTextRaza, EditTextColor, EditTextEdad, EditTextRecompensa, EditTextDescripcion, EditTextDireccion;
    String genero = "", tamaño = "", ImagenBase64 = "";
    Button Postear;
    ImageButton ImageButtonImagen, ImageButtonMapa;
    Boolean FotoAsignada = false;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postear_animal);
        InicializarViews();
        OnClickBotonImagen();
        OnClickPostear();
        OnClickImageButtonMapa();
    }

    private void OnClickImageButtonMapa() {
        ImageButtonMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String direccion = null;
                try {
                    direccion = DevuelveGps.DevolverUltimaDireccionConocida(ActividadPostearAnimal.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                EditTextDireccion.setText(direccion);
            }
        });
    }

    private void OnClickPostear() {
        Postear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidarCampos()) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    int edad, recompensa;
                    String nombre, especie, raza, color, descripcion, usuario , direccion;
                    nombre = EditTextNombre.getText().toString();
                    especie = EditTextEspecie.getText().toString();
                    raza = EditTextRaza.getText().toString();
                    color = EditTextColor.getText().toString();
                    descripcion = EditTextDescripcion.getText().toString();
                    edad = Integer.parseInt(EditTextEdad.getText().toString());
                    recompensa = Integer.parseInt(EditTextRecompensa.getText().toString());
                    direccion = EditTextDireccion.getText().toString();
                    usuario = SesionDeUsuario.ConseguirEmailDeSesion(getApplicationContext());
                    Date currentTime = Calendar.getInstance(TimeZone.getDefault()).getTime();
                    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
                    String fecha_y_hora = dateFormat.format(currentTime);
                    Mascota mascota = new Mascota(edad,recompensa,usuario,nombre,especie,raza,color,genero,tamaño,ImagenBase64,descripcion,direccion,fecha_y_hora);
                    String datos_a_enviar = gson.toJson(mascota);
                    EnviarJSON enviarJSON = new EnviarJSON(ActividadPostearAnimal.this, RutasUrl.RutaDeProduccion + "/mascota/agregarmascotamovil/",datos_a_enviar);
                    enviarJSON.execute();
                } else {
                    Toast.makeText(ActividadPostearAnimal.this, "Hay campos sin rellenar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void OnClickBotonImagen() {
        ImageButtonImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent BuscarGaleria = new Intent(Intent.ACTION_GET_CONTENT);
                BuscarGaleria.setType("image/*");
                Intent BuscarDocumentos = new Intent(Intent.ACTION_PICK);
                BuscarDocumentos.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                Intent SeleccionarAplicacion = Intent.createChooser(BuscarGaleria, "Seleccionar imagen de mascota");
                SeleccionarAplicacion.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{BuscarDocumentos});
                startActivityForResult(SeleccionarAplicacion, PICK_IMAGE);
            }
        });
    }

    private void InicializarViews() {
        EditTextNombre = findViewById(R.id.TvAgregarNombreMascota);
        EditTextEspecie = findViewById(R.id.TvAgregarEspecieMascota);
        EditTextRaza = findViewById(R.id.TvAgregarRazaMascota);
        EditTextEdad = findViewById(R.id.TvAgregarEdadMascota);
        EditTextColor = findViewById(R.id.TvAgregarColorMascota);
        EditTextRecompensa = findViewById(R.id.TvAgregarRecompensaMascota);
        EditTextDescripcion = findViewById(R.id.TvAgregarDescripcionMascota);
        EditTextDireccion = findViewById(R.id.TvAgregarDireccionMascota);
        Postear = findViewById(R.id.PostearMascota);
        ImageButtonImagen = findViewById(R.id.IbFotoMascota);
        ImageButtonMapa = findViewById(R.id.Mapa);


    }

    public void RadioBotonTamañoClickeado(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.RadioTamañoGrande:
                if (checked) {
                    tamaño = "Grande";
                }
                break;
            case R.id.RadioTamañoMediano:
                if (checked) {
                    tamaño = "Mediano";
                }
                break;
            case R.id.RadioTamañoPequeño:
                if (checked) {
                    tamaño = "Pequeño";
                }
        }
    }

    public void RadioBotonGeneroClickeado(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.RadioGeneroHembra:
                if (checked) {
                    genero = "Hembra";
                }
                break;
            case R.id.RadioGeneropMacho:
                if (checked) {
                    genero = "Macho";
                }
                break;
        }
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();

        return TextUtils.isEmpty(str);
    }

    boolean ValidarCampos() {

        boolean validar = true;

        if (!FotoAsignada) {
            Toast.makeText(this, "Por favor ingrese una foto", Toast.LENGTH_SHORT).show();
            validar = false;
        }

        if (isEmpty(EditTextNombre)) {
            EditTextNombre.setError("Ingrese nombre");
            validar = false;
        }
        if (isEmpty(EditTextDescripcion)) {
            EditTextDescripcion.setError("Ingrese descripcion");
            validar = false;
        }
        if (isEmpty(EditTextColor)) {
            EditTextColor.setError("Ingrese color");
            validar = false;
        }
        if (isEmpty(EditTextDireccion)) {
            EditTextDireccion.setError("Ingrese Direccion");
            validar = false;
        }
        if (isEmpty(EditTextEdad)) {
            EditTextEdad.setError("Ingrese Edad");
            validar = false;
        }
        if (isEmpty(EditTextRecompensa)) {
            EditTextDescripcion.setError("Ingrese recompensa (puede ser 0)");
            validar = false;
        }
        if (isEmpty(EditTextEspecie)) {
            EditTextEspecie.setError("Ingrese Especie");
            validar = false;
        }
        if (isEmpty(EditTextRaza)) {
            EditTextRaza.setError("Ingrese Raza");
            validar = false;
        }
        if (genero.isEmpty()) {
            Toast.makeText(this, "Seleccione un genero", Toast.LENGTH_SHORT).show();
            validar = false;
        }
        if (tamaño.isEmpty()) {
            Toast.makeText(this, "Seleccione un tamaño", Toast.LENGTH_SHORT).show();
            validar = false;
        }


        return validar;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            ImageButtonImagen.setImageURI(data.getData());
            try {
                Bitmap imagen = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                ImagenBase64 = FormateadorDeImagenes.ABase64(imagen);
                FotoAsignada = true;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void AlConseguirDato(String output) {
        Log.d("Milog", "PostearMascota: " + output);
        output = output.trim();
        switch (output){
            case "0":
                Toast.makeText(this, "Se agrego la mascota", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActividadPostearAnimal.this, ActividadListadoMascotas.class);
                startActivity(intent);
                break;
            case "1":
                Toast.makeText(this, "Ocurrio un error posteando la mascota", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "Ni idea de que paso", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}


