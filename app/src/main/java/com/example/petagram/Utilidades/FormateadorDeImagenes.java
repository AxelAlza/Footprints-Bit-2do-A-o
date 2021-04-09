package com.example.petagram.Utilidades;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormateadorDeImagenes {

    public static String ABase64(Bitmap imagen)  {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] ImagenEnBytes = byteArrayOutputStream .toByteArray();
        String ImagenCodificada = Base64.encodeToString(ImagenEnBytes, Base64.DEFAULT);
        return ImagenCodificada;
    }

    public static Bitmap DesdeBase64(String base64) {
        byte[] BytesDeImagen = Base64.decode(base64, Base64.DEFAULT);
        Bitmap Imagen = BitmapFactory.decodeByteArray(BytesDeImagen, 0, BytesDeImagen.length);
        return Imagen;
    }


}
