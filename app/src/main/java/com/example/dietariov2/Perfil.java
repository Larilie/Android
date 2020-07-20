package com.example.dietariov2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dietariov2.utilidades.Datos;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        ImageView foto = findViewById(R.id.fotoPerfil);
        TextView nombre = findViewById(R.id.edtNombre);
        TextView mail = findViewById(R.id.edtEmail);

        //conexion sql
        final ConexionSQLite conn = new ConexionSQLite(getApplicationContext());

        //obtengo los datos del usuario
        Object[] datosUsuario = conn.userInformation(Datos.leerValor(Perfil.this, "user"));

        //seteo los datos del usuario
        nombre.setText(datosUsuario[0].toString());
        //mail.setText(datosUsuario[2].toString());
        if (!datosUsuario[3].toString().equals("NOFOTO")) {
            foto.setImageBitmap(Datos.B64toBitmap(datosUsuario[3].toString()));
        }
    }

}
