package com.example.petagram.Actividades;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petagram.Adaptadores.MascotaAdaptador;
import com.example.petagram.R;
import com.example.petagram.Utilidades.Datos;
import com.example.petagram.Utilidades.SesionDeUsuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ActividadListadoMascotas extends AppCompatActivity {

    FloatingActionButton FabAgregarMascota;
    RecyclerView recyclerView;
    androidx.appcompat.widget.Toolbar TbListaMascotas;
    MascotaAdaptador mascotaAdaptador;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadomascotas);
        recyclerView = findViewById(R.id.RecyclerView);
        OnclickFabAgregarMascota();
        SetearMenuDeToolbar();
        Datos.InicializarDataSet(this);

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
        TbListaMascotas.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                String toast = "";
                switch (id) {
                    case R.id.MenuItemBuscar:
                        toast = "buscar";
                        break;
                    case R.id.MenuItemCercanosAMi:
                        toast = "cercanos a mi";
                        break;
                    case R.id.MenuItemEncontreUnaMascota:
                        toast = "encontre una mascota";
                        break;
                    case R.id.MenuItemLogout:
                        Intent intent = new Intent(ActividadListadoMascotas.this, LoginActivity.class);
                        SesionDeUsuario.Logout(ActividadListadoMascotas.this);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.MenuItemRecientes:
                        mascotaAdaptador.setArrayListUsado(Datos.getMascotasMasRecientes());
                        break;
                    case R.id.MenuItemTodos:
                        mascotaAdaptador.setArrayListUsado(Datos.getTodasLasMascotas());
                        break;
                    case R.id.MenuItemMisMascotas:
                        mascotaAdaptador.setArrayListUsado(Datos.getMascotasDelUsuario(ActividadListadoMascotas.this));
                        break;
                }
                Toast.makeText(ActividadListadoMascotas.this, toast, Toast.LENGTH_SHORT).show();
                return true;
            }

        });
    }


    public void InicializarAdaptador() {

        mascotaAdaptador = new MascotaAdaptador(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mascotaAdaptador);
    }


}
