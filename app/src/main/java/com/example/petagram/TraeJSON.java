package com.example.petagram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//// Se tiene que usar una tarea asincronica para las operaciones de red (como pedir jsons al servidor) la ui debe seguir ejecutandose
//// mientras se demora en traer los datos
public class TraeJSON extends AsyncTask {

    private ProgressDialog progressDialog;
    private Prueba context;
    String Direccion;
    TextView textView;

    // Constructor de la clase, pide la actividad en la que se ejecuta la clase para poder mostrar un progressDialog en ella
    // Y la direccion de url de los datos a traer
    public TraeJSON(Prueba context, String url) {
        this.context = context;
        Direccion = url;
        textView = context.json;
    }

    // Esto se ejecuta despues de que se ejecuto la tarea, muestra un mensaje diciendo que termino de traer los datos
    //Tambien hace desaparecer el progress dialog, el parametro "Object o" es lo que se retorna en el metodo doInBackground
    @Override
    protected void onPostExecute(Object o) {
        progressDialog.dismiss();
        Toast.makeText(context, "Termine", Toast.LENGTH_SHORT).show();
        textView.setText((String) o);
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
                TraeJSON.this.cancel(true);
            }
        });
    }

    /// Esta es la tarea que se va a ejecutar, es el codigo que se encarga de hacer la peticion http para traer el json
    @Override
    protected String doInBackground(Object[] objects) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL Url = new URL(Direccion);
            connection = (HttpURLConnection) Url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder buffer = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            return buffer.toString();

            ///Si hay errores:
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Si no hay conexion desconectarse
            if (connection != null) {
                connection.disconnect();
            }
            try {
                //Cerrar el lector
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;


    }
}
