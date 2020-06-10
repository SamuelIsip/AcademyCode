package com.example.academycode.menu_principal.amigos;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.academycode.R;
import com.example.academycode.almacenamiento.SharedPrefManager;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.menu_principal.MenuPrincipal;
import com.example.academycode.model.Usuario;
import com.example.academycode.model.adapters.UsersAdapter;
import com.example.academycode.model.response.UsersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmigosUsers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private List<Usuario> amigosList;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigos_users);

        progressBar = findViewById(R.id.loading_progress_xml);

        recyclerView = findViewById(R.id.recycler_view_amigos);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(layoutManager);


        if (!comprobarInternet()) {
            Toast.makeText(AmigosUsers.this, "Debe conectarse a Internet", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MenuPrincipal.class));
            finish();
        } else {
            cargarListaAmigos();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MenuPrincipal.class));
        finish();
    }

    private boolean comprobarInternet() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else return activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        }

        return false;
    }

    private void cargarListaAmigos() {

        progressBar.setVisibility(View.VISIBLE);

        Usuario usuario = SharedPrefManager.getInstance(this).getUser();
        String usuario_nomb = usuario.getNombre_usuario();

        Call<UsersResponse> call = RetrofitClient.getInstance()
                .getApi().getAllFriends(usuario_nomb);

        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {

                amigosList = response.body().getUsers();
                adapter = new UsersAdapter(getApplicationContext(), amigosList);
                recyclerView.setAdapter(adapter);
                progressBar.clearAnimation();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });

    }

    public void btnAtras(View view) {
        startActivity(new Intent(getApplicationContext(), MenuPrincipal.class));
        finish();
    }
}
