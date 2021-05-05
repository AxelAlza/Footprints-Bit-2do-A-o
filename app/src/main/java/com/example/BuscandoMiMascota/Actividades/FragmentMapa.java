package com.example.BuscandoMiMascota.Actividades;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.BuscandoMiMascota.Modelo.Mascota;
import com.example.BuscandoMiMascota.R;
import com.example.BuscandoMiMascota.Utilidades.Datos;
import com.example.BuscandoMiMascota.Utilidades.DevuelveGps;
import com.example.BuscandoMiMascota.Utilidades.FormateadorDeImagenes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FragmentMapa extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mapa, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Uruguay = new LatLng(-34.905948, -56.191350);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Uruguay));

        ////El ejecutador de threads
        ThreadPoolExecutor Ejecutador = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);

        //// El hashmap que el mapa va usar para colocar marcadores
        final HashMap<Mascota , LatLng> hashMap = new HashMap<>();
        for (final Mascota m : Datos.TodasLasMascotas) {
            Ejecutador.execute(new Runnable() {
                @Override
                public void run() {
                    Address Ubicacion = null;
                    try {
                        Ubicacion = DevuelveGps.ConseguirLatyLong(m.getUltima_posicion_conocida(), getActivity());
                        LatLng LatitudYlongitud = new LatLng(Ubicacion.getLatitude(), Ubicacion.getLongitude());
                        hashMap.put(m,LatitudYlongitud);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        try {
            Ejecutador.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Map.Entry<Mascota,LatLng> entry : hashMap.entrySet()) {
            Mascota m = entry.getKey();
            Bitmap bm = FormateadorDeImagenes.DesdeBase64(m.getImagen());
            Bitmap rbm = Bitmap.createScaledBitmap(bm,100,100,true);
            BitmapDescriptor icono = BitmapDescriptorFactory.fromBitmap(rbm);
            mMap.addMarker(new MarkerOptions().position(entry.getValue()).title(m.getNombre()).icon(icono));
        }
    }
}
