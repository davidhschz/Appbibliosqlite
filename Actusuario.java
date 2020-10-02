package com.example.appbibliosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class Actusuario extends AppCompatActivity {
    EditText emailu, nombreu, passwordu;
    RadioButton adminu, usuu;
    Button agregaru, buscaru, actualizaru,eliminaru, listaru;
    Basedatos osqlite = new Basedatos(this,"BDBIBLIOTECA", null,1);
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


        actualizaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mrol = "0";
                if (adminu.isChecked()){
                    mrol = "1";
                }
                actualizarusuario(emailu.getText().toString().trim(), nombreu.getText().toString().trim(),passwordu.getText().toString().trim(), mrol);
            }
        });


        listaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ListadoUsuarios.class));
            }
        });
        
        buscaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailu.getText().toString().isEmpty()){
                    buscarusuario(emailu.getText().toString().trim());    
                }
                else{
                    Toast.makeText(getApplicationContext(), "Ingresar Email a buscar", Toast.LENGTH_SHORT).show();
                }
            }
        });


        agregaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String memail = emailu.getText().toString();
                if (!emailu.getText().toString().isEmpty()&&!nombreu.getText().toString().isEmpty()&&!passwordu.getText().toString().isEmpty()&&(adminu.isChecked()||usuu.isChecked())){
                    //Instanciar un objeto de la clase SQLiteDatabase para manipular la base de datos.
                    //Para evitar un error (que no se almacenen dos emails iguales) primero leemos para comparar el dato ingresado.
                    /*1*/        SQLiteDatabase db = osqlite.getReadableDatabase();//Leer es aplicar SELECT.
                    //Crear una variable para la instrucción que permite buscar el email. Tabla cursor: Tabla en la memoria, no existe, es temporal.
                    /*2*/        String sql = "Select email From usuario Where email = '" + emailu.getText().toString()+"'";
                    //Crear tabla cursor en ram para almacenar los registros que devuelve la instrucción select.
                    /*3*/        Cursor cusuari = db.rawQuery(sql,null); /*raw quuery significa consulta.*/
                    //Verificar si la tabla cursor tiene al menos un registro.
                    /*4*/     if (cusuari.moveToFirst()){ // Si está ubicado en el primer registro de la tabla cursor
                        //Lo encontró el email.
                        Toast.makeText(getApplicationContext(), "El Email ya está en uso", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //Instanciar objeto de la base de datos para guardar el usuario en modo escritura.
                        SQLiteDatabase db1 = osqlite.getWritableDatabase();
                        try
                        {
                            //Contenedor de datos del usuario
                            ContentValues contusuario = new ContentValues(); //Se crea una tabla cursor, es diferente porque esta es de escritura.
                            //Una vez creada la tabla nos ponemos a asignar los campos que deben ser exactamente iguales a el campo de la tabla real.
                            contusuario.put("email", emailu.getText().toString().trim());//Trim corta los espacios vacios de los string.
                            contusuario.put("nombre",nombreu.getText().toString().trim());
                            contusuario.put("clave",passwordu.getText().toString().trim());
                            if (adminu.isChecked()){
                                contusuario.put("rol","1");
                            }
                            else{
                                contusuario.put("rol","2");
                            }
                            db1.insert("usuario",null,contusuario);
                            db1.close();
                            Toast.makeText(getApplicationContext(),"Usuario agregado correctamente...",Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Debe ingresar todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void buscarusuario(String emailbuscar) {
        SQLiteDatabase db = osqlite.getReadableDatabase();
        String sql = "Select nombre, clave, rol From usuario Where email = '" + emailbuscar +"'";
        Cursor cusuario = db.rawQuery(sql,null);
        if (cusuario.moveToFirst()){
            nombreu.setText(cusuario.getString(0));
            passwordu.setText(cusuario.getString(1));
            if (cusuario.getString(2).equals("1")){
                adminu.setChecked(true);
            }
            else{
                usuu.setChecked(true);
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "El usuario con el email " + emailbuscar + " No existe", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizarusuario(String emailact, String nombreact, String passwordact, String rolact) {
        SQLiteDatabase db = osqlite.getReadableDatabase();
        String sql = "Select email, rol From usuario Where email = '" + emailact +"'";
        Cursor cusuario = db.rawQuery(sql,null);
        if (!cusuario.moveToFirst()){
            SQLiteDatabase dbact = osqlite.getWritableDatabase();
            dbact.execSQL("UPDATE usuario SET email = '" + emailact +"', nombre= '" + nombreact +"', clave =  '" + passwordact +"', rol = '" + rolact +"'");
            Toast.makeText(getApplicationContext(), "Contacto actualizado correctamente", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "El nuevo Email está asignado", Toast.LENGTH_SHORT).show();
        }

    }
}