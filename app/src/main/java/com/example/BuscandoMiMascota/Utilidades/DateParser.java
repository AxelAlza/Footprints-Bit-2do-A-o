package com.example.BuscandoMiMascota.Utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");


    public static Date ParseDate(String input) {
        Date date = null;
        SimpleDateFormat dateFormat;
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            date = dateFormat.parse(input);
        } catch (ParseException e) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = dateFormat.parse(input);
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }

        }
        return date;
    }
}
