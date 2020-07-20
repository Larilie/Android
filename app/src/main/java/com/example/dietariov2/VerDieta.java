package com.example.dietariov2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class VerDieta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_dieta);

        TextView fecha = (TextView) findViewById(R.id.Fecha);
        TextView desayuno = (TextView) findViewById(R.id.Desayuno);

        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        final String selectFecha = extra.getString("fecha");

    }
}
