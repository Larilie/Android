package com.example.dietariov2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dietariov2.entidades.Usuario;
import com.example.dietariov2.utilidades.Datos;

public class CrearUsuario extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_STORED = 2;
    private boolean errorUser, errorPass,subioImagen;
    private Button crear, btnFoto,cancelar;
    private EditText user,pass,passConfirm,email;
    private ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_usuario);
        user = (EditText) findViewById(R.id.edtUsuario);
        pass = (EditText) findViewById(R.id.edtPass1);
        passConfirm = (EditText) findViewById(R.id.edtPass);
        email = (EditText) findViewById(R.id.edtEmail);
        crear = (Button) findViewById(R.id.btnCrear);
        btnFoto = (Button) findViewById(R.id.btnSubirFoto);
        foto = (ImageView) findViewById(R.id.imageView);
        cancelar = (Button) findViewById(R.id.btnCancelar);
        errorUser = false;
        errorPass = false;
        subioImagen = false;

        //conexion sql
        final ConexionSQLite conn = new ConexionSQLite(getApplicationContext());

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //config de los EditText
        user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(conn.existsUser(user.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "El Usuario ya existe", Toast.LENGTH_LONG).show();
                        errorUser = true;
                    }else{
                        errorUser = false;
                    }
                }
            }
        });

        passConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(!hasFocus){
                    if(!pass.getText().toString().isEmpty() && !passConfirm.getText().toString().isEmpty()){
                        if(pass.getText().toString().compareTo(passConfirm.getText().toString())!=0){
                            Toast.makeText(getApplicationContext(), "Las contraseñas no son iguales", Toast.LENGTH_LONG).show();
                            errorPass=true;
                        }
                    }
                }else{
                    errorPass=false;
                }
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] opciones = {"Tomar Foto","Elegir de la galeria","Cancelar"};
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Opciones");
                builder.setItems(opciones, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int seleccion) {
                        if(opciones[seleccion] == "Tomar Foto"){
                            try{
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                }
                            }catch(Exception e){
                                Toast.makeText(getApplicationContext(),"Error en camara", Toast.LENGTH_SHORT).show();
                            }
                        }else if(opciones[seleccion] == "Elegir de la galeria"){
                            Intent storePicIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            storePicIntent.setType("image/*");
                            startActivityForResult(Intent.createChooser(storePicIntent, "Seleccionar una imagen"),REQUEST_IMAGE_STORED);
                        }else{
                            dialogInterface.dismiss();
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        crear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    if(!user.getText().toString().isEmpty() &&
                       !pass.getText().toString().isEmpty() &&
                       !passConfirm.getText().toString().isEmpty()){
                            if(!errorUser && !errorPass){
                                /*creamos usuario*/
                                String usuario = user.getText().toString();
                                String contraseña = pass.getText().toString();
                                String correo = email.getText().toString();
                                Usuario user = new Usuario(usuario,contraseña,correo);

                                //obtenemos bitmap del imageview
                                String fotoUser;
                                if(subioImagen){
                                    BitmapDrawable drawable = (BitmapDrawable) foto.getDrawable();
                                    Bitmap bitmap = drawable.getBitmap();
                                    fotoUser = Datos.imageToB64(bitmap);
                                }else{
                                    fotoUser = "NOFOTO";
                                }

                                //Insertamos usuario en la base de datos
                                conn.insertUser(user.getNombre(),user.getPassword(),user.getEmail(), fotoUser);
                                Toast.makeText(getApplicationContext(), "Se creo el usuario correctamente", Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Algunos datos son erroneos, verificar", Toast.LENGTH_LONG).show();
                            }
                    }else{
                        Toast.makeText(getApplicationContext(), "Faltan completar datos", Toast.LENGTH_LONG).show();
                    }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK){
            subioImagen = true;
            switch (requestCode){
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    foto.setImageBitmap(imageBitmap);
                    break;
                case REQUEST_IMAGE_STORED:
                    Uri path = data.getData();
                    foto.setImageURI(path);
                    break;
            }
        }
    }
}
