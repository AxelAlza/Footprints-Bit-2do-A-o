package com.example.BuscandoMiMascota.Utilidades;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.BuscandoMiMascota.Actividades.LoginActivity;

public class SesionDeUsuario {

    public static final String Contraseña = "LlaveContraseña";
    public static final String Email = "LLaveEmail";
    public static final String Telefono = "LLaveTelefono";
    public static final String PreferenciasCompartidas = "MisPrefs";
    private Context context;

    public static String ConseguirEmailDeSesion(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PreferenciasCompartidas, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Email,"null");
    }

    public static String ConseguirContraseñaDeSesion(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PreferenciasCompartidas, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Contraseña,"null");
    }

    public static String ConseguirTelefonoDeSesion(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PreferenciasCompartidas, Context.MODE_PRIVATE);
        return sharedpreferences.getString(Telefono,"null");
    }

    public static void Logout(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PreferenciasCompartidas, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void Login(String email , String pass, String telefono, LoginActivity login){
        SharedPreferences preferencias = login.getSharedPreferences(PreferenciasCompartidas, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(Email, email);
        editor.putString(Contraseña, pass);
        editor.putString(Telefono, telefono);
        editor.apply();
    }


}
