package com.example.dietariov2;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.dietariov2.utilidades.Utilidades;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ConexionSQLite extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dietario.db";

    public ConexionSQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_USUARIO);
        db.execSQL(Utilidades.CREAR_TABLA_DIETAS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS dietas");
        onCreate(db);
    }

    public boolean existsUser(String name){
        SQLiteDatabase db = getReadableDatabase();
        Cursor userCursor = db.rawQuery(Utilidades.selectUserbyName(name),null);
        if(userCursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public boolean insertUser(String name, String pass, String email, String image){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(Utilidades.insertUser(name,pass,email,image));
            return true;
        }catch(SQLException e){
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    public Object[] userInformation(String name){
        Object datos[] = new Object[4];
        datos[0] = " ";
        datos[1] = " ";
        datos[2] = " ";
        datos[3] = " ";
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor userCursor = db.rawQuery(Utilidades.selectUserbyName(name), null);
            if(userCursor.moveToFirst()){
                do{
                    datos[0] = userCursor.getString(userCursor.getColumnIndex("nombre"));
                    datos[1] = userCursor.getString(userCursor.getColumnIndex("password"));
                    datos[2] = userCursor.getString(userCursor.getColumnIndex("email"));
                    datos[3] = userCursor.getString(userCursor.getColumnIndex("foto"));
                } while (userCursor.moveToNext());
            }else{
                return datos;
            }
        }catch (SQLException e){
            Log.e(TAG, e.getMessage());
        }
        return datos;
    }

    public ArrayList<String> selectALLUser(){
        ArrayList<String> list = new ArrayList<>();
        list.clear();
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(Utilidades.selectAll('u'), null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                    //String password = cursor.getString(cursor.getColumnIndex("password"));
                    //String email = cursor.getString(cursor.getColumnIndex("email"));
                    //String foto = cursor.getString(cursor.getColumnIndex("foto"));
                    list.add(nombre);
                    //list.add(password);
                    //list.add(email);
                    //list.add(foto);
                    cursor.moveToNext();
                }
            }else{
                return list;
            }

        }catch (SQLException e){
            Log.e(TAG, e.getMessage());
        }
        db.close();
        return list;
    }

    public ArrayList<String> selectALLDietas (){
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery(Utilidades.selectAll('d'), null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                    String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
                    String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                    String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                    list.add(nombre);
                    list.add(tipo);
                    list.add(fecha);
                    list.add(descripcion);
                    cursor.moveToNext();
                }
            }else{
                return list;
            }
        }catch (SQLException e){
            Log.e(TAG, e.getMessage());
        }
        db.close();
        return list;
    }

    public boolean insertDiet(String name, String tipo, String date, String desc){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(Utilidades.insertDiet(name,tipo,date,desc));
            return true;
        }catch(SQLException e){
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    public boolean existsDiet(String name, String fecha){
        SQLiteDatabase db = getReadableDatabase();
        Cursor userCursor = db.rawQuery(Utilidades.selectDiet(name,fecha),null);
        if(userCursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }
}
