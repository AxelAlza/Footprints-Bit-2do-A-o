package com.example.petagram.Demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.petagram.R;
import com.example.petagram.Utilidades.TraeJSON;
import com.example.petagram.Utilidades.AsyncResponse;
import com.example.petagram.Modelo.Mascota;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

///Implementar la interfaz AsyncResponse
public class DemonstracionUtilidadTraeJson extends AppCompatActivity implements AsyncResponse {

    public TextView TvPrueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        TvPrueba = findViewById(R.id.textView);

        ////Crear una instancia de la clase TraeJSON y pasarle la actividad donde se llama como parametro y la ruta de donde va a traer
        ////el JSON
        TraeJSON traeJSON = new TraeJSON(this, "https://10.0.2.2/mascota/agregarmascotamovil/");
        ////Ejecutar la tarea de TraeJSON
        traeJSON.execute();



    }

    ////Cuando se termine la tarea de red y se haya traido el JSON (o si salio mal tenerlo en cuenta)
    ////Se ejecuta este metodo
    @Override
    public void AlConseguirDato(String output) {

        ///Demo GSON, crear una nueva instancia de GSON
        Gson gson = new Gson();

        //Crear un array de objetos clase "Mascota" a partir del String que trae "traeJSON"
        Mascota[] a = gson.fromJson(output, Mascota[].class);

        //Declarar un nuevo ArrayList
        ArrayList<Mascota> arrayList = new ArrayList<>();

        //Unir el array con el arraylist
        Collections.addAll(arrayList,a);

        ///Hacer lo que quieras con el arraylist
        TvPrueba.setText(arrayList.get(1).getNombre());

    }
}