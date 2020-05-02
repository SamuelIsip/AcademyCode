package com.example.academycode.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.academycode.almacenamiento.SQLiteBaseDeDatos;
import com.example.academycode.menu_principal.MenuPrincipal;
import com.example.academycode.R;
import com.example.academycode.almacenamiento.SharedPrefManager;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.model.LoginResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IniciarSesion extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    SQLiteBaseDeDatos db;
    EditText e1EmailU, e2Pswrd;
    Button b1Registrar, b2Acceder, btnGoogle;

    //Variable para Google
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private static final int SIGN_IN = 1;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_app);

        db = new SQLiteBaseDeDatos(this);

        e1EmailU = findViewById(R.id.edTxtEmail);
        e2Pswrd = findViewById(R.id.edTxtPsw);
        b1Registrar = findViewById(R.id.btnRegistrar);
        b2Acceder = findViewById(R.id.btnAcceder);
        btnGoogle = findViewById(R.id.inicar_google);

        //permiso de escritura en memoria
        if(ContextCompat.checkSelfPermission(IniciarSesion.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(IniciarSesion.this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        //Botón Iniciar Sesión normal
        b2Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailUsu = e1EmailU.getText().toString();
                String passwU = e2Pswrd.getText().toString();

                if (emailUsu.isEmpty()) {
                    e1EmailU.setError("Email requerido");
                    e1EmailU.requestFocus();
                    return;
                }

                if (passwU.isEmpty()) {
                    e2Pswrd.setError("Password requerido");
                    e2Pswrd.requestFocus();
                    return;
                }

                if (passwU.length() < 8) {
                    e2Pswrd.setError("Password debe ser > 8");
                    e2Pswrd.requestFocus();
                    return;
                }

                if (!comprobarInternet()){
                    Toast.makeText(IniciarSesion.this, "Debe conectarse a Internet", Toast.LENGTH_SHORT).show();
                }

                dialog = ProgressDialog.show(IniciarSesion.this, "",
                        "Conectando con el servidor...", true);

                Call<LoginResponse> call = RetrofitClient
                        .getInstance().getApi().userLogin(emailUsu, passwU);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();

                        if (!loginResponse.isError()){

                            //Si existe, loguearlo, guardarlo
                            SharedPrefManager.getInstance(IniciarSesion.this)
                                    .saveUser(loginResponse.getUser());
                            Intent intent = new Intent(IniciarSesion.this, MenuPrincipal.class);
                            startActivity(intent);
                            dialog.dismiss();
                            finish();

                        }else{
                            dialog.dismiss();
                            cerrarSesionGoogle();
                            btnGoogle.setBackgroundResource(R.drawable.btn_redondeado_rojo);
                            btnGoogle.setTextColor(Color.parseColor("#FFFFFF"));
                            btnGoogle.setEnabled(true);
                            Toast.makeText(IniciarSesion.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });

            }
        });

        //Botón crear usuario
        b1Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesionGoogle();
                Intent intent = new Intent(getApplicationContext(), RegistrarUsuario.class);
                startActivity(intent);
                finish();
            }
        });

        //Declarar variables para acceso con Google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        //Iniciar seión con Google
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN);
            }
        });



    }

    //******************************
    //Métodos para Acceso con Google
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void cerrarSesionGoogle(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()){ //Si se cierra sesion

                        }else{
                            Toast.makeText(getApplicationContext(),"Session not close", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    //Se selecciona cuenta, si el login es correcto se accede
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        GoogleSignInAccount account;

        if (requestCode == SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess())
            {
                account=result.getSignInAccount();

                String emailUG = account.getEmail();
                e1EmailU.setText(emailUG);

                btnGoogle.setBackgroundResource(R.drawable.btn_redondeado_rojo_deshabilitado);
                btnGoogle.setTextColor(Color.parseColor("#8E8686"));
                btnGoogle.setEnabled(false);

            }else
                Toast.makeText(this, "¡Login Failed!", Toast.LENGTH_SHORT).show();
        }
    }
    //******************************

    private boolean comprobarInternet(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null) {
            return false;
        }

        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, MenuPrincipal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed(){
        finish();
        cerrarSesionGoogle();
    }

}
