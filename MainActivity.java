package com.example.appbibliosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText email, contrasena;
    Button iniciarsesion;
    TextView registrarseaqui;
    Basedatos osqlite = new Basedatos(this,"BDBIBLIOTECA", null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.etemail);
        contrasena = findViewById(R.id.etpassword);
        iniciarsesion = findViewById(R.id.btniniciarsesion);
        registrarseaqui = findViewById(R.id.tvregistrarseaqui);

        iniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarsesionb(email.getText().toString().trim(), contrasena.getText().toString().trim());
            }
        });

        registrarseaqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Actusuario.class));
            }
        });

    }

    private void iniciarsesionb(String memail, String mcontra) {
        if (!email.getText().toString().isEmpty() && !contrasena.getText().toString().isEmpty()){
            SQLiteDatabase db = osqlite.getReadableDatabase();
            String query = "Select rol, email From usuario Where email = '" + memail + "' and clave = '" + mcontra + "'";
            Cursor cusuario = db.rawQuery(query, null);
            if (cusuario.moveToFirst()) {
                Intent oactmaterial = new Intent(getApplicationContext(), Actmaterial.class);
                //String mrol = cusuario.getString(0);
                //String memail1 = cusuario.getString(1);
                oactmaterial.putExtra("role",cusuario.getString(0));
                oactmaterial.putExtra("emaile", cusuario.getString(1));
                startActivity(oactmaterial);
            }
            else{
                Toast.makeText(this, "Usuario Inválido", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
        }
    }
}