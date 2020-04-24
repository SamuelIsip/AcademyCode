package com.example.academycode.Login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.academycode.BaseDeDatos.SQLiteBaseDeDatos;
import com.example.academycode.MenuPrincipal.MenuPrincipal;
import com.example.academycode.R;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.model.LoginResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IniciarSesion extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    SQLiteBaseDeDatos db;
    EditText e1EUsuario, e2Pswrd;
    Button b1Registrar, b2Acceder, btnGoogle;

    //Variable para Google
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private static final int SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_app);

        db = new SQLiteBaseDeDatos(this);

        e1EUsuario = findViewById(R.id.edTxtEmail);
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

                String emailUsu = e1EUsuario.getText().toString();
                String passwU = e2Pswrd.getText().toString();

                if (emailUsu.isEmpty()) {
                    e1EUsuario.setError("Nombre Usuario requerido");
                    e1EUsuario.requestFocus();
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

                Call<LoginResponse> call = RetrofitClient
                        .getInstance().getApi().userLogin(emailUsu, passwU);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();

                        if (!loginResponse.isError()){
                            Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                            intent.putExtra("nombreUsuario", emailUsu);
                            intent.putExtra("emailUsuario", db.recuperarEmial(emailUsu));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }else
                            Toast.makeText(IniciarSesion.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    //Se selecciona cuenta, si el login es correcto se accede
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        GoogleSignInAccount account;

        if (requestCode == SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess())
            {
                //Guardar datos de la cuenta google en BD (guardar usuario, sin pw)
                account=result.getSignInAccount();
                try{
                    if (db.checkEmail(account.getEmail()) == true){ //Si es la primera vez que se conecta con google, que se guarde en BD
                        db.insertUsuario(account.getEmail(),String.valueOf((Math.random())),account.getDisplayName(), "");
                        db.insertarFotoUser(account.getPhotoUrl().toString(),account.getEmail(),account.getDisplayName());
                    }
                }catch (Exception e){
                    //Capturamos error al volver a crear la activity (ya no tiene instancia de GoogleSignInAccount
                }

                startActivity(new Intent(IniciarSesion.this, MenuPrincipal.class));
                finish();

            }else
                Toast.makeText(this, "¡Login Failed!", Toast.LENGTH_SHORT).show();
        }
    }
    //******************************

}
