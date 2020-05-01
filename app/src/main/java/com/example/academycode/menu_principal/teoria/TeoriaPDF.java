package com.example.academycode.menu_principal.teoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.academycode.R;

public class TeoriaPDF extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutoriales_p_d_f);

    }


    //**************************************************
    //Abrir cardview de teoría
    //Fragment fragment = null;
    public void abrirJava(View view) {
        displayActivity();
    }

    public void abrirPython(View view) {
        displayActivity();
    }

    public void abrirC(View view) {
        displayActivity();
    }

    public void abrirJavaScript(View view) {
        displayActivity();
    }

    public void abrirPhp(View view) {
        displayActivity();
    }

    public void displayActivity(){
        startActivity(new Intent(this,ListadoLibrosT.class));
    }

    //**************************************************
}
