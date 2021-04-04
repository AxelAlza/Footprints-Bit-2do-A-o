package com.example.petagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText uiEmail;
    private EditText uiPassword;
    private Button uiLogin;
    private TextView uiNewPassword;
    private TextView uiRegistro;

    //Esto es codigo duro
    //Credenciales credenciales = new Credenciales("admin@mail.com", "Admin1234");

    boolean isValid = false;

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

                String inputEmail = uiEmail.getText().toString();
                String inputPassword = uiPassword.getText().toString();

                //Aca Validamos correo y contraseña

                /*if(inputEmail.isEmpty() || inputPassword.isEmpty()){

                    Toast.makeText(LoginActivity.this, "Ingrese los datos correctamente", Toast.LENGTH_SHORT).show();
                }else{

                    isValid = validate(inputEmail, inputPassword);

                    if(!isValid){   //Aca se puede mandar directamente a registro si no estan los datos en la base //

                        Toast.makeText(LoginActivity.this, "Ingrese los datos correctamente", Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(LoginActivity.this, "Bienvenido a BuscandoMiMascota", Toast.LENGTH_SHORT).show();

                        //Agregamos el codigo para enviar a Listado de Mascotas //

                        Intent intent = new Intent(LoginActivity.this, SimulacionListadoMascotas.class);
                        startActivity(intent);
                    }
                }*/

                Intent intent = new Intent(LoginActivity.this, ActividadListadoMascotas.class);
                startActivity(intent);

            }
        });

        uiRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegistroUsuarios.class);
                startActivity(intent);
            }
        });
    }


    // Aca tenemos que vincular la clase del registro para validar //

    /*private boolean validate(String name, String password){

        if(name.equals(credenciales.getEmail()) && password.equals(credenciales.getPassword())){

            return true;
        }

        return false;
    }*/
}
