package com.example.petagram.Utilidades;

import android.app.Activity;
import android.location.Address;
import android.location.Location;
import android.util.Log;

import com.example.petagram.Actividades.ActividadListadoMascotas;
import com.example.petagram.Modelo.Mascota;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Datos implements AsyncResponse {

    private static Datos single_instance = null;


    private static ArrayList<Mascota> TodasLasMascotas;
    private static ArrayList<Mascota> ArrayEnUso = null;
    private static ListenerDeDatos listenerDeDatos;
    private Activity contexto;

    private Datos() {
    }

    public static Datos getInstance() {
        if (single_instance == null)
            single_instance = new Datos();

        return single_instance;
    }

    public static void AgregarMascota(Mascota mascota) {
        ArrayEnUso.add(mascota);
        TodasLasMascotas.add(mascota);
        int position = ArrayEnUso.indexOf(mascota);
        listenerDeDatos.OnSeAgregoMascota(position);
    }

    public static void ModificarMascota(Mascota mascota) {
        int position = ArrayEnUso.indexOf(mascota);
        listenerDeDatos.OnSeModificoMascota(position);
    }

    public static void EliminarMascota(Mascota mascota) {
        int llavePrimariaParaEliminar = mascota.getPk();
        String JsonParaEnviar = String.format("{\"pk\" : \"%d\"}", llavePrimariaParaEliminar);
        Log.d("Milog", "EliminarMascota " + JsonParaEnviar);

        EnviarJSON enviarJSON = new EnviarJSON(Datos.getInstance().contexto, RutasUrl.RutaDeProduccion + "/mascota/eliminarmascotamovil/", JsonParaEnviar);
        enviarJSON.setDelegate(Datos.getInstance());
        enviarJSON.execute();

        int position = ArrayEnUso.indexOf(mascota);
        TodasLasMascotas.remove(mascota);
        ArrayEnUso.remove(mascota);
        listenerDeDatos.OnSeEliminoMascota(position);
    }

    public static void InicializarDataSet(Activity contexto) {
        if (TodasLasMascotas == null) {
            Datos.getInstance().contexto = contexto;
            String JSON_URL = "https://aalza.pythonanywhere.com/mascota/json/";
            TraeJSON traeJSON = new TraeJSON(contexto, JSON_URL);
            traeJSON.setDelegate(Datos.getInstance());
            traeJSON.execute();
        }
    }


    public static void setListenerDeDatos(ListenerDeDatos listenerDeDatos) {
        Datos.listenerDeDatos = listenerDeDatos;
    }

    public static ArrayList<Mascota> getMascotasDelUsuario(final Activity contexto) {
        ArrayList<Mascota> ordenado = Lists.newArrayList(Collections2.filter(ArrayEnUso, new Predicate<Mascota>() {
                    @Override
                    public boolean apply(@NullableDecl Mascota input) {
                        return input.getUsuario().equals(SesionDeUsuario.ConseguirEmailDeSesion(contexto));
                    }
                })
        );
        ArrayEnUso = ordenado;
        return ArrayEnUso;
    }

    public static ArrayList<Mascota> getMascotasMasRecientes() {

        ArrayList<Mascota> ordenado = new ArrayList<>(getTodasLasMascotas());
        Collections.sort(ordenado, new Comparator<Mascota>() {
            @Override
            public int compare(Mascota o1, Mascota o2) {
                return DateParser.ParseDate(o1.getFecha_denuncia()).compareTo(DateParser.ParseDate(o2.getFecha_denuncia()));
            }
        }.reversed());
        ArrayEnUso = ordenado;
        return ArrayEnUso;
    }

    public static ArrayList<Mascota> getMascotasCercanasAmi() {

        Location MiUbicacion = DevuelveGps.getUbicacion(Datos.getInstance().contexto);
        ArrayList<Mascota> arrayList = new ArrayList<>(getTodasLasMascotas());
        if (MiUbicacion != null){

            final Location finalMiUbicacion = MiUbicacion;
            arrayList.sort(new Comparator<Mascota>() {
                @Override
                public int compare(Mascota o1, Mascota o2) {
                    Address ad1 = DevuelveGps.ConseguirLatyLong(o1.getUltima_posicion_conocida(), Datos.getInstance().contexto);
                    Location loc1 = new Location("");
                    loc1.setLatitude(ad1.getLatitude());
                    loc1.setLongitude(ad1.getLongitude());
                    Address ad2 = DevuelveGps.ConseguirLatyLong(o2.getUltima_posicion_conocida(), Datos.getInstance().contexto);
                    Location loc2 = new Location("");
                    loc2.setLatitude(ad2.getLatitude());
                    loc2.setLongitude(ad2.getLongitude());
                    return (int) (finalMiUbicacion.distanceTo(loc1) - finalMiUbicacion.distanceTo(loc2));
                }
            });
        }
        ArrayEnUso = arrayList;
        return ArrayEnUso;
    }

    public static ArrayList<Mascota> getTodasLasMascotas() {
        return TodasLasMascotas;
    }

    public static void setTodasLasMascotas(ArrayList<Mascota> todasLasMascotas) {
        TodasLasMascotas = todasLasMascotas;
    }


    @Override
    public void AlConseguirDato(String output) {

        output = output.trim();
        if (output.length() > 5) {
            Gson gson = new Gson();
            Mascota[] array = gson.fromJson(output, Mascota[].class);
            ArrayList<Mascota> mascotas = new ArrayList<>();
            Collections.addAll(mascotas, array);
            Datos.getInstance().setTodasLasMascotas(mascotas);
            ((ActividadListadoMascotas) Datos.getInstance().contexto).InicializarAdaptador();
        }
        if ("0".equals(output)) {
            Log.d("Milog", "AlConseguirDato: Se elimino la mascota en el servidor todo bien");
        }
    }
}
