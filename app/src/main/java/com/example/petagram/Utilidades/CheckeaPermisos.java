package com.example.petagram.Utilidades;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class CheckeaPermisos implements  ActivityCompat.OnRequestPermissionsResultCallback {

    Activity Contexto;
    Boolean Habilitado = false;

    public CheckeaPermisos(Activity contexto) {
        Contexto = contexto;
    }

    public Boolean CheckearPermisosyPedirlos() {

        int PermisoFineLocation = ActivityCompat.checkSelfPermission(Contexto, Manifest.permission.ACCESS_FINE_LOCATION);
        int PermisoCoarseLocation = ActivityCompat.checkSelfPermission(Contexto, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (PermisoFineLocation != PackageManager.PERMISSION_GRANTED && PermisoCoarseLocation != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(Contexto, "Se necesitan permisos de gps", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(Contexto, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            Habilitado = false;
        }
        else {

            Habilitado = true;
        }
        return Habilitado;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Contexto, "Se obtuvieron permisos, intenta de nuevo", Toast.LENGTH_SHORT).show();
                    Habilitado = true;

                } else {
                    Toast.makeText(Contexto, "No se obtuvieron permisos", Toast.LENGTH_SHORT).show();
                    Habilitado = false;
                }
            }
        }
    }


}



