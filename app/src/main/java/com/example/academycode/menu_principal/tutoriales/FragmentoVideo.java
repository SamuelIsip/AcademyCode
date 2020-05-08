package com.example.academycode.menu_principal.tutoriales;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.academycode.R;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.menu_principal.MenuPrincipal;
import com.example.academycode.menu_principal.teoria.ListadoLibrosT;
import com.example.academycode.menu_principal.teoria.TeoriaPDF;
import com.example.academycode.model.VideoTeoria;
import com.example.academycode.model.VideosTResponse;
import com.example.academycode.model.adapters.VideoAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoVideo extends Fragment {
    private String tematica;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private List<VideoTeoria> videoList;

    public FragmentoVideo(String tematica) {
        this.tematica = tematica;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_video, container, false);
        swipeRefreshLayout = view.findViewById(R.id.loadingv);
        progressBar = view.findViewById(R.id.loading_progress_xml);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewvideos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (!comprobarInternet()){
            Toast.makeText(getContext(), "Debe conectarse a Internet", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getContext(), MenuPrincipal.class));
        }else{
            cargarListaVideos();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarListaVideos();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        progressBar.setVisibility(View.VISIBLE);

    }

    private boolean comprobarInternet(){
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null) {
            return false;
        }

        return true;
    }

    private void cargarListaVideos() {

        Call<VideosTResponse> call = RetrofitClient.getInstance().getApi().getAllVideosT(tematica);

        call.enqueue(new Callback<VideosTResponse>() {
            @Override
            public void onResponse(Call<VideosTResponse> call, Response<VideosTResponse> response) {

                videoList = response.body().getVideos();

                adapter = new VideoAdapter(getActivity(), videoList);

                recyclerView.setAdapter(adapter);
                progressBar.clearAnimation();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<VideosTResponse> call, Throwable t) {

            }
        });

    }


}
