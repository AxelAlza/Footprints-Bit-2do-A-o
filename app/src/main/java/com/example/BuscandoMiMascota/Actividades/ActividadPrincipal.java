package com.example.BuscandoMiMascota.Actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.example.BuscandoMiMascota.Adaptadores.AdaptadorDePagina;
import com.example.BuscandoMiMascota.Adaptadores.MascotaAdaptador;
import com.example.BuscandoMiMascota.Utilidades.Datos;
import com.example.BuscandoMiMascota.Utilidades.SesionDeUsuario;
import com.example.BuscandoMiMascota.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class ActividadPrincipal extends AppCompatActivity {

    FloatingActionButton FabAgregarMascota;
    androidx.appcompat.widget.Toolbar TbListaMascotas;
    ViewPager2 FragmentViewPager;
    TabLayout TabLayout;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentViewPager = findViewById(R.id.VPviewpager);
        TabLayout = findViewById(R.id.TabLayout);
        SetearMenuDeToolbar();
        OnclickFabAgregarMascota();
        SetearViewPager();

    }

    private void SetearViewPager() {
        TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        FragmentViewPager.setCurrentItem(0);
                        FragmentViewPager.setUserInputEnabled(true);
                        break;
                    case 1:
                        FragmentViewPager.setCurrentItem(1);
                        FragmentViewPager.setUserInputEnabled(false);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        FragmentViewPager.setAdapter(new AdaptadorDePagina(getSupportFragmentManager(), getLifecycle()));
        FragmentViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        TabLayout.selectTab(TabLayout.getTabAt(0));
                        FragmentViewPager.setUserInputEnabled(true);
                        break;
                    case 1:
                        TabLayout.selectTab(TabLayout.getTabAt(1));
                        FragmentViewPager.setUserInputEnabled(false);

                }
            }
        });
    }

    private void OnclickFabAgregarMascota() {
        FabAgregarMascota = findViewById(R.id.fabAgregarMascota);
        FabAgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActividadPrincipal.this, ActividadPostearAnimal.class);
                intent.putExtra("Modo", 3);
                startActivity(intent);
            }
        });
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
                FragmentListadoMascotas fr = (FragmentListadoMascotas) getSupportFragmentManager().findFragmentByTag("f0");
                fr.mascotaAdaptador.setArrayListUsado(Datos.getTodasLasMascotas());
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
                FragmentListadoMascotas fr = (FragmentListadoMascotas) getSupportFragmentManager().findFragmentByTag("f0");
                fr.mascotaAdaptador.setArrayListUsado(Datos.Buscar(newText));
                return true;
            }

        });
        TbListaMascotas.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FragmentListadoMascotas fr = (FragmentListadoMascotas) getSupportFragmentManager().findFragmentByTag("f0");
                int id = item.getItemId();
                switch (id) {
                    case R.id.MenuItemCercanosAMi:
                        fr.mascotaAdaptador.setArrayListUsado(Datos.getMascotasCercanasAmi());
                        TbListaMascotas.setTitle("Cercanos a mi");
                        break;
                    case R.id.MenuItemEncontreUnaMascota:
                        Intent Encontre = new Intent(ActividadPrincipal.this, ActividadPostearAnimal.class);
                        Encontre.putExtra("Modo", 1);
                        startActivity(Encontre);
                        break;
                    case R.id.MenuItemLogout:
                        Intent Logout = new Intent(ActividadPrincipal.this, LoginActivity.class);
                        SesionDeUsuario.Logout(ActividadPrincipal.this);
                        startActivity(Logout);
                        finish();
                        break;
                    case R.id.MenuItemRecientes:
                        fr.mascotaAdaptador.setArrayListUsado(Datos.getMascotasMasRecientes());
                        TbListaMascotas.setTitle("Recientes");
                        break;
                    case R.id.MenuItemTodos:
                        fr.mascotaAdaptador.setArrayListUsado(Datos.getTodasLasMascotas());
                        TbListaMascotas.setTitle("Todas las mascotas");
                        break;
                    case R.id.MenuItemMisMascotas:
                        fr.mascotaAdaptador.setArrayListUsado(Datos.getMascotasDelUsuario(ActividadPrincipal.this));
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


}
