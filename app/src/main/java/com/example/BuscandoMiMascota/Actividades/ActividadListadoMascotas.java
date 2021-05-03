package com.example.BuscandoMiMascota.Actividades;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BuscandoMiMascota.Adaptadores.MascotaAdaptador;
import com.example.BuscandoMiMascota.Utilidades.Datos;
import com.example.BuscandoMiMascota.Utilidades.SesionDeUsuario;
import com.example.BuscandoMiMascota.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ActividadListadoMascotas extends AppCompatActivity {

    FloatingActionButton FabAgregarMascota;
    RecyclerView recyclerView;
    androidx.appcompat.widget.Toolbar TbListaMascotas;
    public MascotaAdaptador mascotaAdaptador;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadomascotas);
        recyclerView = findViewById(R.id.RecyclerView);
        OnclickFabAgregarMascota();
        SetearMenuDeToolbar();
        mascotaAdaptador = new MascotaAdaptador(this);
        Datos.InicializarDataSet(this);

    }

    private void OnclickFabAgregarMascota() {
        FabAgregarMascota = findViewById(R.id.fabAgregarMascota);
        FabAgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActividadListadoMascotas.this, ActividadPostearAnimal.class);
                intent.putExtra("Modo", 3);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Milog", "onResume: " + Datos.ArrayEnUso.size());
        if (mascotaAdaptador == null && Datos.TodasLasMascotas != null) {
            mascotaAdaptador = new MascotaAdaptador(this);
            mascotaAdaptador.setArrayListUsado(Datos.getTodasLasMascotas());
            InicializarAdaptador();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void SetearMenuDeToolbar() {
        TbListaMascotas = findViewById(R.id.toolbar);
        TbListaMascotas.inflateMenu(R.menu.menu);
        TbListaMascotas.setTitle("Bienvenido");
        final SearchView searchView = findViewById(R.id.Buscar);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mascotaAdaptador.setArrayListUsado(Datos.getTodasLasMascotas());
                TbListaMascotas.setTitle("Todas las mascotas");
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mascotaAdaptador.setArrayListUsado(Datos.Buscar(newText));
                return true;
            }

        });
        TbListaMascotas.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.MenuItemCercanosAMi:
                        mascotaAdaptador.setArrayListUsado(Datos.getMascotasCercanasAmi());
                        TbListaMascotas.setTitle("Cercanos a mi");
                        break;
                    case R.id.MenuItemEncontreUnaMascota:
                        Intent Encontre = new Intent(ActividadListadoMascotas.this, ActividadPostearAnimal.class);
                        Encontre.putExtra("Modo" , 1);
                        startActivity(Encontre);
                        break;
                    case R.id.MenuItemLogout:
                        Intent Logout = new Intent(ActividadListadoMascotas.this, LoginActivity.class);
                        SesionDeUsuario.Logout(ActividadListadoMascotas.this);
                        startActivity(Logout);
                        finish();
                        break;
                    case R.id.MenuItemRecientes:
                        mascotaAdaptador.setArrayListUsado(Datos.getMascotasMasRecientes());
                        TbListaMascotas.setTitle("Recientes");
                        break;
                    case R.id.MenuItemTodos:
                        mascotaAdaptador.setArrayListUsado(Datos.getTodasLasMascotas());
                        TbListaMascotas.setTitle("Todas las mascotas");
                        break;
                    case R.id.MenuItemMisMascotas:
                        mascotaAdaptador.setArrayListUsado(Datos.getMascotasDelUsuario(ActividadListadoMascotas.this));
                        TbListaMascotas.setTitle("Mis mascotas");
                        break;
                }

                return true;
            }

        });
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void InicializarAdaptador() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mascotaAdaptador);
    }


}
