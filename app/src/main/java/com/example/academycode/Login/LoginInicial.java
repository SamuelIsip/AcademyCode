package com.example.academycode.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.academycode.R;

public class LoginInicial extends AppCompatActivity {

    private final int DURACION_SPLASH = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_inicial);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_inicial);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(LoginInicial.this, IniciarSesion.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);
    }
}
