package com.example.dietariov2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dietariov2.utilidades.Datos;


public class PrincipalActivity extends AppCompatActivity {

    private String fechaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        final Button modif = findViewById(R.id.btnModificar);
        Button cerrarSesion = findViewById(R.id.btnCerrarSesion);
        Button perfil = findViewById(R.id.btnPerfil);
        Button ver = findViewById(R.id.btnVer);
        final String usuario = Datos.leerValor(PrincipalActivity.this,"user");
        CalendarView calendario = findViewById(R.id.calendario);
        fechaSeleccionada= Datos.getCurrentDate();

        //conexion sql
        final ConexionSQLite conn = new ConexionSQLite(getApplicationContext());

        //Seteamos la fecha actual
        calendario.setDate(Datos.getMilliFromDate(fechaSeleccionada));

        //Deshabilitamos boton de cargar dieta si ya existe
        modif.setEnabled(true);
        if(conn.existsDiet(usuario,fechaSeleccionada)){
            modif.setEnabled(false);
        }

        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                fechaSeleccionada = dayOfMonth + "/" + month + "/" + year;
                if(conn.existsDiet(usuario,fechaSeleccionada)){
                   modif.setEnabled(false);
                }else{
                   modif.setEnabled(true);
                }
            }
        });

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent llamarVer = new Intent(PrincipalActivity.this,VerDieta.class);
                llamarVer.putExtra("fecha", fechaSeleccionada);
                startActivity(llamarVer);
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Datos.guardarValor(PrincipalActivity.this,"user"," ");
                Datos.guardarValor(PrincipalActivity.this,"pass"," ");
                Toast.makeText(getApplicationContext(), "Se cerro sesion correctamente", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent llamarModif= new Intent(PrincipalActivity.this,Dieta.class);
                llamarModif.putExtra("fecha", fechaSeleccionada);
                startActivity(llamarModif);
            }
        });

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent llamarPerfil = new Intent(PrincipalActivity.this,Perfil.class);
                startActivity(llamarPerfil);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Deshabilitar boton back
    }

}
