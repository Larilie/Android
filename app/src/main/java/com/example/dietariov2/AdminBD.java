package com.example.dietariov2;

import android.app.PendingIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.dietariov2.utilidades.Utilidades;

import java.util.ArrayList;

public class AdminBD extends AppCompatActivity {

    private EditText querys;
    private ListView listView;
    private RadioButton usuarios,dietas;
    private Button boton;
    private RadioGroup tablas;
    private ArrayAdapter<String> ad;
    private ArrayList<String> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminbd);

        listView = (ListView) findViewById(R.id.listView);
        querys = (EditText) findViewById(R.id.edtQuerys);
        usuarios = (RadioButton) findViewById(R.id.rbUsuario);
        dietas = (RadioButton) findViewById(R.id.rbDietas);
        boton = (Button) findViewById(R.id.btnAccion);
        tablas = (RadioGroup) findViewById(R.id.radioGrupo);

        querys.setVisibility(View.INVISIBLE);

        //conexion sql
        final ConexionSQLite conn = new ConexionSQLite(getApplicationContext());

        //querys.setText(Utilidades.CREAR_TABLA_USUARIO + System.getProperty("line.separator") +
                        //Utilidades.CREAR_TABLA_DIETAS);
        querys.setText(Utilidades.selectUserbyName("leandro") + System.getProperty("line.separator") +
                       Utilidades.CREAR_TABLA_DIETAS + System.getProperty("line.separator") +
                       Utilidades.CREAR_TABLA_USUARIO + System.getProperty("line.separator") +
                       Utilidades.selectAll('u'));

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tablas.getCheckedRadioButtonId()==-1) {
                    Toast.makeText(getApplicationContext(), "Please select Gender", Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        datos = new ArrayList<String>();
                        if(dietas.isChecked()){
                            datos = conn.selectALLDietas();
                            if(datos != null && !datos.isEmpty()){
                                ad = new ArrayAdapter<String>(AdminBD.this,android.R.layout.simple_list_item_1,android.R.id.text1,datos);
                                listView.setAdapter(ad);
                            }else{
                                Toast.makeText(getApplicationContext(), "Vacio", Toast.LENGTH_LONG).show();
                            }
                        }else if(usuarios.isChecked()){
                            datos = conn.selectALLUser();
                            if(datos != null && !datos.isEmpty()){
                                ad = new ArrayAdapter<String>(AdminBD.this,android.R.layout.simple_list_item_1,android.R.id.text1,datos);
                                listView.setAdapter(ad);
                            }else{
                                Toast.makeText(getApplicationContext(), "Vacio", Toast.LENGTH_LONG).show();
                            }
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Error general", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }


}
