package com.example.academycode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.academycode.Login.IniciarSesion;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, NavigationView.OnNavigationItemSelectedListener {

    TextView nomb,email;
    CircularImageView fotoPerfilUser;

    Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.menu_desplegable);
        navigationView.setNavigationItemSelectedListener(this); //Para click en items del menu desplegable

        //Acceder a los elementos de la cabecera del menu desplegable
        nomb = navigationView.getHeaderView(0).findViewById(R.id.nombre_user_menu);
        email = navigationView.getHeaderView(0).findViewById(R.id.email_user_menu);
        fotoPerfilUser = navigationView.getHeaderView(0).findViewById(R.id.imagen_usuario_menu);

        //Para que aparezca el icono de menu en el toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Solicitamos cuenta con email
        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


    }

    //Método seleccion del item del menu desplegable
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.cerrar_sesion:
                cerrarSesionGoogle();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true; //fue seleccionado
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

    //************************************
    //Métodos para gestionar cuenta Google
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle datos = this.getIntent().getExtras();
        if (!datos.isEmpty()) {
            nomb.setText(datos.getString("nombreUsuario"));
            email.setText(datos.getString("emailUsuario"));
        }
        //Mantener Cuenta de google conectada
        OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    //Recoger datos de la cuenta google
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            nomb.setText(account.getDisplayName());
            email.setText(account.getEmail());
            //Foto de perfil
            Picasso.get().load(account.getPhotoUrl()).placeholder(R.mipmap.ic_launcher).into(fotoPerfilUser);
        }
    }

    public void cerrarSesionGoogle(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
            new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status.isSuccess()){ //Si se cierra sesion
                        startActivity(new Intent(MainActivity.this, IniciarSesion.class));
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Session not close", Toast.LENGTH_LONG).show();
                    }
                }
            });
    }
    //************************************
    
}
