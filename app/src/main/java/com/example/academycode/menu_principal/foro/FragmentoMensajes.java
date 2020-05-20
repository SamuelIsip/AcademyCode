package com.example.academycode.menu_principal.foro;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.academycode.R;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.menu_principal.MenuPrincipal;
import com.example.academycode.model.Mensaje;
import com.example.academycode.model.adapters.MessagesAdapter;
import com.example.academycode.model.response.MessagesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoMensajes extends Fragment {

    private RecyclerView recyclerView;
    private MessagesAdapter adapter;
    private List<Mensaje> mensajeList;
    private ProgressBar progressBar;

    public FragmentoMensajes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_mensajes, container, false);
        progressBar = view.findViewById(R.id.loading_progress_xml);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewForo);
        LinearLayoutManager lnr = new LinearLayoutManager(getActivity());
        //lnr.setReverseLayout(true);
        recyclerView.setLayoutManager(lnr);

        cargarListaMensajes();

    }

    private void cargarListaMensajes() {
        progressBar.setVisibility(View.VISIBLE);
        Call<MessagesResponse> call = RetrofitClient.getInstance()
                .getApi().getAllMessages();

        call.enqueue(new Callback<MessagesResponse>() {
            @Override
            public void onResponse(Call<MessagesResponse> call, Response<MessagesResponse> response) {

                mensajeList = response.body().getMessages();

                adapter = new MessagesAdapter(getActivity(), mensajeList);

                recyclerView.setAdapter(adapter);

                recyclerView.getLayoutManager().scrollToPosition(mensajeList.size()-1);

                progressBar.clearAnimation();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<MessagesResponse> call, Throwable t) {

            }
        });

    }


}
