package com.example.petagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.petagram.Adaptadores.MascotaSimpleAdapter_Bitmap;
import com.example.petagram.Utilidades.FormateadorDeImagenes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class ActividadListadoMascotas extends AppCompatActivity {
    ImageView imageView;
    private ListView ListView_ListMascota;
    String imagen,nombre,especie,raza,color,genero,recompensa,tamano,descripcion,edad,ultima_posicion_conocida,fecha_denuncia,usuario;
    FloatingActionButton FabAgregarMascota;


    private static String JSON_URL="https://aalza.pythonanywhere.com/mascota/json/";
    //="https://aalza.pythonanywhere.com/mascota/json/";
    androidx.appcompat.widget.Toolbar TbListaMascotas;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    ArrayList<HashMap<String,Object>> mascotaList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadomascotas);
        imageView = findViewById(R.id.ImvMascota);
        FabAgregarMascota = findViewById(R.id.fabAgregarMascota);
        FabAgregarMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActividadListadoMascotas.this, ActividadPostearAnimal.class);
                startActivity(intent);
            }
        });
        mascotaList = new ArrayList<>();


        ListView_ListMascota = findViewById(R.id.listview);

        GetData getData = new GetData();
        getData.execute();
        ///Seteo el Recycler view con el adaptador
        //setRvListaMascotas();

        ///Seteo el Menu del toolbar
        SetearMenuDeToolbar();


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void SetearMenuDeToolbar( ) {
        TbListaMascotas = findViewById(R.id.toolbar);
        TbListaMascotas.inflateMenu(R.menu.menu);
    }

    public class GetData extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {

            StringBuilder current = new StringBuilder("{ListadoDeMascotas:");
            // StringBuilder current = new StringBuilder();

            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(in);

                    int data = isr.read();
                    while (data != -1){
                        current.append((char) data);
                        data= isr.read();


                    }

                    return current.toString();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection !=null){
                        urlConnection.disconnect();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();

            }
            return current.toString();

        }

        @Override
        protected void onPostExecute (String s){
            String arreglofinal = "}";
            String currentfinal = s.concat(arreglofinal);

            try {
                JSONObject jsonObject = new JSONObject(currentfinal);
                JSONArray jsonArray = jsonObject.getJSONArray("ListadoDeMascotas");

                for (int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    imagen = jsonObject1.getString("imagen");
                    nombre = jsonObject1.getString("nombre");
                    edad = jsonObject1.getString("edad");
                    especie = jsonObject1.getString("especie");
                    raza = jsonObject1.getString("raza");
                    color = jsonObject1.getString("color");
                    genero = jsonObject1.getString("genero");
                    usuario = jsonObject1.getString("usuario");
                    recompensa = jsonObject1.getString("recompensa");
                    descripcion = jsonObject1.getString("descripcion");
                    ultima_posicion_conocida = jsonObject1.getString("ultima_posicion_conocida");
                    fecha_denuncia = jsonObject1.getString("fecha_denuncia");
                    usuario = jsonObject1.getString("usuario");


                    //Convertir la imagen a bitmap

                    Bitmap bitmap = FormateadorDeImagenes.DesdeBase64(imagen);

                    //Listado con la imagen en formato Bitmap

                    HashMap<String,Bitmap> ListadoDeMascotas2 = new HashMap<>();
                    // HashMap<String,String> ListadoDeMascotas2 = new HashMap<>();
                    ListadoDeMascotas2.put("ImvMascota", bitmap);

                    // Crear un mapa pasando el bitmap y el resto de los string

                    HashMap<String, Object> ListadoMascotas = new HashMap<>();
                    ListadoMascotas.put("ImvMascota", bitmap);
                    ListadoMascotas.put("TvNombreMascota", nombre);
                    ListadoMascotas.put("TvEspecieMascota",especie);
                    ListadoMascotas.put("TvAgregarEdadMascota",edad);
                    ListadoMascotas.put("TvRazaMascota",raza);
                    ListadoMascotas.put("TvColor",color);
                    ListadoMascotas.put("TvGenero",genero);
                    ListadoMascotas.put("TvAgregarrecompensa",recompensa);
                    ListadoMascotas.put("TvAgregarTamano",tamano);
                    ListadoMascotas.put("TvAgregarDescripcionMascota",descripcion);
                    ListadoMascotas.put("tvultima_posicion_conocida",ultima_posicion_conocida);
                    ListadoMascotas.put("fecha_denuncia",fecha_denuncia);
                    ListadoMascotas.put("TVusuario",usuario);

                    mascotaList.add(ListadoMascotas);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListAdapter adapter = new MascotaSimpleAdapter_Bitmap(
                    ActividadListadoMascotas.this,
                    mascotaList,

                    R.layout.cardview_mascota,

                    new String[] {"ImvMascota","TvNombreMascota", "TvEspecieMascota","TvAgregarEdadMascota","TvRazaMascota","TvColor","TvGenero",
                            "TvAgregarrecompensa","TvAgregarTamano","TvAgregarDescripcionMascota","fecha_denuncia","TVusuario","tvultima_posicion_conocida"},
                    new int[] {R.id.ImvMascota,R.id.TvNombreMascota,R.id.TvEspecieMascota,R.id.TvAgregarEdadMascota,R.id.TvRazaMascota,
                            R.id.TvColor,R.id.TvGenero,R.id.TvAgregarrecompensa,R.id.TvAgregarTamano,R.id.TvAgregarDescripcionMascota,R.id.fecha_y_hora,
                            R.id.TVusuario,R.id.tvultima_posicion_conocida});



            ListView_ListMascota.setAdapter(adapter);

        }
    }
}
