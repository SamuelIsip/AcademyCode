package com.example.academycode.menu_principal.teoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.academycode.R;
import com.example.academycode.api.RetrofitClient;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_libros_t);

        tematica = getIntent().getExtras().getString("tematica");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<LibrosTResponse> call = RetrofitClient.getInstance().getApi().getAllLibrosT(tematica);

        call.enqueue(new Callback<LibrosTResponse>() {
            @Override
            public void onResponse(Call<LibrosTResponse> call, Response<LibrosTResponse> response) {
                libroList = response.body().getLibros();
                adapter = new LibrosTAdapter(getApplicationContext(), libroList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<LibrosTResponse> call, Throwable t) {

            }
        });


    }


}
