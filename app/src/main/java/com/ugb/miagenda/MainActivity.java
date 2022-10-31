package com.ugb.miagenda;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ugb.miagenda.clases.ConexionSQLite;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ErrorManager;

public class MainActivity extends AppCompatActivity {
    ConexionSQLite objConexion;
    final String NOMBRE_BASE_DATOS = "miagenda";
    Button botonAgregar, botonBuscar;
    ListView listaContactos;
    ArrayList<String> lista;
    ArrayAdapter adaptador;
    EditText txtCriterio;
    List<Integer> arregloID = new ArrayList<Integer>();

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objConexion = new ConexionSQLite(MainActivity.this,NOMBRE_BASE_DATOS,null,1);
        floatingActionButton=findViewById(R.id.floatAgregar);
        //botonAgregar = findViewById(R.id.btnAgregar);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ventana = new Intent(MainActivity.this, Registro.class);
                startActivity(ventana);
                MainActivity.this.finish();
            }
        });
      /*  botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        txtCriterio = findViewById(R.id.editText3);

        listaContactos = findViewById(R.id.lvContactos);
        lista = llenarLista();
        adaptador = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,lista);
        listaContactos.setAdapter(adaptador);

        listaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int idSeleccionado = arregloID.get(position);
                Intent ventanaModificar = new Intent(MainActivity.this, ModificarContacto.class);
                ventanaModificar.putExtra("id_contacto", idSeleccionado);
                startActivity(ventanaModificar);
                MainActivity.this.finish();
            }
        });

        botonBuscar = findViewById(R.id.btnBuscar);
        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lista = llenarLista();
                adaptador = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,lista);
                listaContactos.setAdapter(adaptador);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            lista.clear();
            lista = llenarLista();
            adaptador = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,lista);
            listaContactos.setAdapter(adaptador);
        }catch (Exception error){
            Toast.makeText(MainActivity.this, "Error: "+ error.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList llenarLista(){
        ArrayList<String> miLista = new ArrayList<>();
        SQLiteDatabase base = objConexion.getReadableDatabase();
        String criterio = txtCriterio.getText().toString();
        String consulta = "select id_contacto, nombre,telefono from contactos WHERE nombre LIKE '%"+ criterio +"%' order by nombre ASC";
        Cursor cadaRegistro = base.rawQuery(consulta,null);
        arregloID.clear();
        if(cadaRegistro.moveToFirst()){
            do{
                miLista.add(cadaRegistro.getString(1).toString() + " - "+ cadaRegistro.getString(2).toString());
                arregloID.add(cadaRegistro.getInt(0));
            }while(cadaRegistro.moveToNext());
        }
        return miLista;
    }

}
