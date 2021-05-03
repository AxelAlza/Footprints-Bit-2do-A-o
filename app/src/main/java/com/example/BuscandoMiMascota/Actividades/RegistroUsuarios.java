package com.example.BuscandoMiMascota.Actividades;

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

import com.example.BuscandoMiMascota.Modelo.Usuario;
import com.example.BuscandoMiMascota.R;
import com.example.BuscandoMiMascota.Utilidades.AsyncResponse;
import com.example.BuscandoMiMascota.Utilidades.EnviarJSON;
import com.example.BuscandoMiMascota.Utilidades.RutasUrl;
import com.google.gson.Gson;


public class RegistroUsuarios extends AppCompatActivity implements AsyncResponse {

    EditText nombre_usuario, email, contrasena, confContrasena, telefono;
    Button confirmar;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        nombre_usuario = findViewById(R.id.nombre_usuario);
        email = findViewById(R.id.email);
        contrasena = findViewById(R.id.contrasena);
        confContrasena = findViewById(R.id.confContrasena);
        telefono = findViewById(R.id.telefono);
        confirmar = findViewById(R.id.confirmar);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Volver();
            }
        });

        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Confirmar()) {
                    Toast t = Toast.makeText(RegistroUsuarios.this, "Registro en proceso....", Toast.LENGTH_SHORT);
                    t.show();
                    Usuario usuario = new Usuario(nombre_usuario.getText().toString(), email.getText().toString(), contrasena.getText().toString(), telefono.getText().toString());
                    Gson gson = new Gson();
                    String datos = gson.toJson(usuario);
                    EnviarJSON datReg = new EnviarJSON(RegistroUsuarios.this, RutasUrl.RutaDeProduccion + "/usuario/registrousuariomovil", datos);
                    datReg.setDelegate(RegistroUsuarios.this);
                    datReg.execute();
                }
            }

        });
    }

    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }


    Boolean Confirmar() {
        boolean validar = true;
        // chequear para validar el nombre de usuario.
        if (isEmpty(nombre_usuario)) {

            nombre_usuario.setError("¡Debe ingresar el nombre para registrarse!");
            validar = false;
        }

        if (email.getText().length() == 0) {
            email.setError("Introduce un email");

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            Toast.makeText(getApplicationContext(), "Por favor, introduce una dirección de correo electrónico válida!!!", Toast.LENGTH_SHORT).show();
            validar = false;
        }


        // chequear para validar la contraseña.
        if (isEmpty(contrasena)) {
            contrasena.setError("¡Ingrese un una contraseña válida!");
            validar = false;
        } else if (contrasena.getText().length() < 6) {
            contrasena.setError(getResources().getString(R.string.error_invalid_password));
            validar = false;
        }

        // chequear para validar la confirmación de la contraseña.
        if (isEmpty(confContrasena)) {
            confContrasena.setError("¡Ingrese un una contraseña válida!");
            validar = false;
        } else if (confContrasena.getText().length() < 6) {
            confContrasena.setError(getResources().getString(R.string.error_invalid_password2));
            validar = false;
        }else

        //implementar que coincidan las contraseñas (FALTA)
        if(!confContrasena.getText().toString().equals(contrasena.getText().toString())) {
            confContrasena.setError("La contraseña ingresada no coincide!!! Vuelva a intentarlo!!!");
            validar = false;
        }


        // chequear para validar el número de teléfono.
        if (isEmpty(telefono)) {
            telefono.setError("¡Ingrese un número válido!");
            validar = false;
        }
        if (telefono.length() < 6 || telefono.length() > 13) {
            telefono.setError("¡Ha ingresado un número inválido!");
            validar = false;
        }
        return validar;
    }

    public void Volver() {
        Intent intent = new Intent(RegistroUsuarios.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void AlConseguirDato(String output) {
        output = output.trim();
        switch (output) {
            case "0":
                Toast.makeText(this, "Se registro su usuario con exito", Toast.LENGTH_SHORT).show();
                Volver();
                break;
            case "1":
                Toast.makeText(this, "Ya existe un usuario con ese nombre", Toast.LENGTH_SHORT).show();
                email.setError("Ya existe un usuario con ese nombre");
                break;
        }
    }

    public void confirmar(View view) {
    }
}





