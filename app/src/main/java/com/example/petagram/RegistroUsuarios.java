package com.example.petagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petagram.Modelo.Usuario;
import com.example.petagram.Utilidades.AsyncResponse;
import com.example.petagram.Utilidades.EnviarJSON;
import com.google.gson.Gson;

public class RegistroUsuarios extends AppCompatActivity implements AsyncResponse {

    EditText nombre_usuario;
    EditText email;
    EditText contrasena;
    EditText confContrasena;
    EditText telefono;
    Button confirmar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_registro_usuarios);

        nombre_usuario = findViewById (R.id.nombre_usuario);
        email = findViewById (R.id.email);
        contrasena = findViewById (R.id.contrasena);
        confContrasena = findViewById (R.id.confContrasena);
        telefono = findViewById (R.id.telefono);
        confirmar = findViewById (R.id.confirmar);

        confirmar.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if (Confirmar ()) {
                    Usuario usuario = new Usuario (nombre_usuario.getText().toString (), email.getText().toString (), contrasena.getText().toString (), telefono.getText().toString ());
                    Gson gson = new Gson ();
                    String datos = gson.toJson (usuario);
                    EnviarJSON datReg = new EnviarJSON (RegistroUsuarios.this, "https://aalza.pythonanywhere.com/usuario/registrousuariomovil", datos);
                    datReg.execute ();
                }

            }

        });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText ().toString ();
        return (!TextUtils.isEmpty (email) && Patterns.EMAIL_ADDRESS.matcher (email).matches ());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText ().toString ();
        return TextUtils.isEmpty (str);
    }



    Boolean Confirmar() {

        boolean validar = true;


        if (isEmpty (nombre_usuario)) {
            Toast t = Toast.makeText (this, "¡Debe ingresar el nombre para registrarse!", Toast.LENGTH_SHORT);
            t.show ();
            validar = false;
        }

        if (!isEmail (email)) {
            email.setError ("¡Ingrese un email valido!");
            validar = false;
        }

        if (isEmpty (contrasena)) {
            contrasena.setError ("¡Ingrese un una contraseña válida!");
            validar = false;
        }

        if (isEmpty (confContrasena)) {
            confContrasena.setError ("¡Ingrese un una contraseña válida!");
            validar = false;
        }

        if (isEmpty (telefono)) {
            telefono.setError ("¡Ingrese un número válido!");
            validar = false;
        }

        return validar;

    }


    public void Volver (View view){
        Intent intent = new Intent (RegistroUsuarios.this, LoginActivity.class);
        startActivity (intent);
    }

    @Override
    public void AlConseguirDato(String output) {

    }
}





