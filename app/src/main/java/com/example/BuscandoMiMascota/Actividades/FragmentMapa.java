package com.example.BuscandoMiMascota.Actividades;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.BuscandoMiMascota.Modelo.Mascota;
import com.example.BuscandoMiMascota.R;
import com.example.BuscandoMiMascota.Utilidades.CheckeaPermisos;
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

import java.io.IOException;
import java.util.ArrayList;

public class FragmentMapa extends Fragment implements OnMapReadyCallback {

    private GoogleMap Mapa;
    final ArrayList<Marker> Marcadores = new ArrayList<>();
    ImageButton Refrescar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mapa, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        Refrescar = v.findViewById(R.id.ImbRefrescar);
        Refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LlenarMapa();
            }
        });
        mapFragment.getMapAsync(this);
        return v;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Mapa = googleMap;
        Mapa.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Mascota m = null;
                for (Marker marker1 : Marcadores) {
                    if (marker1.getId().equals(marker.getId())) {
                        m = (Mascota) marker.getTag();
                    }
                }
                Intent InformacionDeMascota = new Intent(getActivity(), ActividadInformacionDeMascota.class);
                InformacionDeMascota.putExtra("pk", m.getPk());
                getActivity().startActivity(InformacionDeMascota);
            }
        });
        LatLng Uruguay = new LatLng(-34.905948, -56.191350);
        Mapa.moveCamera(CameraUpdateFactory.newLatLng(Uruguay));
        Mapa.moveCamera(CameraUpdateFactory.zoomTo(15));
        CheckeaPermisos checkeaPermisos = new CheckeaPermisos(getActivity());
        Boolean GpsActivado = DevuelveGps.CheckearUbicacionActivadaYPedirla(getActivity());
        Boolean PermisosActivados = checkeaPermisos.CheckearPermisosyPedirlos();
        if (GpsActivado && PermisosActivados) {
            Mapa.setMyLocationEnabled(true);
        }
        LlenarMapa();
    }

    private void LlenarMapa() {
        Marcadores.clear();
        Mapa.clear();
        Address Ubicacion;
        for (Mascota mascota : Datos.TodasLasMascotas) {
            try {
                Bitmap imagen = FormateadorDeImagenes.DesdeBase64(mascota.getImagen());
                Bitmap imagenescalada = Bitmap.createScaledBitmap(imagen, 80, 80, true);
                BitmapDescriptor icono = BitmapDescriptorFactory.fromBitmap(imagenescalada);
                Ubicacion = DevuelveGps.ConseguirLatyLong(mascota.getUltima_posicion_conocida(), getActivity());
                LatLng latLng = new LatLng(Ubicacion.getLatitude(), Ubicacion.getLongitude());
                Marker marker = Mapa.addMarker(new MarkerOptions().position(latLng).icon(icono).title(mascota.getNombre()).snippet(String.valueOf(mascota.getUsuariotelefono())));
                marker.setTag(mascota);
                Marcadores.add(marker);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
