package com.example.academycode.menu_principal.ejercicios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.academycode.R;

public class EjerciciosPDF extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios_p_d_f);
    }


    public void btnAtras(View view) {
        onBackPressed();
    }
}
