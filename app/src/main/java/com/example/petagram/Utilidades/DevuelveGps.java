package com.example.petagram.Utilidades;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class DevuelveGps {
    private Activity Contexto;
    private final LocationManager ManagerDeUbicacion;
    private Location Ubicacion;
    private Boolean Trackeando = false;
    private Geocoder geocoder;

    public Boolean Trackeando() {
        return Trackeando;
    }

    public DevuelveGps(Activity context) {
        this.Contexto = context;
        ManagerDeUbicacion = (LocationManager) Contexto.getSystemService(Context.LOCATION_SERVICE);
        Locale locale = new Locale("es", "UY");
        geocoder = new Geocoder(context, locale);
    }

    //Empieza el trackeo del dispositivo, lo que me da las coordenadas cada vez que cambio de ubicacion
    @SuppressLint("MissingPermission")
    public void EmpezarTrackeo() {

        ManagerDeUbicacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 5, new LocationListener() {

            //Cuanda cambia la ubicacion, Guardo la ubicacion
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Ubicacion = location;
                Trackeando = true;
            }
        });
    }

    @SuppressLint("MissingPermission")
    public String DevolverUltimaUbicacionConocida() {
        Ubicacion = ManagerDeUbicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String ubicacion = "";
        try {

            List<Address> direcciones = geocoder.getFromLocation(Ubicacion.getLatitude(), Ubicacion.getLongitude(), 1);
            ubicacion = direcciones.get(0).getAddressLine(0);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return ubicacion;

    }
}


