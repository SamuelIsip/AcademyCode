package com.example.academycode.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.academycode.R;

public class LoginInicial extends AppCompatActivity {

    Button btn_ir_iniciarsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_inicial);

        btn_ir_iniciarsesion = findViewById(R.id.ir_login);

        btn_ir_iniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),IniciarSesion.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
