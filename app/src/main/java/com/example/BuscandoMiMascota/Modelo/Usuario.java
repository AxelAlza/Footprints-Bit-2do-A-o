package com.example.BuscandoMiMascota.Modelo;

import android.widget.Button;
import android.widget.EditText;


public class Usuario {
    String nombre_usuario;
    String email;
    String contrasena;
    String telefono;

    public Usuario(String nombre_usuario, String email, String contrasena, String telefono) {
        this.nombre_usuario = nombre_usuario;
        this.email = email;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
