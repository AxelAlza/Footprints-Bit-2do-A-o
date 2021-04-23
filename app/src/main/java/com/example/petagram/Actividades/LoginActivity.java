package com.example.petagram.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petagram.Modelo.JuntarDatosLogin;
import com.example.petagram.Modelo.Usuario;
import com.example.petagram.R;
import com.example.petagram.Actividades.RegistroUsuarios;
import com.example.petagram.Utilidades.AsyncResponse;
import com.example.petagram.Utilidades.EnviarJSON;
import com.example.petagram.Utilidades.RutasUrl;
import com.example.petagram.Utilidades.SesionDeUsuario;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity implements AsyncResponse {

    private EditText uiEmail;
    private EditText uiPassword;
    private Button uiLogin;
    private TextView uiNewPassword;
    private TextView uiRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uiEmail = findViewById(R.id.etEmail);
        uiPassword = findViewById(R.id.etPassword);
        uiLogin = findViewById(R.id.btnLogin);
        uiNewPassword = findViewById(R.id.tvNewPassword);
        uiRegistro = findViewById(R.id.tvRegistrarse);
        uiLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Aca Validamos correo y contraseña

                String inputEmail = uiEmail.getText().toString();
                String inputPassword = uiPassword.getText().toString();

                if (inputEmail.isEmpty() || inputPassword.isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Ingrese los datos correctamente", Toast.LENGTH_SHORT).show();

                } else {

                    Pattern pattern = Pattern
                            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

                    Matcher matcher = pattern.matcher(inputEmail);

                    if (matcher.find()) {

                        // Cambio de formato de datosLogin
                        JuntarDatosLogin datosLogin = new JuntarDatosLogin(inputEmail, inputPassword);
                        Gson Convertidor = new Gson();
                        String resultado = Convertidor.toJson(datosLogin);
                        Log.d("Convertidor", resultado);

                        // Enviando datosLogin al servidor como JSON
                        EnviarJSON enviarDatosLogin = new EnviarJSON(LoginActivity.this, RutasUrl.RutaDeProduccion + "/usuario/loginmovil", resultado);
                        enviarDatosLogin.setDelegate(LoginActivity.this);
                        enviarDatosLogin.execute();

                    } else {
                        Toast.makeText(LoginActivity.this, "Ingrese correctamente su Email", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        uiRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegistroUsuarios.class);
                startActivity(intent);
            }
        });

        uiNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lo mando a la actividad RecoverPassword
                Intent intent = new Intent(LoginActivity.this, RecoverPassword.class);
                startActivity(intent);


            }
        });
    }

    //Aca responde el servidor
    @Override
    public void AlConseguirDato(String output) {
        output = output.trim();
        if (output.length() > 5) {
            Gson gson = new Gson();
            Usuario user = gson.fromJson(output, Usuario.class);
            SesionDeUsuario.Login(user.getEmail(), user.getContrasena(), user.getTelefono(), LoginActivity.this);
            Intent intent_listado = new Intent(LoginActivity.this, ActividadListadoMascotas.class);
            startActivity(intent_listado);
            finish();
        }

        switch (output) {
            case "1":
                Toast.makeText(LoginActivity.this, "Su mail no esta registrado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, RegistroUsuarios.class);
                startActivity(intent);
                break;
            case "2":
                Toast.makeText(LoginActivity.this, "Su contraseña no es valida", Toast.LENGTH_SHORT).show();
        }
    }
}




