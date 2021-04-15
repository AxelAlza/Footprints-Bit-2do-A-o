package com.example.petagram;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petagram.Adaptadores.MascotaAdaptador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ActividadListadoMascotas extends AppCompatActivity {
    RecyclerView RvListaMascotas;
    androidx.appcompat.widget.Toolbar TbListaMascotas;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadomascotas);

        ///Seteo el Recycler view con el adaptador
        setRvListaMascotas();

        ///Seteo el Menu del toolbar
        SetearMenuDeToolbar();
        FloatingActionButton AgregarMascota = findViewById(R.id.FabAgregarMascota);
        AgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_agregar_mascota = new Intent(ActividadListadoMascotas.this, ActividadPostearAnimal.class);
                startActivity(intent_agregar_mascota);
            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void SetearMenuDeToolbar( ) {
        TbListaMascotas = findViewById(R.id.toolbar);
        TbListaMascotas.inflateMenu(R.menu.menu);
    }

    public void setRvListaMascotas( ) {
        RvListaMascotas = findViewById(R.id.RvMascotas);
        LinearLayoutManager ManagerDeLayoutLineal = new LinearLayoutManager(this);
        ManagerDeLayoutLineal.setOrientation(LinearLayoutManager.VERTICAL);
        RvListaMascotas.setLayoutManager(ManagerDeLayoutLineal);
        MascotaAdaptador AdaptadorDeListaMascotas = new MascotaAdaptador(this);
        RvListaMascotas.setAdapter(AdaptadorDeListaMascotas);
    }

}