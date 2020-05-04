package com.example.academycode.menu_principal.teoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academycode.R;
import com.example.academycode.login.IniciarSesion;

public class TeoriaPDF extends AppCompatActivity {

    private TextView tematica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoriales_p_d_f);

        if (!comprobarInternet()){
            Toast.makeText(TeoriaPDF.this, "Debe conectarse a Internet", Toast.LENGTH_SHORT).show();
        }

    }


    private boolean comprobarInternet(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null) {
            return false;
        }

        return true;
    }

    //**************************************************
    //Abrir cardview de teoría
    //Fragment fragment = null;
    public void abrirJava(View view) {
        tematica = findViewById(R.id.tematicaJava);
        displayActivity(tematica.getText().toString().trim().toLowerCase());
    }

    public void abrirPython(View view) {
        tematica = findViewById(R.id.tematicaPython);
        displayActivity(tematica.getText().toString().trim().toLowerCase());
    }

    public void abrirC(View view) {
        tematica = findViewById(R.id.tematicaCplus);
        displayActivity("cplusplus");
    }

    public void abrirJavaScript(View view) {
        tematica = findViewById(R.id.tematicaJavaScript);
        displayActivity(tematica.getText().toString().trim().toLowerCase());
    }

    public void abrirPhp(View view) {
        tematica = findViewById(R.id.tematicaPhp);
        displayActivity(tematica.getText().toString().trim().toLowerCase());
    }

    public void displayActivity(String tematica){
        Intent intent = new Intent(this,ListadoLibrosT.class);
        intent.putExtra("tematica",tematica);
        startActivity(intent);
    }

    //**************************************************
}
