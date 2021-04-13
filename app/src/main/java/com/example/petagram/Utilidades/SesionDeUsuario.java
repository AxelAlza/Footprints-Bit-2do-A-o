package com.example.petagram.Utilidades;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.example.petagram.LoginActivity;

public class SesionDeUsuario {

    public static final String Contraseña = "LlaveContraseña";
    public static final String Email = "LLaveEmail";
    public static final String PreferenciasCompartidas = "MisPrefs";
    private Context context;

    public static String ConseguirEmailDeSesion(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PreferenciasCompartidas, Context.MODE_PRIVATE);
        String email = sharedpreferences.getString(Email,"null");
        return email;
    }

    public static String ConseguirContraseñaDeSesion(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PreferenciasCompartidas, Context.MODE_PRIVATE);
        String contraseña = sharedpreferences.getString(Contraseña,"null");
        return contraseña;
    }

    public static void Logout(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences(PreferenciasCompartidas, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }

    public static void Login(String email , String pass , LoginActivity login){
        SharedPreferences preferencias = login.getSharedPreferences(PreferenciasCompartidas, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(Email, email);
        editor.putString(Contraseña, pass);
        editor.apply();
    }


}
