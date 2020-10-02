package com.example.appbibliosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText email, contrasena;
    Button iniciarsesion;
    TextView registrarseaqui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.etemail);
        contrasena = findViewById(R.id.etpassword);
        iniciarsesion = findViewById(R.id.btniniciarsesion);
        registrarseaqui = findViewById(R.id.tvregistrarseaqui);

        registrarseaqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Actusuario.class));
            }
        });

    }
}