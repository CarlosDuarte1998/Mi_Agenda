package com.ugb.miagenda;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.ugb.miagenda.clases.ConexionSQLite;

public class RegistrarContacto extends AppCompatActivity {
  //  ConexionSQLite objConexion;
    //final String NOMBRE_BASE_DATOS = "miagenda";
    //EditText nombre, telefono;
    //Button botonAgregar, botonRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_contactos);
      //  objConexion = new ConexionSQLite(RegistrarContacto.this,NOMBRE_BASE_DATOS,null,1);

        /*nombre = findViewById(R.id.edNombre);
        telefono = findViewById(R.id.edNumero);
        botonAgregar = findViewById(R.id.agregar);
        botonRegresar = findViewById(R.id.RegresarHome);

        //Evento (CLICK)
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Registrar
                registrar();
            }
        });

        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Regresar
                regresar();
            }
        });
    }

    private void regresar(){
        Intent actividad = new Intent(RegistrarContacto.this,MainActivity.class);
        startActivity(actividad);
        RegistrarContacto.this.finish();
    }

    private void registrar(){
        try{
            SQLiteDatabase miBaseDatos = objConexion.getWritableDatabase();
            String comando = "INSERT INTO contactos (nombre,telefono) VALUES ('"+ nombre.getText() +"','"+ telefono.getText() +"')";
            miBaseDatos.execSQL(comando);
            miBaseDatos.close();
            Toast.makeText(RegistrarContacto.this, "Datos Registrados con éxito", Toast.LENGTH_LONG).show();
        }catch (Exception error){
            Toast.makeText(RegistrarContacto.this, "Error: "+ error.getMessage(), Toast.LENGTH_LONG).show();
        }*/
    }}