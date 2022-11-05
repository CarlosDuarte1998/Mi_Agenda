package com.ugb.miagenda;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ugb.miagenda.clases.Configuraciones;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ModificarContacto extends AppCompatActivity {
    EditText nombre, numero, correo;
    Button botonAgregar, botonRegresar, botonEliminar, botoncorreo, botonllamar;
    String id_contacto, nombre_contacto, telefono_contacto, NumLlamar, correo_contacto;
    Configuraciones objConfiguracion = new Configuraciones();
    String URL = objConfiguracion.urlWebServices;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_contactos);


        nombre = findViewById(R.id.edNombre);
        numero = findViewById(R.id.edNumero);
        botonAgregar = findViewById(R.id.agregar);
        botonRegresar = findViewById(R.id.BtnRegresar);
        botonEliminar = findViewById(R.id.BtnEliminars);
        botoncorreo=findViewById(R.id.btnCorreos);
        correo=findViewById(R.id.EditCorreos);

        botoncorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Se crean todos


                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{correo.getText().toString()});
                startActivity(emailIntent);

            }
        });

        botonllamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumLlamar=numero.getText().toString();
                //Se crean todos
                Intent i= new Intent(Intent.ACTION_CALL,Uri.parse(NumLlamar.toString()));
                Uri number = Uri.parse("tel:" + NumLlamar.toString());
                Intent intent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(intent);

            }



        });

        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar();
            }
        });




        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificar();
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
                eliminar();
            }
        });
    }

    private void modificar(){
        try{
            RequestQueue objetoPeticion = Volley.newRequestQueue(ModificarContacto.this);
            StringRequest peticion = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject objJSONResultado = new JSONObject(response.toString());
                        String estado = objJSONResultado.getString("estado");
                        if(estado.equals("1")){
                            Toast.makeText(ModificarContacto.this, "Contacto Modificado con exito", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ModificarContacto.this, "Error: "+ estado, Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ModificarContacto.this, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("accion","modificar");
                    params.put("nombre",nombre.getText().toString());
                    params.put("numero",numero.getText().toString());
                    params.put("correo",correo.getText().toString());
                    params.put("id_contacto",id_contacto);
                    return params;
                }
            };

            objetoPeticion.add(peticion);
        }catch (Exception error){
            Toast.makeText(ModificarContacto.this, "Error en tiempo de ejecucion: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void regresar(){
        Intent actividad = new Intent(ModificarContacto.this, MainActivity.class);
        startActivity(actividad);
        ModificarContacto.this.finish();
    }

    private void eliminar(){
        try{
            RequestQueue objetoPeticion = Volley.newRequestQueue(ModificarContacto.this);
            StringRequest peticion = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject objJSONResultado = new JSONObject(response.toString());
                        String estado = objJSONResultado.getString("estado");
                        if(estado.equals("1")){
                            Toast.makeText(ModificarContacto.this, "Contacto Eliminado con exito", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ModificarContacto.this, "Error: "+ estado, Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ModificarContacto.this, "Error: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String,String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("accion","eliminar");
                    params.put("id_contacto",id_contacto);
                    return params;
                }
            };

            objetoPeticion.add(peticion);
        }catch (Exception error){
            Toast.makeText(ModificarContacto.this, "Error en tiempo de ejecucion: "+ error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle valoresAdicionales = getIntent().getExtras();
        if(valoresAdicionales==null){
            Toast.makeText(ModificarContacto.this, "Debe enviar un ID de contacto", Toast.LENGTH_SHORT).show();
            id_contacto = "";
            regresar();
        }else{
            id_contacto = valoresAdicionales.getString("id_contacto");
            nombre_contacto = valoresAdicionales.getString("nombre");
            telefono_contacto = valoresAdicionales.getString("numero");
            correo_contacto = valoresAdicionales.getString("correo");
            verContacto();
        }
    }

    private void verContacto(){

        nombre.setText(nombre_contacto);
        numero.setText(telefono_contacto);
        correo.setText(correo_contacto);

    }


    }


