package com.example.petagram.Modelo;

public class Mascota {
    int pk, edad, usuario, recompensa;
    String nombre;
    String especie;
    String raza;
    String color;
    String genero;
    String tamano;
    String imagen;
    String descripcion;
    String ultima_posicion_conocida;

    public Mascota(int pk, int edad, int usuario, int recompensa, String nombre, String especie, String raza, String color, String genero, String tamano, String imagen, String descripcion, String ultima_posicion_conocida) {
        this.pk = pk;
        this.edad = edad;
        this.usuario = usuario;
        this.recompensa = recompensa;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.color = color;
        this.genero = genero;
        this.tamano = tamano;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.ultima_posicion_conocida = ultima_posicion_conocida;
    }

    public String getUltima_posicion_conocida() {
        return ultima_posicion_conocida;
    }

    public void setUltima_posicion_conocida(String ultima_posicion_conocida) {
        this.ultima_posicion_conocida = ultima_posicion_conocida;
    }

    public int getPk() {
        return pk;
    }

    public int getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(int recompensa) {
        this.recompensa = recompensa;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTamaño() {
        return tamano;
    }

    public void setTamaño(String tamaño) {
        this.tamano = tamaño;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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
}

