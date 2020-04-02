package com.example.academycode.Login;

import androidx.annotation.NonNull;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class IniciarSesion extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    SQLiteBaseDeDatos db;
    EditText e1NUsuario, e2Pswrd;
    Button b1Registrar, b2Acceder, btnGoogle;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private static final int SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_app);

        db = new SQLiteBaseDeDatos(this);

        e1NUsuario = findViewById(R.id.edTxtNUs);
        e2Pswrd = findViewById(R.id.edTxtPsw);
        b1Registrar = findViewById(R.id.btnRegistrar);
        b2Acceder = findViewById(R.id.btnAcceder);
        btnGoogle = findViewById(R.id.inicar_google);

        b2Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombreUsu = e1NUsuario.getText().toString();
                String passwU = e2Pswrd.getText().toString();
                
                if (!nombreUsu.equals("") || !passwU.equals("")) {
                    if (db.checkUserPasswd(nombreUsu, passwU)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(IniciarSesion.this, "¡Usuario/Contraseña incorrecto!", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(IniciarSesion.this, "¡Debe rellenar todos los campos!", Toast.LENGTH_SHORT).show();
            }
        });

        b1Registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegistrarUsuario.class);
                startActivity(intent);
                finish();
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN);
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess())
            {
                startActivity(new Intent(IniciarSesion.this, MainActivity.class));
                finish();
            }else
                Toast.makeText(this, "¡Login Failed!", Toast.LENGTH_SHORT).show();
        }
    }

}
