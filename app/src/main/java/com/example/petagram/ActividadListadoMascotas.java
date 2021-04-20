package com.example.petagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petagram.Adaptadores.MascotaAdaptador;
import com.example.petagram.Adaptadores.MascotaSimpleAdapter_Bitmap;
import com.example.petagram.Modelo.Mascota;
import com.example.petagram.Utilidades.AsyncResponse;
import com.example.petagram.Utilidades.Datos;
import com.example.petagram.Utilidades.EnviarJSON;
import com.example.petagram.Utilidades.FormateadorDeImagenes;
import com.example.petagram.Utilidades.TraeJSON;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class ActividadListadoMascotas extends AppCompatActivity implements AsyncResponse {

    FloatingActionButton FabAgregarMascota;

    RecyclerView recyclerView;
    private static String JSON_URL = "https://aalza.pythonanywhere.com/mascota/json/";
    //="https://aalza.pythonanywhere.com/mascota/json/";
    androidx.appcompat.widget.Toolbar TbListaMascotas;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    ArrayList<HashMap<String, Object>> mascotaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadomascotas);
        recyclerView = findViewById(R.id.RecyclerView);
        OnclickFabAgregarMascota();
        SetearMenuDeToolbar();

        TraeJSON traeJSON = new TraeJSON(this,JSON_URL);
        traeJSON.execute();
    }

    private void OnclickFabAgregarMascota() {
        FabAgregarMascota = findViewById(R.id.fabAgregarMascota);
        FabAgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActividadListadoMascotas.this, ActividadPostearAnimal.class);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void SetearMenuDeToolbar() {
        TbListaMascotas = findViewById(R.id.toolbar);
        TbListaMascotas.inflateMenu(R.menu.menu);
    }


    @Override
    public void AlConseguirDato(String output) {
        Gson gson = new Gson();
        Mascota[] array = gson.fromJson(output, Mascota[].class);
        ArrayList<Mascota> mascotas = new ArrayList<>();
        Collections.addAll(mascotas,array);
        Datos.setMascotas(mascotas);
        MascotaAdaptador mascotaAdaptador = new MascotaAdaptador(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mascotaAdaptador);

    }
}
