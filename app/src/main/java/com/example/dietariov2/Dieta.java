package com.example.dietariov2;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dietariov2.utilidades.Datos;

public class Dieta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dieta);
        final EditText desayuno = findViewById(R.id.editDesayuno);
        final EditText colacion1 = findViewById(R.id.editColacion1);
        final EditText almuerzo = findViewById(R.id.editAlmuerzo);
        final EditText merienda = findViewById(R.id.editMerienda);
        final EditText colacion2 = findViewById(R.id.editColacion2);
        final EditText cena = findViewById(R.id.editCena);
        Button cargar = findViewById(R.id.btnCargar);
        Button cancelar = findViewById(R.id.btnCancelar);

        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        final String selectFecha = extra.getString("fecha");

        //conexion sql
        final ConexionSQLite conn = new ConexionSQLite(getApplicationContext());

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conn.insertDiet(Datos.leerValor(Dieta.this,"user"),"Desayuno",selectFecha,desayuno.getText().toString().trim());
                conn.insertDiet(Datos.leerValor(Dieta.this,"user"),"Colacion1",selectFecha,colacion1.getText().toString().trim());
                conn.insertDiet(Datos.leerValor(Dieta.this,"user"),"Almuerzo",selectFecha,almuerzo.getText().toString().trim());
                conn.insertDiet(Datos.leerValor(Dieta.this,"user"),"Merienda",selectFecha,merienda.getText().toString().trim());
                conn.insertDiet(Datos.leerValor(Dieta.this,"user"),"Colacion2",selectFecha,colacion2.getText().toString().trim());
                conn.insertDiet(Datos.leerValor(Dieta.this,"user"),"Cena",selectFecha,cena.getText().toString().trim());
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Deshabilito el boton back
    }
}
