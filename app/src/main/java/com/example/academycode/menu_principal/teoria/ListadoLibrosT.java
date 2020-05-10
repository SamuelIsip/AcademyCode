package com.example.academycode.menu_principal.teoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academycode.R;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.login.IniciarSesion;
import com.example.academycode.model.LibroTeoria;
import com.example.academycode.model.LibrosTResponse;
import com.example.academycode.model.UsersResponse;
import com.example.academycode.model.Usuario;
import com.example.academycode.model.adapters.LibrosTAdapter;
import com.example.academycode.model.adapters.UsersAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoLibrosT extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LibrosTAdapter adapter;
    private List<LibroTeoria> libroList;
    private String tematica;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_libros_t);

        swipeRefreshLayout = findViewById(R.id.loading);
        progressBar = findViewById(R.id.loading_progress_xml);

        tematica = getIntent().getExtras().getString("tematica");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        if (!comprobarInternet()){
            Toast.makeText(ListadoLibrosT.this, "Debe conectarse a Internet", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, TeoriaPDF.class));
            finish();
        }else{
            cargarListaLibros();
        }

       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               progressBar.setVisibility(View.VISIBLE);
               cargarListaLibros();
               swipeRefreshLayout.setRefreshing(false);
           }
       });


    }

    private boolean comprobarInternet(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null) {
            return false;
        }

        return true;
    }

    private void cargarListaLibros() {

        progressBar.setVisibility(View.VISIBLE);

        Call<LibrosTResponse> call = RetrofitClient.getInstance().getApi().getAllLibrosT(tematica);

        call.enqueue(new Callback<LibrosTResponse>() {
            @Override
            public void onResponse(Call<LibrosTResponse> call, Response<LibrosTResponse> response) {
                libroList = response.body().getLibros();
                adapter = new LibrosTAdapter(getApplicationContext(), libroList);
                recyclerView.setAdapter(adapter);
                progressBar.clearAnimation();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<LibrosTResponse> call, Throwable t) {

            }
        });

    }


}
