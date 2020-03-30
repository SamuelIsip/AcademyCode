package com.example.academycode.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.academycode.BaseDeDatos.SQLiteBaseDeDatos;
import com.example.academycode.R;

public class LoginApp extends AppCompatActivity {

    SQLiteBaseDeDatos db;
    EditText e1Email, e2Pswrd;
    Button b1Registrar, b2Acceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_app);

        db = new SQLiteBaseDeDatos(this);

        e1Email = findViewById(R.id.edTxtEmail);
        e2Pswrd = findViewById(R.id.edTxtPsw);
        b1Registrar = findViewById(R.id.btnRegistrar);
        b2Acceder = findViewById(R.id.btnAcceder);

        b1Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailU = e1Email.getText().toString();
                String passwrdU = e2Pswrd.getText().toString();
                if (emailU.equals("") || passwrdU.equals("")){
                    Toast.makeText(LoginApp.this, "¡Debe rellenar todos los campos!", Toast.LENGTH_SHORT).show();
                }else{
                    if (db.checkEmail(emailU)){
                        if (db.insertUsuario(emailU,passwrdU)){
                            Toast.makeText(LoginApp.this, "¡Registrado con éxito!", Toast.LENGTH_SHORT).show();
                        }
                    }else
                        Toast.makeText(LoginApp.this, "¡Email ya existente!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
