package com.example.petagram.Utilidades;

import com.example.petagram.Modelo.Mascota;

public interface ListenerDeDatos {

    void OnSeAgregoMascota(int position);

    void OnSeModificoMascota(int position );

    void OnSeEliminoMascota(int position);
}
