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
    String nombre, raza,genero,edad,descripcion,ciudad,barrio;

    private static String JSON_URL="https://run.mocky.io/v3/5ab470aa-3d00-4899-90c6-62eeecf12b6f";
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

            //StringBuilder current = new StringBuilder("{ListadoDeMascotas:");
            StringBuilder current = new StringBuilder();

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
            // String arreglofinal = "}";
            //String currentfinal = s.concat(arreglofinal);

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("ListadoDeMascotas");

                for (int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    nombre = jsonObject1.getString("nombre");
                    raza = jsonObject1.getString("raza");
                    genero = jsonObject1.getString("genero");
                    edad = jsonObject1.getString("edad");
                    descripcion = jsonObject1.getString("descripcion");
                    ciudad = jsonObject1.getString("ciudad");
                    barrio = jsonObject1.getString("barrio");
                    //Hashmap
                    HashMap<String,String> ListadoDeMascotas = new HashMap<>();
                    ListadoDeMascotas.put("TvNombreMascota", nombre);
                    ListadoDeMascotas.put("TvRazaMascota",raza);
                    ListadoDeMascotas.put("genero",genero);
                    ListadoDeMascotas.put("edad",edad);
                    ListadoDeMascotas.put("descripcion",descripcion);
                    ListadoDeMascotas.put("ciudad",ciudad);
                    ListadoDeMascotas.put("barrio",barrio);

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
                    new String[] {"TvNombreMascota", "TvRazaMascota","genero","edad","descripcion","ciudad","barrio"},
                    new int[] {R.id.TvNombreMascota,R.id.TvRazaMascota});


            ListView_ListMascota.setAdapter(adapter);
        }
    }
}
