package com.example.petagram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.petagram.Modelo.Mascota;
import com.example.petagram.Utilidades.FormateadorDeImagenes;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActividadPostearAnimal extends AppCompatActivity {

    TextInputEditText EditTextNombre, EditTextEspecie, EditTextRaza, EditTextColor, EditTextEdad, EditTextRecompensa, EditTextDescripcion, EditTextDireccion;
    String genero, tamaño;
    Button Postear;
    ImageButton BotonImagen;
    String ImagenBase64;
    Boolean FotoAsignada = false;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postear_animal);
        InicializarViews();
        OnClickBotonImagen();
        OnClickPostear();
    }

    private void OnClickPostear() {
        Postear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidarCampos()) {
                    int edad, recompensa;
                    String nombre, especie, raza, color, descripcion;
                    nombre = EditTextNombre.getText().toString();
                    especie = EditTextEspecie.getText().toString();
                    raza = EditTextRaza.getText().toString();
                    color = EditTextColor.getText().toString();
                    descripcion = EditTextDescripcion.getText().toString();
                    edad = Integer.parseInt(EditTextEdad.getText().toString());
                    recompensa = Integer.parseInt(EditTextRecompensa.getText().toString());

                    //TODO: Resolver la direccion y el usuario
                    //Mascota mascota = new Mascota(edad, usuario, recompensa, nombre, especie, raza, color, genero, tamaño, ImagenBase64, descripcion);
                } else {
                    Toast.makeText(ActividadPostearAnimal.this, "Hay campos sin rellenar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void OnClickBotonImagen() {
        BotonImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent BuscarGaleria = new Intent(Intent.ACTION_GET_CONTENT);
                BuscarGaleria.setType("image/*");
                BuscarGaleria.putExtra("SeUsoCamara", false);

                Intent BuscarDocumentos = new Intent(Intent.ACTION_PICK);
                BuscarDocumentos.putExtra("SeUsoCamara", false);
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
        BotonImagen = findViewById(R.id.IbFotoMascota);

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
            BotonImagen.setImageURI(data.getData());
            try {
                Bitmap imagen = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                ImagenBase64 = FormateadorDeImagenes.ABase64(imagen);
                FotoAsignada = true;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}


