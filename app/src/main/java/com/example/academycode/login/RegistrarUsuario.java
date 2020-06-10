package com.example.academycode.login;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.academycode.R;
import com.example.academycode.almacenamiento.SQLiteBaseDeDatos;
import com.example.academycode.almacenamiento.SharedPrefManager;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.menu_principal.MenuPrincipal;
import com.example.academycode.model.response.DefaultResponse;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarUsuario extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Button btnRegistrarU, btnVolverInicioS, btnRegitrarGoogle;
    private EditText edTextEmail, edTextNUs, edTxtPasw1, edTxtPasw2, edTxtTelef;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private static final int SIGN_IN = 1;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        btnRegistrarU = findViewById(R.id.btnRegistrarU);
        btnRegitrarGoogle = findViewById(R.id.registrar_google);
        btnVolverInicioS = findViewById(R.id.btnVolverIniciarSesion);
        edTextEmail = findViewById(R.id.edTxtEmail);
        edTextNUs = findViewById(R.id.edTxtNomUs);
        edTxtPasw1 = findViewById(R.id.edTxtPasw1);
        edTxtPasw2 = findViewById(R.id.edTxtPasw2);
        edTxtTelef = findViewById(R.id.edTxtTlf);

        btnRegistrarU.setOnClickListener(v -> insertarUsuarioComprobado());

        btnVolverInicioS.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), IniciarSesion.class));
            finish();
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        btnRegitrarGoogle.setOnClickListener(v -> {
            cerrarSesionGoogle();
            Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(intent, SIGN_IN);
            btnRegitrarGoogle.setBackgroundResource(R.drawable.btn_redondeado_rojo_deshabilitado);
            btnRegitrarGoogle.setTextColor(Color.parseColor("#8E8686"));
            btnRegitrarGoogle.setEnabled(false);
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void cerrarSesionGoogle() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NotNull Status status) {
                        if (status.isSuccess()) { //Si se cierra sesion

                        } else {
                            Toast.makeText(getApplicationContext(), "Session not close", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GoogleSignInAccount account;

        if (requestCode == SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                account = result.getSignInAccount();
                final String emailUG = account.getEmail();
                final String nombUG = account.getDisplayName();

                edTextEmail.setText(emailUG);
                edTextNUs.setText(nombUG);
                edTxtTelef.setText("+34 ");

                cerrarSesionGoogle();
            } else
                Toast.makeText(this, "¡Login Failed!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MenuPrincipal.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void insertarUsuarioComprobado() {
        String emailU = edTextEmail.getText().toString().trim();
        final String passwrdU = edTxtPasw1.getText().toString().trim();
        String nombreUsu = edTextNUs.getText().toString().trim();
        String telefono = edTxtTelef.getText().toString().trim();
        String passwrdU2 = edTxtPasw2.getText().toString().trim();

        if (telefono.equals("")) {
            telefono = "+34 ";
        }

        if (emailU.equals("") || nombreUsu.equals("") || passwrdU.equals("") || passwrdU2.equals("")) {
            Toast.makeText(RegistrarUsuario.this, "¡Debe rellenar todos los campos!", Toast.LENGTH_SHORT).show();
        } else {
            if (emailValido(emailU) && passValida(passwrdU)) {
                if (passwrdU.equals(passwrdU2)) {

                    dialog = ProgressDialog.show(RegistrarUsuario.this, "",
                            "Conectando con el servidor...", true);

                    Call<DefaultResponse> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .createUser(emailU, passwrdU, nombreUsu, telefono);

                    call.enqueue(new Callback<DefaultResponse>() {
                        @Override
                        public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                            if (response.code() == 201) {
                                DefaultResponse dr = response.body();
                                Toast.makeText(getApplicationContext(), dr.getMessage(), Toast.LENGTH_SHORT).show();

                                SQLiteBaseDeDatos db = new SQLiteBaseDeDatos(getApplicationContext());
                                db.insertUsuario(nombreUsu);

                                Intent intent = new Intent(getApplicationContext(), IniciarSesion.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                dialog.dismiss();
                                startActivity(intent);

                            } else if (response.code() == 422) {
                                Toast.makeText(getApplicationContext(), "Email/Nombre ya existente", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<DefaultResponse> call, Throwable t) {

                        }
                    });


                } else
                    passwNoCoinciden();
            }

        }

    }

    public void passwNoCoinciden() {
        edTxtPasw2.setError("¡Las contraseñas no coinciden!");
        edTxtPasw2.requestFocus();
    }

    public boolean emailValido(String email) {
        boolean valido;

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edTextEmail.setError("¡Formato Email incorrecto!");
            edTextEmail.requestFocus();
            valido = false;
        } else valido = true;

        return valido;

    }

    public boolean passValida(String pass) {
        if (pass.length() > 8)
            return true;
        else {
            edTxtPasw1.setError("¡Password demasiado corta!");
            edTxtPasw1.requestFocus();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegistrarUsuario.this, IniciarSesion.class));
        finish();
    }

}
