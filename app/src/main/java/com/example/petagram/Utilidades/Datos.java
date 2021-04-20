package com.example.petagram.Utilidades;

import com.example.petagram.Modelo.Mascota;

import java.util.ArrayList;

public class Datos {

    public static ArrayList<Mascota> mascotas;


    public static ArrayList<Mascota> getMascotas() {
        return mascotas;
    }

    public static void setMascotas(ArrayList<Mascota> mascotas) {
        Datos.mascotas = mascotas;
    }

    public static ArrayList<Mascota> getDatos(){
        return mascotas;
    }
}
