package com.example.academycode.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.academycode.BaseDeDatos.SQLiteBaseDeDatos;
import com.example.academycode.MainActivity;
import com.example.academycode.R;

public class LoginApp extends AppCompatActivity {

    SQLiteBaseDeDatos db;
    EditText e1NUsuario, e2Pswrd;
    Button b1Registrar, b2Acceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_app);

        db = new SQLiteBaseDeDatos(this);

        e1NUsuario = findViewById(R.id.edTxtEmail);
        e2Pswrd = findViewById(R.id.edTxtPsw);
        b1Registrar = findViewById(R.id.btnRegistrar);
        b2Acceder = findViewById(R.id.btnAcceder);

        b2Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombreUsu = e1NUsuario.getText().toString();
                String passwU = e2Pswrd.getText().toString();
                
                if (!nombreUsu.equals("") || !passwU.equals("")) {
                    if (db.checkUserPasswd(nombreUsu, passwU)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else
                        Toast.makeText(LoginApp.this, "¡Usuario/Contraseña incorrecto!", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(LoginApp.this, "¡Debe rellenar todos los campos!", Toast.LENGTH_SHORT).show();
            }
        });

        b1Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrarUsuario.class);
                startActivity(intent);
            }
        });

    }
}
