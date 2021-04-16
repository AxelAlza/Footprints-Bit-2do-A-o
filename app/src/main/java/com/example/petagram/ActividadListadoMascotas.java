package com.example.petagram;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

    private ListView ListView_ListMascota;
    String imagen,nombre,especie,raza,color,genero,tamano,descripcion,ultima_posicion_conocida,fecha_denuncia;

    private static String JSON_URL="https://aalza.pythonanywhere.com/mascota/json/";
    //="https://aalza.pythonanywhere.com/mascota/json/";
    androidx.appcompat.widget.Toolbar TbListaMascotas;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    ArrayList<HashMap<String,String>> mascotaList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listadomascotas);

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
                    especie = jsonObject1.getString("especie");
                    raza = jsonObject1.getString("raza");
                    color = jsonObject1.getString("color");
                    genero = jsonObject1.getString("genero");
                    tamano = jsonObject1.getString("tamano");
                    descripcion = jsonObject1.getString("descripcion");
                    //ultima_posicion_conocida = jsonObject1.getString("ultima_posicion_conocida");
                    fecha_denuncia = jsonObject1.getString("fecha_denuncia");
                    //Hashmap
                    HashMap<String,String> ListadoDeMascotas = new HashMap<>();

                    ListadoDeMascotas.put("ImvMascota", imagen);
                    ListadoDeMascotas.put("TvNombreMascota", nombre);
                    ListadoDeMascotas.put("TvEspecieMascota",especie);
                    ListadoDeMascotas.put("TvRazaMascota",raza);
                    ListadoDeMascotas.put("TvColor",color);
                    ListadoDeMascotas.put("TvGenero",genero);
                    ListadoDeMascotas.put("TvAgregarEdadMascota",tamano);
                    ListadoDeMascotas.put("TvAgregarDescripcionMascota",descripcion);
                   // ListadoDeMascotas.put("tvultima_posicion_conocida",ultima_posicion_conocida);
                    ListadoDeMascotas.put("fecha_y_hora",fecha_denuncia);
                    mascotaList.add(ListadoDeMascotas);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Displaying the results

            ListAdapter adapter = new SimpleAdapter(
                    ActividadListadoMascotas.this,
                    mascotaList,
                    R.layout.cardview_mascota,
                    new String[] {"ImvMascota","TvNombreMascota", "TvEspecieMascota","TvRazaMascota","TvColor","TvGenero",
                            "TvAgregarEdadMascota","TvAgregarDescripcionMascota","fecha_y_hora"},
                    new int[] {R.id.ImvMascota,R.id.TvNombreMascota,R.id.TvEspecieMascota,R.id.TvRazaMascota,
                            R.id.TvColor,R.id.TvGenero,R.id.TvAgregarEdadMascota,R.id.TvAgregarDescripcionMascota,
                            R.id.fecha_y_hora});


            ListView_ListMascota.setAdapter(adapter);
        }
    }
}
