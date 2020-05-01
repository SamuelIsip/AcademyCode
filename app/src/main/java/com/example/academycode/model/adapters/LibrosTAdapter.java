package com.example.academycode.model.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.academycode.R;
import com.example.academycode.model.LibroTeoria;

import java.util.List;

public class LibrosTAdapter extends RecyclerView.Adapter<LibrosTAdapter.LibrosViewHolder> {

    private Context mCtx;
    private List<LibroTeoria> librosList;

    public LibrosTAdapter(Context mCtx, List<LibroTeoria> librosList) {
        this.mCtx = mCtx;
        this.librosList = librosList;
    }

    @NonNull
    @Override
    public LibrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_listado_libros_teoria, parent, false);
        return new LibrosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibrosViewHolder holder, int position) {
        LibroTeoria librosT = librosList.get(position);

        holder.textViewTitulo.setText(librosT.getTitulo());
        holder.textViewTematica.setText(librosT.getTematica());
        holder.textViewAutor.setText(librosT.getAutor());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(librosT.getUrl_pdf()));
                mCtx.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return librosList.size();
    }


    public class LibrosViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewTitulo, textViewTematica, textViewAutor;
        public LinearLayout linearLayout;

        public LibrosViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewTematica = itemView.findViewById(R.id.textViewTematica);
            textViewAutor = itemView.findViewById(R.id.textViewAutor);
            linearLayout = itemView.findViewById(R.id.linearLayoutPdf);

        }

    }


}
