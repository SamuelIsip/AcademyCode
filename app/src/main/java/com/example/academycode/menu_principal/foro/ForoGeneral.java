package com.example.academycode.menu_principal.foro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.academycode.R;

import com.example.academycode.almacenamiento.SQLiteBaseDeDatos;
import com.example.academycode.almacenamiento.SharedPrefManager;
import com.example.academycode.login.IniciarSesion;
import com.example.academycode.login.RegistrarUsuario;
import com.example.academycode.menu_principal.MenuPrincipal;

import com.example.academycode.model.Usuario;
import com.example.academycode.model.response.DefaultResponse;

import com.example.academycode.api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForoGeneral extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText mensajeDelUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_general);

        recyclerView = findViewById(R.id.recyclerViewForo);
        LinearLayoutManager lnr = new LinearLayoutManager(this);
        lnr.setReverseLayout(true);
        recyclerView.setLayoutManager(lnr);

        mensajeDelUsuario = findViewById(R.id.edTxtMensajeForo);



        if (!comprobarInternet()){
            Toast.makeText(ForoGeneral.this, "Debe conectarse a Internet", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MenuPrincipal.class));
            finish();
        }else{
            cargarMensajes();
        }

    }

    private void cargarMensajes() {

        //Aquí se recogen los datos del servidor

    }


    private boolean comprobarInternet(){
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }

        return false;
    }

    public void enviarMensaje(View view) {

        String mensaje = mensajeDelUsuario.getText().toString().trim();

        Usuario user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String nombre_usuario = user.getNombre_usuario();
        String email = user.getEmail();

        /*String fotoUser = db.recuperarFotoUser(user.getNombre_usuario());
        mostrarImagen_guardada_o_no(fotoUser);*/

        if (!mensaje.trim().equals("")){
            Call<DefaultResponse> call = RetrofitClient.getInstance()
                    .getApi().saveMessage("12", nombre_usuario, email, mensaje);

            call.enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                    if (response.code() == 201){
                        DefaultResponse dr = response.body();
                        Toast.makeText(getApplicationContext(), dr.getMsg(), Toast.LENGTH_SHORT).show();
                        mensajeDelUsuario.setText("");
                    }else if(response.code() == 422){
                        Toast.makeText(getApplicationContext(), "No se ha enviado el mensaje", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {

                }
            });
        }

    }



    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, RegistrarUsuario.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }


}
