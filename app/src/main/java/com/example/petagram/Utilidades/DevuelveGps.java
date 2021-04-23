package com.example.petagram.Utilidades;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class DevuelveGps {

    private static LocationManager ManagerDeUbicacion;
    private static final Locale locale = new Locale("es", "UY");
    private static Boolean Trackeando = false;
    static CheckeaPermisos checkeaPermisos;

    public static String DevolverUltimaDireccionConocida(Activity context) throws IOException {
        String ubicacion = "";
        if (ManagerDeUbicacion == null) {
            ManagerDeUbicacion = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
        checkeaPermisos = new CheckeaPermisos(context);
        if (checkeaPermisos.CheckearPermisosyPedirlos()) {
            if (CheckearUbicacionActivadaYPedirla(context)) {
                if (!Trackeando) {
                    EmpezarTrackeo();
                }
                Geocoder geocoder = new Geocoder(context, locale);
                Location Ubicacion = null;
                while (Ubicacion == null) {
                    Ubicacion = getUbicacion(context);
                }
                List<Address> direcciones = geocoder.getFromLocation(Ubicacion.getLatitude(), Ubicacion.getLongitude(), 1);
                ubicacion = direcciones.get(0).getAddressLine(0);
            }
        }
        return ubicacion;
    }

    public static Address ConseguirLatyLong(String Direccion, Activity context) {
        Geocoder geocoder = new Geocoder(context, locale);
        List<Address> direcciones = null;
        try {
            direcciones = geocoder.getFromLocationName(Direccion, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return direcciones.get(0);
    }

    public static Boolean CheckearUbicacionActivadaYPedirla(final Activity contexto) {
        if (ManagerDeUbicacion == null) {
            ManagerDeUbicacion = (LocationManager) contexto.getSystemService(Context.LOCATION_SERVICE);
        }
        boolean gps_habilitado = ManagerDeUbicacion.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean red_habilitada = ManagerDeUbicacion.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!gps_habilitado || !red_habilitada) {
            new AlertDialog.Builder(contexto)
                    .setMessage("No podemos darte la direccion")
                    .setPositiveButton("Activar Ubicacion", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            contexto.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No activar", null)
                    .show();
            return false;
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    public static Location getUbicacion(Activity context) {

        if (ManagerDeUbicacion == null) {
            ManagerDeUbicacion = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        checkeaPermisos = new CheckeaPermisos(context);
        Location MejorUbicacion = null;
        if (checkeaPermisos.CheckearPermisosyPedirlos()) {
            if (CheckearUbicacionActivadaYPedirla(context)) {
                if (!Trackeando) {
                    EmpezarTrackeo();
                }

                ///Hay que fijarse en cada proveedor de ubicacion, tambien fijarse cual es la mas aproximada
                List<String> Proveedores = ManagerDeUbicacion.getProviders(true);
                MejorUbicacion = null;
                for (String Proveedor : Proveedores) {
                    Location l = ManagerDeUbicacion.getLastKnownLocation(Proveedor);
                    //Si un proveedor no puede darme una ubicacion ignoro el proveedor
                    if (l == null) {
                        continue;
                    }

                    ///Checkeo cual de todas las ubicaciones de los proveedores son las mas exactas y me quedo con la mas precisa
                    if (MejorUbicacion == null || l.getAccuracy() < MejorUbicacion.getAccuracy()) {
                        MejorUbicacion = l;
                    }
                }
            }
        }
        return MejorUbicacion;
    }

    @SuppressLint("MissingPermission")
    private static void EmpezarTrackeo() {
        ManagerDeUbicacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 5, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Trackeando = true;
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Trackeando = false;
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                Trackeando = false;
            }
        });
    }

}





