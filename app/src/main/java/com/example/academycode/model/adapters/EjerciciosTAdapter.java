package com.example.academycode.model.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.academycode.R;
import com.example.academycode.model.EjercicioTeoria;

import java.util.List;

public class EjerciciosTAdapter extends RecyclerView.Adapter<EjerciciosTAdapter.EjerciciosViewHolder> {

    private Context mCtx;
    private List<EjercicioTeoria> ejerciciosList;

    public EjerciciosTAdapter(Context mCxt, List<EjercicioTeoria> ejerciciosList) {
        this.mCtx = mCxt;
        this.ejerciciosList = ejerciciosList;
    }


    @NonNull
    @Override
    public EjerciciosTAdapter.EjerciciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_listado_libros_teoria, parent, false);
        return new EjerciciosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EjerciciosTAdapter.EjerciciosViewHolder holder, int position) {

        EjercicioTeoria ejerT = ejerciciosList.get(position);

        holder.textViewTitulo.setText(ejerT.getTitulo());
        holder.textViewAutor.setText("Autor: "+ejerT.getAutor());
        holder.textViewFecha.setText("Actualización: "+ejerT.getFecha());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ejerT.getUrl_pdf()));
                    mCtx.startActivity(browserIntent);
                }catch(Exception e){
                    Toast.makeText(mCtx, "¡El ejercicio ya no está disponible!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Glide.with(mCtx)
                .load(ejerT.getPortada())
                .into(holder.imagenPortada);

    }

    @Override
    public int getItemCount() {
        return ejerciciosList.size();
    }

    class EjerciciosViewHolder extends  RecyclerView.ViewHolder{

        public TextView textViewTitulo, textViewFecha, textViewAutor;
        public LinearLayout linearLayout;
        public ImageView imagenPortada;

        public EjerciciosViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewFecha = itemView.findViewById(R.id.textViewFecha);
            textViewAutor = itemView.findViewById(R.id.textViewAutor);
            imagenPortada = itemView.findViewById(R.id.imagenPortada);
            linearLayout = itemView.findViewById(R.id.linearLayoutPdf);

        }


    }

}
