package com.example.academycode.menu_principal.teoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.academycode.R;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.model.LibroTeoria;
import com.example.academycode.model.response.LibrosTResponse;
import com.example.academycode.model.adapters.LibrosTAdapter;

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
        LinearLayoutManager lnr = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lnr);


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
