package com.ugb.miagenda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ugb.miagenda.clases.ConexionSQLite;

public class ModificarContacto extends AppCompatActivity {
    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DATOS = "miagenda";
     String NumLlamar;
    EditText nombre, telefono;
    View parent_view;
    AlertDialog.Builder builder;
    int id_contacto;
    Button botonAgregar, botonRegresar, botonEliminar, botonllamar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_contactos);
        //Se crean las referencias
        objConexion = new ConexionSQLite(ModificarContacto.this, NOMBRE_BASE_DATOS,null,1);
        nombre = findViewById(R.id.edNombre);
        telefono = findViewById(R.id.edNumero);
        builder=new AlertDialog.Builder(this);
        botonAgregar = findViewById(R.id.agregar);
        botonRegresar = findViewById(R.id.BtnRegresar);
        botonEliminar = findViewById(R.id.BtnEliminars);
        botonllamar=findViewById(R.id.BtnLlamar);

        //Se crean todos los eventos Onclick
        botonllamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumLlamar=telefono.getText().toString();
                    //Se crean todos
              Intent i= new Intent(Intent.ACTION_CALL,Uri.parse(NumLlamar.toString()));
                        Uri number = Uri.parse("tel:" + NumLlamar.toString());
                        Intent intent = new Intent(Intent.ACTION_DIAL, number);
                        startActivity(intent);
            }



        });


        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificar();
                regresar();
            }
        });

        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar();
            }
        });

        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setTitle("¿Está seguro de que desea eliminar este contacto?");
                builder.setMessage("Se borrara de forma permanente");
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        eliminar();
                        Intent intent = new Intent(ModificarContacto.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancelar",null);
                builder.show();

            }


        });

    }

    private void modificar(){
        try{
            SQLiteDatabase miBaseDatos = objConexion.getWritableDatabase();
            String comando = "UPDATE contactos SET nombre='"+ nombre.getText() +"',telefono='"+ telefono.getText() +"' " +
                    "WHERE id_contacto='"+id_contacto+"'";
            miBaseDatos.execSQL(comando);
            miBaseDatos.close();
            Toast.makeText(ModificarContacto.this, "Contacto modificado", Toast.LENGTH_LONG).show();
        }catch (Exception error){
            Toast.makeText(ModificarContacto.this, "Error: "+ error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void regresar(){
        Intent actividad = new Intent(ModificarContacto.this, MainActivity.class);
        startActivity(actividad);
        ModificarContacto.this.finish();
    }

    private void eliminar(){
        try{
            SQLiteDatabase miBaseDatos = objConexion.getWritableDatabase();
            String comando = "DELETE FROM contactos WHERE id_contacto='"+id_contacto+"'";
            miBaseDatos.execSQL(comando);
            miBaseDatos.close();
            Toast.makeText(ModificarContacto.this, "Contacto eliminado", Toast.LENGTH_LONG).show();

        }catch (Exception error){
            Toast.makeText(ModificarContacto.this, "Error: "+ error.getMessage(), Toast.LENGTH_LONG).show();
        }



    }





    @Override
    protected void onResume() {
        super.onResume();
        Bundle valoresAdicionales = getIntent().getExtras();
        if(valoresAdicionales==null){
            Toast.makeText(ModificarContacto.this, "Debe enviar un ID de contacto", Toast.LENGTH_SHORT).show();
            id_contacto = 0;
            regresar();
        }else{
            id_contacto = valoresAdicionales.getInt("id_contacto");
            verContacto();
        }
    }

    private void verContacto() {
        SQLiteDatabase base = objConexion.getReadableDatabase();
        String consulta = "select id_contacto,nombre,telefono from contactos WHERE id_contacto='" + id_contacto + "'";
        Cursor cadaRegistro = base.rawQuery(consulta, null);
        if (cadaRegistro.moveToFirst()) {
            do {
                nombre.setText(cadaRegistro.getString(1));
                telefono.setText(cadaRegistro.getString(2));
            } while (cadaRegistro.moveToNext());
        }
    }
}