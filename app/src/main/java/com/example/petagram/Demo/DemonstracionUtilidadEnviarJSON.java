package com.example.petagram.Demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.petagram.R;
import com.example.petagram.Utilidades.AsyncResponse;
import com.example.petagram.Utilidades.EnviarJSON;

import org.w3c.dom.Text;

///Implementar la interface AsyncResponse para traer el resultado de la clase EnviarJSON
public class DemonstracionUtilidadEnviarJSON extends AppCompatActivity implements AsyncResponse {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demonstracion_utilidad_enviar_j_s_o_n);
        tv = findViewById(R.id.Tv);

        ////Al instanciar esta clase se necesitan 3 parametros, la actividad en la que se esta ejecutando,
        ///una url y un String que se va a enviar al servidor
        EnviarJSON enviarJSON = new EnviarJSON
                (this, "https://aalza.pythonanywhere.com/mascota/agregarmascotamovil/", "{'mensaje' : 'Hola'}");

        ///Ejecutar la tarea y mandar el string al servidor
        enviarJSON.execute();
    }

   ///Cuando se termine la tarea de EnviarJSON hacer lo que se necesite con el resultado "Output", lo que responda el servidor es arbitrario
    ///Lo que responda es arbitrario y se va a definir cuando defina mejor la funcion de la ruta
    @Override
    public void AlConseguirDato(String output) {
        tv.setText(output);

    }
}