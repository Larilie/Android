package com.example.dietariov2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dietariov2.utilidades.Datos;


public class MainActivity extends AppCompatActivity {

    private EditText nombre,pass,multi;
    private Button login,admin;
    private Object datosUsuario[] = new Object[5];
    String usuario;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = (EditText) findViewById(R.id.etxtNombre);
        pass = (EditText) findViewById(R.id.etxtPass);
        login = (Button) findViewById(R.id.btnLogin);
        admin = (Button) findViewById(R.id.btnAdmin);
        multi = (EditText) findViewById(R.id.multiline);

        admin.setVisibility(View.INVISIBLE);
        multi.setVisibility(View.INVISIBLE);

        //Inicializamos datos para SharedPreferences
        usuario = Datos.leerValor(MainActivity.this,"user");
        password = Datos.leerValor(MainActivity.this,"pass");

        if(!usuario.trim().isEmpty() && !password.trim().isEmpty()){
            Intent llamarPrincipal = new Intent(MainActivity.this,PrincipalActivity.class);
            startActivity(llamarPrincipal);

        }

        //conexion sql
        final ConexionSQLite conn = new ConexionSQLite(getApplicationContext());

        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                if((nombre.getText().toString().equals("admin")) && (pass.getText().toString().equals("admin"))){
                    Intent llamarConfig = new Intent(MainActivity.this,AdminBD.class);
                    startActivity(llamarConfig);
                }else{
                    try{
                        datosUsuario = conn.userInformation(nombre.getText().toString());
                        if(!Datos.arrayVacio(datosUsuario)){
                            //Comparo la contraseña ingresada con la guardada en la base de datos
                            if(datosUsuario[1].toString().compareTo(pass.getText().toString())==0){
                                Datos.guardarValor(MainActivity.this,"user",nombre.getText().toString());
                                Datos.guardarValor(MainActivity.this,"pass",pass.getText().toString());
                                Intent llamarPrincipal = new Intent(MainActivity.this,PrincipalActivity.class);
                                startActivity(llamarPrincipal);
                            }else{
                                Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Usuario incorrecto o inexistente", Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent llamarAdmin = new Intent(MainActivity.this,AdminBD.class);
                startActivity(llamarAdmin);
            }
        });

}

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCrearUser:
                Intent llamarCrear = new Intent(MainActivity.this,CrearUsuario.class);
                startActivity(llamarCrear);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Inicializamos datos para SharedPreferences
        usuario = Datos.leerValor(MainActivity.this,"user");
        password = Datos.leerValor(MainActivity.this,"pass");

        if(!usuario.trim().isEmpty() && !password.trim().isEmpty()){
            Intent llamarPrincipal = new Intent(MainActivity.this,PrincipalActivity.class);
            startActivity(llamarPrincipal);

        }
    }

}
