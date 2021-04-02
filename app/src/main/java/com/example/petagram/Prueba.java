package com.example.petagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Prueba extends AppCompatActivity {

    public TextView json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        json = findViewById(R.id.textView);
        TraeJSON traeJSON = new TraeJSON(this, "https://aalza.pythonanywhere.com/mascota/json/");
        traeJSON.execute();

    }
}