package com.example.academycode.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.academycode.BaseDeDatos.SQLiteBaseDeDatos;
import com.example.academycode.R;

public class RegistrarUsuario extends AppCompatActivity {

    SQLiteBaseDeDatos db;
    Button btnRegistrarU;
    EditText edTextEmail, edTextNUs, edTxtPasw1, edTxtPasw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        db = new SQLiteBaseDeDatos(this);

        btnRegistrarU = findViewById(R.id.btnRegistrarU);
        edTextEmail = findViewById(R.id.edTxtEmail);
        edTextNUs = findViewById(R.id.edTxtNUs);
        edTxtPasw1 = findViewById(R.id.edTxtPasw1);
        edTxtPasw2 = findViewById(R.id.edTxtPasw2);

        btnRegistrarU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailU = edTextEmail.getText().toString();
                String passwrdU = edTxtPasw1.getText().toString();
                String nombreUsu = edTextNUs.getText().toString();

                if (passwrdU.equals(edTxtPasw2.getText().toString())) {
                    if (emailU.equals("") || nombreUsu.equals("") || passwrdU.equals("") || edTxtPasw2.getText().toString().equals("")) {
                        Toast.makeText(RegistrarUsuario.this, "¡Debe rellenar todos los campos!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (db.checkEmail(emailU)) {
                            if (db.insertUsuario(emailU, passwrdU,nombreUsu)) {
                                Toast.makeText(RegistrarUsuario.this, "¡Registrado con éxito!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginApp.class);
                                startActivity(intent);
                            }
                        } else
                            Toast.makeText(RegistrarUsuario.this, "¡Email ya existente!", Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(RegistrarUsuario.this, "¡Las contraseñas no coinciden!", Toast.LENGTH_SHORT).show();
                
            }
        });



    }
}
