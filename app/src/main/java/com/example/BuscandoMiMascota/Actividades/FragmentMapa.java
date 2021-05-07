package com.example.BuscandoMiMascota.Actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.net.Uri;
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
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent llamar = new Intent(Intent.ACTION_SEND);
                getActivity().startActivity(llamar);
                return true;
            }
        });
        LatLng Uruguay = new LatLng(-34.905948, -56.191350);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Uruguay));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        Address Ubicacion = null;
        for (Mascota mascota : Datos.TodasLasMascotas) {
            try {
                Bitmap imagen = FormateadorDeImagenes.DesdeBase64(mascota.getImagen());
                Bitmap imagenescalada = Bitmap.createScaledBitmap(imagen , 80 , 80 , true);
                BitmapDescriptor icono = BitmapDescriptorFactory.fromBitmap(imagenescalada);
                Ubicacion = DevuelveGps.ConseguirLatyLong(mascota.getUltima_posicion_conocida() , getActivity());
                LatLng latLng = new LatLng(Ubicacion.getLatitude(),Ubicacion.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).icon(icono).title(mascota.getNombre()).snippet(String.valueOf(mascota.getUsuariotelefono())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
