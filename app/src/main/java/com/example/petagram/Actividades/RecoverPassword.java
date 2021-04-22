package com.example.petagram.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petagram.Modelo.JuntarDatosLogin;
import com.example.petagram.R;
import com.example.petagram.Utilidades.AsyncResponse;
import com.example.petagram.Utilidades.EnviarJSON;
import com.example.petagram.Utilidades.RutasUrl;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecoverPassword extends AppCompatActivity implements AsyncResponse {

    private EditText uiEmailRecover;
    private Button uiBtnOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        uiEmailRecover = findViewById(R.id.etEmailRecover);
        uiBtnOk = findViewById(R.id.btnOk);


        uiBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aca valido el mail (expresion regular) y enviar los datos al servidor (falta ruta)

                String inputEmailRecover = uiEmailRecover.getText().toString();


                if (inputEmailRecover.isEmpty()) {

                    Toast.makeText(RecoverPassword.this, "Debe ingresar su correo", Toast.LENGTH_SHORT).show();

                } else {

                    Pattern pattern = Pattern
                            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");


                    Matcher mather = pattern.matcher(inputEmailRecover);

                    if (mather.find() == true) {

                        // Cambio de formato de datosLogin // PRONTO
                        Gson convertidor = new Gson();
                        JuntarDatosLogin EmailRecovery = new JuntarDatosLogin(inputEmailRecover,"vacia");
                        String resultado = convertidor.toJson(EmailRecovery);
                        Log.d("Convertidor", resultado);

                        // Enviando datosLogin al servidor como JSON // FALTA AGREGAR RUTA PARA TRABAJAR LOS DATOS
                        EnviarJSON enviarRecoverPassword = new EnviarJSON(RecoverPassword.this, RutasUrl.RutaDeProduccion+"/usuario/recoverpasswordmovil", resultado);
                        enviarRecoverPassword.execute();


                    } else {
                        Toast.makeText(RecoverPassword.this, "Ingrese correctamente su correo", Toast.LENGTH_SHORT).show();
                    }

                }

                //Toast.makeText(RecoverPassword.this, "Enviando peticion JSON", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Aca responde el servidor
    @Override
    public void AlConseguirDato(String output) {

        output = output.trim();

        switch (output){

            case "0":
                Toast.makeText(RecoverPassword.this, "El mail se ha enviado correctamente, revise su casilla de correo", Toast.LENGTH_SHORT).show();
                Intent intent_login = new Intent(RecoverPassword.this, LoginActivity.class);
                startActivity(intent_login);
                break;
            case "1":
                Toast.makeText(RecoverPassword.this, "El mail no esta registrado, registrese para ingresar", Toast.LENGTH_SHORT).show();
                Intent intent_registro = new Intent(RecoverPassword.this, RegistroUsuarios.class);
                startActivity(intent_registro);
                break;

        }

    }
}