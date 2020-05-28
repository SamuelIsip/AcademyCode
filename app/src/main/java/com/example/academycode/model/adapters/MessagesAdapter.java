package com.example.academycode.model.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.academycode.R;
import com.example.academycode.almacenamiento.SQLiteBaseDeDatos;
import com.example.academycode.menu_principal.foro.Chat;
import com.example.academycode.model.Mensaje;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private Context mCtx;
    private List<Mensaje> messageList = new ArrayList<>();
    private SQLiteBaseDeDatos db;

    public MessagesAdapter(Context mCtx, List<Mensaje> messageList) {
        this.mCtx = mCtx;
        this.messageList = messageList;

    }

    public MessagesAdapter(Context mCtx) {
        this.mCtx = mCtx;
    }


    public void addItem(Mensaje item){
        messageList.add(item);
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

        db =  new SQLiteBaseDeDatos(mCtx);

        String fotoUser = db.recuperarFotoUser(mensaje.getNombre_usuario());

        if (fotoUser!=null){
            //Mostrar foto perfil accedido
            Glide.with(mCtx)
                    .load(fotoUser) //conseguir fotoUsuario de BD
                    .into(holder.imagen_usuario_foro);
        }else{
            Glide.with(mCtx)
                    .load(R.mipmap.ic_launcher_round)
                    .into(holder.imagen_usuario_foro);
        }

        holder.imagen_usuario_foro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCtx.startActivity(new Intent(mCtx, Chat.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder{

        TextView nombre_user_foro, email_user_foro, mensaje, fechaMensaje;
        CircularImageView imagen_usuario_foro;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre_user_foro = itemView.findViewById(R.id.nombre_user_foro);
            email_user_foro = itemView.findViewById(R.id.email_user_foro);
            mensaje = itemView.findViewById(R.id.mensaje);
            fechaMensaje = itemView.findViewById(R.id.fechaMensaje);
            imagen_usuario_foro = itemView.findViewById(R.id.imagen_usuario_foro);

        }

    }

}
