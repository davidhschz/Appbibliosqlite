package com.example.appbibliosqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Actmaterial extends AppCompatActivity {

    TextView idmat, emailu, nombreMat;
    RadioButton lenguaje, basedatos;
    Button agregarm, buscarm, actualizarm, eliminarm, listarm;
    String ultimaBusqueda, genero;
    boolean admin = true;
    Basedatos osqlite = new Basedatos(this, "BDBIBLIOTECA", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actmaterial);
        idmat = findViewById(R.id.etidmatm);
        emailu = findViewById(R.id.etemailm);
        nombreMat = findViewById(R.id.etnombrematu);
        lenguaje = findViewById(R.id.rblenguajesm);
        basedatos = findViewById(R.id.rbbasedatosm);
        agregarm = findViewById(R.id.btnagregarm);
        buscarm = findViewById(R.id.btnbuscarm);
        actualizarm = findViewById(R.id.btnactualizarm);
        eliminarm = findViewById(R.id.btneliminarm);
        listarm = findViewById(R.id.btnlistarm);


        String mrol = (getIntent().getStringExtra("role"));
        String memaile = (getIntent().getStringExtra("emaile"));
        emailu.setText(memaile);
        if (mrol.equals("0")){
            admin = false;
        }
        seleccionarVistaUsuario(admin);

        eliminarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarMaterial();
            }
        });

        buscarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarMaterial(idmat.getText().toString().trim());
            }
        });

        listarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListadoMaterial.class));
            }
        });

        agregarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarMaterial();
            }
        });

        actualizarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarMaterial();
            }
        });

    }

    private void eliminarMaterial() {
        if (!idmat.getText().toString().isEmpty()){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Actmaterial.this);
            alertDialogBuilder.setMessage("Está seguro de eliminar el material con ID: " + idmat.getText().toString() + "?");
            alertDialogBuilder.setPositiveButton("Si",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SQLiteDatabase db = osqlite.getWritableDatabase();
                            db.execSQL("DELETE FROM material WHERE idMat = '" + idmat.getText().toString() +"'");
                            db.close();
                            Toast.makeText(Actmaterial.this, "Material eliminado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();    
        }
        else {
            Toast.makeText(this, "Debe ingresar la identificación del material", Toast.LENGTH_SHORT).show();
        }
        
    }

    private void actualizarMaterial() {
        if (!idmat.getText().toString().isEmpty() && !nombreMat.getText().toString().isEmpty() && (lenguaje.isChecked() || basedatos.isChecked())){
            SQLiteDatabase db = osqlite.getReadableDatabase();
            SQLiteDatabase db1 = osqlite.getWritableDatabase();
            genero = "0";
            if (basedatos.isChecked()){
                genero = "1";
            }
            if (idmat.getText().toString().equals(ultimaBusqueda.trim())) {
                db1.execSQL("UPDATE material SET nombre = '" + nombreMat.getText().toString().trim() + "', genero = '" + genero + "' WHERE  idMat = '" + idmat.getText().toString() + "'");
                db1.close();
                Toast.makeText(getApplicationContext(), "Material Actualizado correctamente...", Toast.LENGTH_SHORT).show();
            }
            else {
                String sql = "Select idMat From material Where idMat = '" + idmat.getText().toString() + "'";
                Cursor cusuario = db.rawQuery(sql, null);
                if (cusuario.moveToFirst())
                {
                    Toast.makeText(getApplicationContext(), "El material ya está registrado!! ...", Toast.LENGTH_SHORT).show();
                } else {
                    db1.execSQL("UPDATE material SET idMat = '" + idmat.getText().toString() + "', nombre = '" + nombreMat.getText().toString().trim() + "', genero = '" + genero + "' WHERE  idMat = '" + ultimaBusqueda + "'");
                    db1.close();
                    Toast.makeText(getApplicationContext(), "Material Actualizado correctamente...", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Debe ingresar todos los datos", Toast.LENGTH_SHORT).show();
        }

    }

    private void agregarMaterial() {
        if (!idmat.getText().toString().isEmpty() && !nombreMat.getText().toString().isEmpty() && (lenguaje.isChecked() || basedatos.isChecked())){
            SQLiteDatabase db = osqlite.getReadableDatabase();
            SQLiteDatabase db1 = osqlite.getWritableDatabase();
            String query = "Select idMat From material Where idMat = '" + idmat.getText().toString() + "'";
            Cursor cusuari = db.rawQuery(query, null);
            if (cusuari.moveToFirst()) {
                Toast.makeText(Actmaterial.this, "El ID ya está registrado", Toast.LENGTH_SHORT).show();
            }
            else{
                ContentValues cvMateriales = new ContentValues();
                try {
                    cvMateriales.put("idMat", idmat.getText().toString().trim());
                    cvMateriales.put("email", getIntent().getStringExtra("emaile"));
                    cvMateriales.put("nombre", nombreMat.getText().toString().trim());
                    if (lenguaje.isChecked()){
                        cvMateriales.put("genero", "0");
                    }
                    else{
                        cvMateriales.put("genero", "1");
                    }
                    db1.insert("material",null,cvMateriales);
                    db1.close();
                    Toast.makeText(Actmaterial.this, "Material agregado correctamente", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(Actmaterial.this, "Error " + e, Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            Toast.makeText(Actmaterial.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void buscarMaterial(String idMaterial) {
        if (!idmat.getText().toString().isEmpty()){
            SQLiteDatabase db = osqlite.getReadableDatabase();
            String query = "Select nombre, genero From material Where idMat = '" + idmat.getText().toString() + "'";
            Cursor cusuari = db.rawQuery(query, null);
            if (cusuari.moveToFirst()) {
                ultimaBusqueda = idmat.getText().toString().trim();
                nombreMat.setText(cusuari.getString(0));
                if (cusuari.getString(1).equals("0")){
                    lenguaje.setChecked(true);
                }
                else{
                    basedatos.setChecked(true);
                }
            }
            else{
                Toast.makeText(this, "ID Material no existe", Toast.LENGTH_SHORT).show();
            }    
        }
        else{
            Toast.makeText(this, "Debe ingresar la identificación del material", Toast.LENGTH_SHORT).show();
        }
    }

    private void seleccionarVistaUsuario(boolean admin) {
        if (admin){
            agregarm.setEnabled(true);
            buscarm.setEnabled(true);
            actualizarm.setEnabled(true);
            eliminarm.setEnabled(true);
            listarm.setEnabled(true);
        }
        else{
            agregarm.setEnabled(false);
            buscarm.setEnabled(true);
            actualizarm.setEnabled(false);
            eliminarm.setEnabled(false);
            listarm.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_crud, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (admin){
            switch (item.getItemId()) {
                case R.id.agregar:
                    agregarMaterial();
                    return true;
                case R.id.buscar:
                    buscarMaterial(idmat.getText().toString().trim());
                    return true;
                case R.id.actualizar:
                    actualizarMaterial();
                    return true;
                case R.id.eliminar:
                    eliminarMaterial();
                    return true;
                case R.id.listar:
                    startActivity(new Intent(getApplicationContext(), ListadoMaterial.class));
                    return true;
            }
        }
        else {
            switch (item.getItemId()) {
                case R.id.buscar:
                    buscarMaterial(idmat.getText().toString().trim());
                    return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }
}