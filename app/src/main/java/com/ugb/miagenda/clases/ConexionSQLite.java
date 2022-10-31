package com.ugb.miagenda.clases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
// Se crea la clase con extencion de SQLite
public class ConexionSQLite extends SQLiteOpenHelper {
    // se crea una variable donde contenga la cadena de texto de script
    final String TABLA_CONTACTOS= "CREATE TABLE contactos(" +
            "id_contacto INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "nombre TEXT," +
            "telefono TEXT)";
    public ConexionSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    //En el onCreate le pasamos el parametro de la cadena de texto para la creacion de la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_CONTACTOS);
    }

    //Si ya existe la base de datos y lo que se solicita es una version; se ejecuta el metodo onUpgrate
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS contactos");
        onCreate(db);
    }
}
