package com.ugb.miagenda;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ugb.miagenda.clases.ConexionSQLite;

import org.intellij.lang.annotations.Pattern;

public class Registro extends AppCompatActivity {
    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DATOS = "miagenda";
    EditText nombre, telefono, edCorreo;
    Button botonAgregar, botonRegresar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        objConexion = new ConexionSQLite(Registro.this, NOMBRE_BASE_DATOS, null, 1);
        nombre = findViewById(R.id.edNombre);
        telefono = findViewById(R.id.edNumero);
        edCorreo = findViewById(R.id.edCorreos);
        botonAgregar = findViewById(R.id.agregar);
        botonRegresar = findViewById(R.id.RegresarHome);


        //Evento (CLICK)
        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Registrar
                String Validacion1 = nombre.getText().toString();
                String Validacion2 = telefono.getText().toString();
                String Validacion3 = edCorreo.getText().toString();
                if (Validacion1.toString().isEmpty() || Validacion2.toString().isEmpty() || Validacion3.toString().isEmpty()) {
                    Toast.makeText(Registro.this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    String VlCorreo=edCorreo.getText().toString();
                    if(!VlCorreo.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(VlCorreo).matches()){
                        registrar();
                        Intent intent = new Intent(Registro.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(Registro.this, "Correo invalido", Toast.LENGTH_LONG).show();
                }
                }


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

    private void regresar() {
        Intent actividad = new Intent(Registro.this, MainActivity.class);
        startActivity(actividad);
        Registro.this.finish();
    }

    private void registrar() {


        try {
            SQLiteDatabase miBaseDatos = objConexion.getWritableDatabase();
            String comando = "INSERT INTO contactos (nombre,telefono,correo) VALUES ('" + nombre.getText() + "','" + telefono.getText() + "','" + edCorreo.getText() + "')";
            miBaseDatos.execSQL(comando);
            miBaseDatos.close();
            Toast.makeText(Registro.this, "Contacto guardado", Toast.LENGTH_LONG).show();
        } catch (Exception error) {
            Toast.makeText(Registro.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}