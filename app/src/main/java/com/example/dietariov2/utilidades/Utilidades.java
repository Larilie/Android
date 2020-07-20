package com.example.dietariov2.utilidades;

import java.util.Date;

public class Utilidades {
    /*Campos tabla usuarios*/
    private static final String TABLA_USU="usuarios";
    private static final String CAMPO_NOMBRE="nombre";
    private static final String CAMPO_PASSWORD="password";
    private static final String CAMPO_EMAIL="email";
    private static final String CAMPO_FOTO="foto";

    /*Campos tabla dietas*/
    private static final String TABLA_DIET="dietas";
    private static final String CAMPO_TIPO="tipo";
    private static final String CAMPO_FECHA="fecha";
    private static final String CAMPO_DESC="descripcion";

    /*Utilidades generales*/
    public static String selectAll(Character table) {
        switch(table){
            case 'u':
                return ("SELECT * FROM "+ TABLA_USU);
            case 'd':
                return ("SELECT * FROM " + TABLA_DIET);
            default:
                return "INCORRECT CHAR";
        }
    }

    /*Utilidades de tabla usuario*/
    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE "+ TABLA_USU +" ("+
            CAMPO_NOMBRE + " TEXT PRIMARY KEY, "+
            CAMPO_PASSWORD + " TEXT NOT NULL, "+
            CAMPO_EMAIL+" TEXT, "+
            CAMPO_FOTO+" TEXT )";

    public static String insertUser(String nombre, String password, String email, String image){
        return ("INSERT INTO"+" "+TABLA_USU
                +" VALUES( "
                +"'"+nombre+"',"
                +"'"+password+"',"
                +"'"+email+"',"
                +"'"+image+"')");
    }

    public static String selectUserbyName(String name) {
        return ("SELECT * FROM"+" "+TABLA_USU+" "
                +"WHERE "+ CAMPO_NOMBRE +"='"+ name+"'");
    }

    /*Utilidades de tabla dieta*/
    public static final String CREAR_TABLA_DIETAS = "CREATE TABLE "+TABLA_DIET+" ("+
            CAMPO_NOMBRE + " TEXT, "+
            CAMPO_TIPO + " TEXT, "+
            CAMPO_FECHA + " DATE, "+
            CAMPO_DESC+" TEXT,"+
            "PRIMARY KEY ("+CAMPO_NOMBRE+","+CAMPO_TIPO+","+CAMPO_FECHA+")," +
            "FOREIGN KEY("+CAMPO_NOMBRE+") REFERENCES "+TABLA_USU+"("+CAMPO_NOMBRE+")"+
            ")";

    public static String insertDiet(String nombre, String tipo, String fecha, String desc){
        return ("INSERT INTO "+TABLA_DIET
                +" VALUES( "
                +"'"+nombre+"',"
                +"'"+tipo+"',"
                +"'"+fecha+"',"
                +"'"+desc+"')");
    }

    public static String selectDiet(String name, String fecha) {
        return ("SELECT * FROM "+TABLA_DIET
                +" WHERE "+ CAMPO_NOMBRE +"='"+ name+"' AND" + CAMPO_FECHA +"='"+ fecha+"'");
    }
}
