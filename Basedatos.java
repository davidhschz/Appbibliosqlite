package com.example.appbibliosqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Basedatos extends SQLiteOpenHelper {

    //Definir las variables con la instrucción necesaria para crear las tablas de la BD.
    String tblusuario = "Create Table usuario (email text primary key, nombre text, clave text, rol text)";
    String tblmaterial = "Create Table material (idMat text primary key, email text, nombre text, genero text)";

    public Basedatos( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tblusuario);
        db.execSQL(tblmaterial);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE usuario");
        db.execSQL(tblusuario);
        db.execSQL("DROP TABLE material");
        db.execSQL(tblmaterial);
    }
}
