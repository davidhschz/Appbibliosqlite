package com.example.appbibliosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListadoMaterial extends AppCompatActivity {

    ArrayList<String> arrayMaterial;
    ListView listaMaterial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_material);
        listaMaterial = findViewById(R.id.lvmaterial);
        String emailRecibido = (getIntent().getStringExtra("emailL"));
        cargarMaterial();

    }

    private void cargarMaterial() {
        arrayMaterial = listadoMateriales();
        ArrayAdapter<String> adaptermateriales = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,arrayMaterial);
        listaMaterial.setAdapter(adaptermateriales);
    }

    private ArrayList<String> listadoMateriales() {
        ArrayList<String> datos = new ArrayList<String>();
        Basedatos osqlite = new Basedatos(this, "BDBIBLIOTECA", null, 1);
        SQLiteDatabase db = osqlite.getReadableDatabase();
        String query = "Select idMat, email, nombre, genero From material";
        Cursor cMateriales = db.rawQuery(query,null);
        if (cMateriales.moveToFirst()){
            do {
                String generom = "Base de datos";
                if (cMateriales.getString(3).equals("0")){
                    generom = "Lenguaje";
                }
                String row = cMateriales.getString(0) + " " + cMateriales.getString(1) + " " + cMateriales.getString(2) + " " + generom;
                datos.add(row);
            }while (cMateriales.moveToNext());
            db.close();
        }

        return datos;
    }
}