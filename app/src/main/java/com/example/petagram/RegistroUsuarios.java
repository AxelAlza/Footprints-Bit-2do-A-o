package com.example.petagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petagram.Modelo.Usuario;
import com.example.petagram.Utilidades.AsyncResponse;
import com.example.petagram.Utilidades.EnviarJSON;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

public class RegistroUsuarios extends AppCompatActivity implements AsyncResponse {

    EditText nombre_usuario, email,contrasena, confContrasena, telefono;
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
        // chequear para validar el nombre de usuario.

        if (isEmpty (nombre_usuario)) {
            Toast t = Toast.makeText (this, "¡Debe ingresar el nombre para registrarse!", Toast.LENGTH_SHORT);
            t.show ();
            validar = false;
        }

        if (email.getText ().length () == 0) {
            Toast.makeText (getApplicationContext (), "Introduce un email", Toast.LENGTH_SHORT).show ();
            validar = false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher (email.getText ().toString ().trim ()).matches ()) {
            Toast.makeText (getApplicationContext (), "Por favor, introduce una dirección de correo electrónico válida!!!", Toast.LENGTH_SHORT).show ();
            validar = false;
        }

        // chequear para validar la contraseña.
        if (isEmpty (contrasena)) {
            contrasena.setError ("¡Ingrese un una contraseña válida!");
            validar = false;
        }else if (contrasena.getText().length() < 6) {
            contrasena.setError(getResources().getString(R.string.error_invalid_password));
            validar = false;
        } else  {
            validar = true;
        }

        // chequear para validar la confirmación de la contraseña.
        if (isEmpty (confContrasena)) {
            confContrasena.setError ("¡Ingrese un una contraseña válida!");
            validar = false;
        }else if (confContrasena.getText().length() < 6) {
            confContrasena.setError(getResources().getString(R.string.error_invalid_password2));
            validar = false;
        } else  {
            validar = true;
        }


        //implementar que coincidan las contraseñas (FALTA)



        // chequear para validar el número de teléfono.
        if (isEmpty (telefono)) {
            telefono.setError ("¡Ingrese un número válido!");
            validar = false;
        }else {
            if (telefono.length () < 6 || telefono.length () >13) {
             telefono.setError(getResources().getString(R.string.phone_error2));
             validar = true;
            }
        }
        Toast t = Toast.makeText (this, "Registro en proceso....", Toast.LENGTH_SHORT);
        t.show ();
        return validar;

    }

    // volver al Login
    public void Volver (View view){
        Intent intent = new Intent (RegistroUsuarios.this, LoginActivity.class);
        startActivity (intent);
    }

    @Override
    public void AlConseguirDato(String output) {

    }
}





