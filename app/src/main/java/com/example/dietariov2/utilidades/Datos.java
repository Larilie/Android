package com.example.dietariov2.utilidades;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class Datos {

    private static String PREFS_KEY = "pref";

    public static boolean arrayVacio(Object[] datosUsuario){
        boolean vacio = true;
        for (Object ob : datosUsuario) {
            if (ob != null) {
                vacio = false;
                break;
            }
        }
        return vacio;
    }

    /*Funcion que retorna el dia actual en formato String*/
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    /*Funcion que retorna el dia en milisegundos*/
    public static long getMilliFromDate(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = formatter.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /*Funcion que retorna en formato date*/
    public static String getDateFromMilli (long date){
        Date auxDate = new Date(date);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(auxDate);
    }

    public static void guardarValor(Context context, String keyPref, String valor) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putString(keyPref, valor);
        editor.commit();
    }

    public static String leerValor(Context context, String keyPref) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
        return preferences.getString(keyPref, "");
    }

    public static String imageToB64(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bai = baos.toByteArray();
        return Base64.encodeToString(bai, Base64.DEFAULT);
    }

    public static Bitmap B64toBitmap (String base64){
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inMutable = true;
        return BitmapFactory.decodeByteArray(data, 0, data.length, opt);
    }

}
