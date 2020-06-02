package com.example.academycode.model.adapters;

import android.app.Notification;
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
import com.example.academycode.almacenamiento.SharedPrefManager;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.menu_principal.foro.UserInfo;
import com.example.academycode.model.Mensaje;
import com.example.academycode.model.Usuario;
import com.example.academycode.model.response.LoginResponse;
import com.example.academycode.model.response.UsersResponse;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private Context mCtx;
    private List<Mensaje> messageList = new ArrayList<>();
    private SQLiteBaseDeDatos db;

    Notification.Builder notificacion;
    private static final int idUnica = 51623;


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
        File archivo = new File(fotoUser);
        if (fotoUser!=null && archivo.exists()){
            //Mostrar foto perfil accedido
            Glide.with(mCtx.getApplicationContext())
                    .load(fotoUser) //conseguir fotoUsuario de BD
                    .into(holder.imagen_usuario_foro);
        }else{
            Glide.with(mCtx.getApplicationContext())
                    .load(R.mipmap.ic_launcher_round)
                    .into(holder.imagen_usuario_foro);
        }

        holder.imagen_usuario_foro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<UsersResponse> call = RetrofitClient.getInstance().getApi().getOneUser(mensaje.getEmail());

                call.enqueue(new Callback<UsersResponse>() {
                    @Override
                    public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                        UsersResponse usersResponse = response.body();

                        if (!usersResponse.isError()){
                            List<Usuario> user = usersResponse.getUsers();

                            Intent intent = new Intent(mCtx, UserInfo.class);
                            intent.putExtra("username", user.get(0).getNombre_usuario());
                            intent.putExtra("useremail", user.get(0).getEmail());
                            intent.putExtra("usertlf", user.get(0).getTelefono());
                            intent.putExtra("userfecha", user.get(0).getFecha());
                            mCtx.startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<UsersResponse> call, Throwable t) {

                    }
                });

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
