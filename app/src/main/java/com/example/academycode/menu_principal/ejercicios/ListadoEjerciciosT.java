package com.example.academycode.menu_principal.ejercicios;

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
import android.widget.Toast;
import com.example.academycode.R;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.model.EjercicioTeoria;
import com.example.academycode.model.EjerciciosTResponse;
import com.example.academycode.model.adapters.EjerciciosTAdapter;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoEjerciciosT extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EjerciciosTAdapter adapter;
    private List<EjercicioTeoria> ejercicioList;
    private String tematica;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_ejercicios_t);

        swipeRefreshLayout = findViewById(R.id.loading);
        progressBar = findViewById(R.id.loading_progress_xml);

        tematica = getIntent().getExtras().getString("tematica");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (!comprobarInternet()){
            Toast.makeText(ListadoEjerciciosT.this, "Debe conectarse a Internet", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, EjerciciosPDF.class));
            finish();
        }else{
            cargarListaEjercicios();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                progressBar.setVisibility(View.VISIBLE);
                cargarListaEjercicios();
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


    private void cargarListaEjercicios() {
        progressBar.setVisibility(View.VISIBLE);

        Call<EjerciciosTResponse> call = RetrofitClient.getInstance().getApi().getAllEjerciciosT(tematica);

        call.enqueue(new Callback<EjerciciosTResponse>() {
            @Override
            public void onResponse(Call<EjerciciosTResponse> call, Response<EjerciciosTResponse> response) {
                ejercicioList = response.body().getEjercicios();
                adapter = new EjerciciosTAdapter(getApplicationContext(), ejercicioList);
                recyclerView.setAdapter(adapter);
                progressBar.clearAnimation();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<EjerciciosTResponse> call, Throwable t) {

            }
        });

    }


}
