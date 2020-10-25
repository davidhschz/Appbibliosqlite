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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Actusuario extends AppCompatActivity {
    EditText emailu, nombreu, passwordu;
    RadioButton adminu, usuu;
    Button agregaru, buscaru, actualizaru, eliminaru, listaru;
    String emailanterior, emailnuevo;
    Basedatos osqlite = new Basedatos(this, "BDBIBLIOTECA", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actusuario);
        emailu = findViewById(R.id.etemailu);
        nombreu = findViewById(R.id.etnombreu);
        passwordu = findViewById(R.id.etpasswordu);
        adminu = findViewById(R.id.rbadminu);
        usuu = findViewById(R.id.rbusuu);
        agregaru = findViewById(R.id.btnagregaru);
        buscaru = findViewById(R.id.btnbuscaru);
        actualizaru = findViewById(R.id.btnactualizaru);
        eliminaru = findViewById(R.id.btneliminaru);
        listaru = findViewById(R.id.btnlistaru);


        eliminaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarusuario();
                /*AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Actusuario.this);
                alertDialogBuilder.setMessage("Está seguro de eliminar el usuario: " + nombreu.getText().toString());
                alertDialogBuilder.setPositiveButton("Sí",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                SQLiteDatabase obde = osqlite.getWritableDatabase();
                                obde.execSQL("DELETE FROM usuario WHERE email = '"+ emailu.getText().toString()+"'");
                                Toast.makeText(getApplicationContext(),"Usuario Eliminado correctamente...",Toast.LENGTH_SHORT).show();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();*/
            }
        });

        actualizaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String mrol = "0";
                if (adminu.isChecked()) {
                    mrol = "1";
                }*/
                actualizarusuario(emailu.getText().toString().trim(), nombreu.getText().toString().trim(), passwordu.getText().toString().trim());
            }
        });


        listaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListadoUsuarios.class));
            }
        });

        buscaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailu.getText().toString().isEmpty()) {
                    buscarusuario(emailu.getText().toString().trim());
                } else {
                    Toast.makeText(getApplicationContext(), "Ingresar Email a buscar", Toast.LENGTH_SHORT).show();
                }
            }
        });


        agregaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarusuario();
                /*String memail = emailu.getText().toString();
                if (!emailu.getText().toString().isEmpty() && !nombreu.getText().toString().isEmpty() && !passwordu.getText().toString().isEmpty() && (adminu.isChecked() || usuu.isChecked())) {

                    SQLiteDatabase db = osqlite.getReadableDatabase();
                    String sql = "Select email From usuario Where email = '" + emailu.getText().toString() + "'";
                    Cursor cusuari = db.rawQuery(sql, null);
                    if (cusuari.moveToFirst()) {
                        Toast.makeText(getApplicationContext(), "El Email ya está en uso", Toast.LENGTH_SHORT).show();
                    } else {
                        SQLiteDatabase db1 = osqlite.getWritableDatabase();
                        try {
                            ContentValues contusuario = new ContentValues();
                            contusuario.put("email", emailu.getText().toString().trim());
                            contusuario.put("nombre", nombreu.getText().toString().trim());
                            contusuario.put("clave", passwordu.getText().toString().trim());
                            if (adminu.isChecked()) {
                                contusuario.put("rol", "1");
                            } else {
                                contusuario.put("rol", "0");
                            }
                            db1.insert("usuario", null, contusuario);
                            db1.close();
                            Toast.makeText(getApplicationContext(), "Usuario agregado correctamente...", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Debe ingresar todos los datos", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    private void buscarusuario(String emailbuscar) {
        if (!emailbuscar.isEmpty()) {
            SQLiteDatabase db = osqlite.getReadableDatabase();
            String sql = "Select nombre, clave, rol From usuario Where email = '" + emailbuscar + "'";
            Cursor cusuario = db.rawQuery(sql, null);
            if (cusuario.moveToFirst()) {
                nombreu.setText(cusuario.getString(0));
                passwordu.setText(cusuario.getString(1));
                emailanterior = emailbuscar;
                if (cusuario.getString(2).equals("1")) {
                    adminu.setChecked(true);
                } else {
                    usuu.setChecked(true);
                }
            } else {
                Toast.makeText(getApplicationContext(), "El usuario con el email " + emailbuscar + " No existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Ingresar Email a buscar", Toast.LENGTH_SHORT).show();
        }

    }

    private void actualizarusuario(String emailact, String nombreact, String passwordact) {
        String mrol = "0";
        if (adminu.isChecked()) {
            mrol = "1";
        }
        if (!emailu.getText().toString().isEmpty() && !nombreu.getText().toString().isEmpty() && !passwordu.getText().toString().isEmpty() && (adminu.isChecked() || usuu.isChecked())){
            emailnuevo = emailu.getText().toString().trim();
            emailnuevo = emailact.trim();
            SQLiteDatabase dbact = osqlite.getWritableDatabase();
            SQLiteDatabase db = osqlite.getReadableDatabase();
            if (emailnuevo.equals(emailanterior.trim())) {
                dbact.execSQL("UPDATE Usuario SET nombre = '" + nombreact + "', clave = '" + passwordact + "', rol = '" + mrol + "' WHERE  email = '" + emailanterior + "'");
                Toast.makeText(getApplicationContext(), "Contacto Actualizado correctamente...", Toast.LENGTH_SHORT).show();
            } else {
                String sql = "Select email From Usuario Where email = '" + emailnuevo + "'";
                Cursor cusuario = db.rawQuery(sql, null);
                if (cusuario.moveToFirst())
                {
                    Toast.makeText(getApplicationContext(), "El email está asignado a otro usuario!! ...", Toast.LENGTH_SHORT).show();
                } else {
                    dbact.execSQL("UPDATE Usuario SET email = '" + emailnuevo + "', nombre = '" + nombreact + "', clave = '" + passwordact + "', rol = '" + mrol + "' WHERE  email = '" + emailanterior + "'");
                    Toast.makeText(getApplicationContext(), "Contacto Actualizado correctamente...", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Debe ingresar todos los datos", Toast.LENGTH_SHORT).show();
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
        switch (item.getItemId()) {
            case R.id.agregar:
                agregarusuario();
                return true;
            case R.id.buscar:
                buscarusuario(emailu.getText().toString().trim());
                return true;
            case R.id.actualizar:
                actualizarusuario(emailu.getText().toString().trim(), nombreu.getText().toString().trim(), passwordu.getText().toString().trim());
                return true;
            case R.id.eliminar:
                eliminarusuario();
                return true;
            case R.id.listar:
                startActivity(new Intent(getApplicationContext(), ListadoUsuarios.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void eliminarusuario() {
        if (!emailu.getText().toString().isEmpty()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Actusuario.this);
            alertDialogBuilder.setMessage("Está seguro de eliminar el usuario: " + nombreu.getText().toString());
            alertDialogBuilder.setPositiveButton("Sí",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            SQLiteDatabase obde = osqlite.getWritableDatabase();
                            obde.execSQL("DELETE FROM usuario WHERE email = '" + emailu.getText().toString() + "'");
                            Toast.makeText(getApplicationContext(), "Usuario Eliminado correctamente...", Toast.LENGTH_SHORT).show();
                        }
                    });

            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else{
            Toast.makeText(this, "Debe ingresar el Email del usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarusuario() {
        String memail = emailu.getText().toString();
        if (!emailu.getText().toString().isEmpty() && !nombreu.getText().toString().isEmpty() && !passwordu.getText().toString().isEmpty() && (adminu.isChecked() || usuu.isChecked())) {

            SQLiteDatabase db = osqlite.getReadableDatabase();
            String sql = "Select email From usuario Where email = '" + emailu.getText().toString() + "'";
            Cursor cusuari = db.rawQuery(sql, null);
            if (cusuari.moveToFirst()) {
                Toast.makeText(getApplicationContext(), "El Email ya está en uso", Toast.LENGTH_SHORT).show();
            } else {
                SQLiteDatabase db1 = osqlite.getWritableDatabase();
                try {
                    ContentValues contusuario = new ContentValues();
                    contusuario.put("email", emailu.getText().toString().trim());
                    contusuario.put("nombre", nombreu.getText().toString().trim());
                    contusuario.put("clave", passwordu.getText().toString().trim());
                    if (adminu.isChecked()) {
                        contusuario.put("rol", "1");
                    } else {
                        contusuario.put("rol", "0");
                    }
                    db1.insert("usuario", null, contusuario);
                    db1.close();
                    Toast.makeText(getApplicationContext(), "Usuario agregado correctamente...", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Debe ingresar todos los datos", Toast.LENGTH_SHORT).show();
        }
    }
}
