package com.example.academycode.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.academycode.R;
import com.example.academycode.model.Mensaje;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private Context mCtx;
    private List<Mensaje> messageList;

    public MessagesAdapter(Context mCtx, List<Mensaje> messageList) {
        this.mCtx = mCtx;
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_mensajes_foro, parent, false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {

        Mensaje mensaje = messageList.get(position);

        holder.nombre_user_foro.setText("Usuario: "+mensaje.getNombre_usuario());
        holder.email_user_foro.setText("Email: "+mensaje.getEmail());
        holder.mensaje.setText(mensaje.getMensaje());
        holder.fechaMensaje.setText(mensaje.getFecha());

    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder{

        TextView nombre_user_foro, email_user_foro, mensaje, fechaMensaje;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre_user_foro = itemView.findViewById(R.id.nombre_user_foro);
            email_user_foro = itemView.findViewById(R.id.email_user_foro);
            mensaje = itemView.findViewById(R.id.mensaje);
            fechaMensaje = itemView.findViewById(R.id.fechaMensaje);

        }

    }

}
