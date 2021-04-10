package com.example.petagram.Utilidades;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.petagram.Demo.DemonstracionUtilidadTraeJson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//// Se tiene que usar una tarea asincronica para las operaciones de red (como pedir jsons al servidor) la ui debe seguir ejecutandose
//// mientras se demora en traer los datos
public class EnviarJSON extends AsyncTask<String, Void, String > {

    private ProgressDialog progressDialog;
    private final Activity context;
    private final String Direccion;
    public AsyncResponse delegate;
    private String JsonAEnviar;


    // Constructor de la clase, pide la actividad en la que se ejecuta la clase para poder mostrar un progressDialog en ella
    // Y la direccion de url de los datos a traer, mas el json que se quiere enviar al servidor
    public EnviarJSON(Activity context, String url, String jsonAEnviar) {
        this.context = context;
        this.delegate = (AsyncResponse) this.context;
        Direccion = url;
        this.JsonAEnviar = jsonAEnviar;

    }

    // Esto se ejecuta despues de que se ejecuto la tarea, muestra un mensaje diciendo que termino de traer los datos
    //Tambien hace desaparecer el progress dialog, el parametro "Object o" es lo que se retorna en el metodo doInBackground
    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Toast.makeText(context, "Termine", Toast.LENGTH_SHORT).show();
        delegate.AlConseguirDato(s);
    }

    /// Esta es la tarea que se va a ejecutar, es el codigo que se encarga de hacer la peticion http para traer el json
    @Override
    protected String doInBackground(String...strings) {
        String Respuesta = null;
        HttpURLConnection conn;
        try {
            URL url = new URL(Direccion);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            Log.d("Milog",JsonAEnviar);
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(JsonAEnviar);
            os.flush();
            os.close();
            Log.d("Milog", String.valueOf(conn.getResponseCode()));

            if (conn.getResponseCode() == 200){
                InputStream contenido = conn.getInputStream();
                BufferedReader LectorFlujoDeDatos =  new BufferedReader(new InputStreamReader(contenido));
                StringBuilder buffer = new StringBuilder();
                String Linea = "";
                while ((Linea = LectorFlujoDeDatos.readLine()) != null) {
                    buffer.append(Linea).append("\n");
                }
                Respuesta = buffer.toString();
                Log.d("Milog",Respuesta);
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return Respuesta;
        }

    // Esto se ejecuta antes de comenzar la tarea, invoca un progress dialog en la actividad que se le paso a la clase como parametro
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando");
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                EnviarJSON.this.cancel(true);
            }
        });
    }

}

/// Esta es la tarea que se va a ejecutar, es el codigo que se encarga de hacer la peticion http para traer el json

