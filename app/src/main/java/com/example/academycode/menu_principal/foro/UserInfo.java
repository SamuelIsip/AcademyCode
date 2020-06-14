package com.example.academycode.menu_principal.foro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.academycode.R;
import com.example.academycode.almacenamiento.SQLiteBaseDeDatos;
import com.example.academycode.almacenamiento.SharedPrefManager;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.menu_principal.amigos.AmigosUsers;
import com.example.academycode.model.Usuario;
import com.example.academycode.model.response.DefaultResponse;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfo extends AppCompatActivity {

    private TextView username, useremail, usertlf, userfecha;
    private CircularImageView fotoPerfilUser;
    private SQLiteBaseDeDatos db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        db = new SQLiteBaseDeDatos(this);

        username = findViewById(R.id.info_user_nombre);
        useremail = findViewById(R.id.info_user_email);
        usertlf = findViewById(R.id.info_user_telef);
        userfecha = findViewById(R.id.info_user_fecha);
        fotoPerfilUser = findViewById(R.id.imagen_usuario_info);

        Bundle extras = getIntent().getExtras();
        username.setText(extras.getString("username"));
        useremail.setText(extras.getString("useremail"));
        usertlf.setText(extras.getString("usertlf"));
        userfecha.setText(extras.getString("userfecha"));

        String fotoUser = db.recuperarFotoUser(extras.getString("username"));
        mostrarImagen_guardada_o_no(fotoUser);
        db.close();

    }

    private void mostrarImagen_guardada_o_no(String fotoUser) {
        File archivo = null;
        try {
            archivo = new File(fotoUser);
        }catch(Exception e){
            e.printStackTrace();
        }

        if (fotoUser != null && archivo.exists()) {
            //Mostrar foto perfil accedido
            Glide.with(this)
                    .load(fotoUser) //conseguir fotoUsuario de BD
                    .into(fotoPerfilUser);
        } else {
            /*Glide.with(this)
                    .load(R.mipmap.ic_launcher_round)
                    .into(fotoPerfilUser);*/
        }
    }


    public void aniadirAmigo(View view) {

        Usuario usuario = SharedPrefManager.getInstance(getApplicationContext()).getUser();

        Call<DefaultResponse> call = RetrofitClient.getInstance()
                .getApi()
                .saveFriend(usuario.getNombre_usuario(), username.getText().toString().trim());

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                if (response.code() == 201) {
                    DefaultResponse defaultResponse = response.body();

                    Toast.makeText(UserInfo.this, defaultResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 422) {
                    Toast.makeText(getApplicationContext(), "¡Ya sois amigos!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });

    }

    public void verAmigos(View view) {
        Intent intent = new Intent(this, AmigosUsers.class);
        startActivity(intent);
        finish();
    }
}
