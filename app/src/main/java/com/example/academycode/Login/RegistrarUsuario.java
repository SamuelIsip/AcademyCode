package com.example.academycode.Login;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.academycode.BaseDeDatos.SQLiteBaseDeDatos;
import com.example.academycode.R;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.model.DefaultResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarUsuario extends AppCompatActivity {

    private SQLiteBaseDeDatos db;
    private Button btnRegistrarU, btnVolverInicioS;
    private EditText edTextEmail, edTextNUs, edTxtPasw1, edTxtPasw2, edTxtTelef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        db = new SQLiteBaseDeDatos(this);

        btnRegistrarU = findViewById(R.id.btnRegistrarU);
        btnVolverInicioS = findViewById(R.id.btnVolverIniciarSesion);
        edTextEmail = findViewById(R.id.edTxtEmail);
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

    public void insertarUsuarioComprobado(){
        String emailU = edTextEmail.getText().toString().trim();
        final String passwrdU = edTxtPasw1.getText().toString().trim();
        String nombreUsu = edTextNUs.getText().toString().trim();
        String telefono = edTxtTelef.getText().toString().trim();
        String passwrdU2 = edTxtPasw2.getText().toString().trim();

        //Comprobar los datos introducidos, antes de guardar en BD
        if (emailU.equals("") || nombreUsu.equals("") || passwrdU.equals("") || passwrdU2.equals("")) {
            Toast.makeText(RegistrarUsuario.this, "¡Debe rellenar todos los campos!", Toast.LENGTH_SHORT).show();
        } else {
            if (emailValido(emailU) && passValida(passwrdU)) {
                    if (passwrdU.equals(passwrdU2)) {

                        Call<DefaultResponse> call = RetrofitClient
                                .getInstance()
                                .getApi()
                                .createUser(emailU,passwrdU,nombreUsu,telefono);

                        call.enqueue(new Callback<DefaultResponse>() {
                            @Override
                            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                                DefaultResponse dr = response.body();

                                if (!dr.isErr()) {
                                    //if (response.code() == 201){
                                    Toast.makeText(getApplicationContext(), dr.getMsg(), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), IniciarSesion.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                /*}else if(response.code() == 422){
                                    Toast.makeText(getApplicationContext(), "Email/Nombre ya existente", Toast.LENGTH_SHORT).show();
                                }*/
                                }else
                                    Toast.makeText(RegistrarUsuario.this, dr.getMsg(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<DefaultResponse> call, Throwable t) {

                            }
                        });


                    }else
                        passwNoCoinciden();
                }else
                    Toast.makeText(RegistrarUsuario.this, "¡Nombre Usuario/Email ya existente!", Toast.LENGTH_SHORT).show();
            }

        }


    public void passwNoCoinciden(){
        //edTxtPasw2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        edTxtPasw2.setError("¡Las contraseñas no coinciden!");
        edTxtPasw2.requestFocus();
        //Toast.makeText(RegistrarUsuario.this, "¡Las contraseñas no coinciden!", Toast.LENGTH_SHORT).show();
    }

    public boolean emailValido(String email){
        boolean valido = false;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edTextEmail.setError("¡Formato Email incorrecto!");
            edTextEmail.requestFocus();
            valido = false;
        }else
            valido = true;

        return valido;

    }

    public boolean passValida(String pass){
        if (pass.length()>8)
            return true;
        else {
            edTxtPasw1.setError("¡Password demasiado corta!");
            edTxtPasw1.requestFocus();
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
