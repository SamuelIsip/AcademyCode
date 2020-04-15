package com.example.academycode.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.academycode.BaseDeDatos.SQLiteBaseDeDatos;
import com.example.academycode.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrarUsuario extends AppCompatActivity {

    SQLiteBaseDeDatos db;
    Button btnRegistrarU, btnVolverInicioS;
    EditText edTextEmail, edTextNUs, edTxtPasw1, edTxtPasw2, edTxtTelef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        db = new SQLiteBaseDeDatos(this);

        btnRegistrarU = findViewById(R.id.btnRegistrarU);
        btnVolverInicioS = findViewById(R.id.btnVolverIniciarSesion);
        edTextEmail = findViewById(R.id.edTxtNUs);
        edTextNUs = findViewById(R.id.edTxtNomUs);
        edTxtPasw1 = findViewById(R.id.edTxtPasw1);
        edTxtPasw2 = findViewById(R.id.edTxtPasw2);
        edTxtTelef = findViewById(R.id.edTxtTlf);

        //Click botón registrar
        btnRegistrarU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarUsuarioComprobado();
            }
        });

        //Botón volver a inicio
        btnVolverInicioS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),IniciarSesion.class));
                finish();
            }
        });

    }

    //*********************************
    //Métodos comprobación al registrar
    public void insertarUsuarioComprobado(){
        String emailU = edTextEmail.getText().toString();
        String passwrdU = edTxtPasw1.getText().toString();
        String nombreUsu = edTextNUs.getText().toString();
        String telefono = edTxtTelef.getText().toString();
        String passwrdU2 = edTxtPasw2.getText().toString();

        //Comprobar los datos introducidos, antes de guardar en BD
        if (emailU.equals("") || nombreUsu.equals("") || passwrdU.equals("") || passwrdU2.equals("")) {
            Toast.makeText(RegistrarUsuario.this, "¡Debe rellenar todos los campos obligatorios!", Toast.LENGTH_SHORT).show();
        } else {
            if (emailValido(emailU) && passValida(passwrdU)) {
                if (db.checkEmail(emailU) && db.checkUserName(nombreUsu)) {
                    if (passwrdU.equals(passwrdU2)) {
                        if (db.insertUsuario(emailU, passwrdU, nombreUsu, telefono)) //Se inserta en la BD
                            usuarioRegistrado();
                    }else
                        passwNoCoinciden();
                }else
                    Toast.makeText(RegistrarUsuario.this, "¡Nombre Usuario/Email ya existente!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void usuarioRegistrado(){
        Toast.makeText(RegistrarUsuario.this, "¡Registrado con éxito!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), IniciarSesion.class));
        finish();
    }

    public void passwNoCoinciden(){
        edTxtPasw2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        Toast.makeText(RegistrarUsuario.this, "¡Las contraseñas no coinciden!", Toast.LENGTH_SHORT).show();
    }

    public boolean emailValido(String email){

        boolean valido = false;
        //Patrón de email basado en el de Google(gmail)
        Pattern patronEmail = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");

        Matcher mEmail = patronEmail.matcher(email.toLowerCase());

        if (mEmail.find()){
            valido = true;
        }else{
            Toast.makeText(this, "¡Formato Email incorrecto!", Toast.LENGTH_SHORT).show();
            edTextEmail.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }

        return valido;

    }

    public boolean passValida(String pass){
        if (pass.length()>8)
            return true;
        else {
            Toast.makeText(this, "!Password demasiado corta!", Toast.LENGTH_SHORT).show();
            edTxtPasw1.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            return false;
        }
    }
    //*********************************

    @Override
    public void onBackPressed(){
        startActivity(new Intent(RegistrarUsuario.this, IniciarSesion.class));
        finish();
    }



}
