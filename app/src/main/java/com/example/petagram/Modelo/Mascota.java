package com.example.petagram.Modelo;

import java.io.Serializable;

public class Mascota implements Serializable {
    int pk, edad, recompensa;
    String usuariotelefono;
    String usuario;
    String nombre;
    String especie;
    String raza;
    String color;
    String genero;
    String tamano;
    String imagen;
    String descripcion;
    String ultima_posicion_conocida;
    String fecha_denuncia;


    public Mascota(int edad, int recompensa, String usuario, String nombre, String especie, String raza, String color, String genero, String tamano, String imagen, String descripcion, String ultima_posicion_conocida, String fecha_y_hora, String usuariotelefono) {
        this.edad = edad;
        this.recompensa = recompensa;
        this.usuario = usuario;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.color = color;
        this.genero = genero;
        this.tamano = tamano;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.ultima_posicion_conocida = ultima_posicion_conocida;
        this.fecha_denuncia = fecha_y_hora;
        this.usuariotelefono = usuariotelefono;
    }


    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getEdad() {
        return edad;
    }

    public String getUsuariotelefono() {
        return usuariotelefono;
    }

    public void setUsuariotelefono(String usuariotelefono) {
        this.usuariotelefono = usuariotelefono;
    }

    public String getFecha_denuncia() {
        return fecha_denuncia;
    }

    public void setFecha_denuncia(String fecha_denuncia) {
        this.fecha_denuncia = fecha_denuncia;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(int recompensa) {
        this.recompensa = recompensa;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUltima_posicion_conocida() {
        return ultima_posicion_conocida;
    }

    public void setUltima_posicion_conocida(String ultima_posicion_conocida) {
        this.ultima_posicion_conocida = ultima_posicion_conocida;
    }



    public void setFecha_y_hora(String fecha_y_hora) {
        this.fecha_denuncia = fecha_y_hora;
    }
}



