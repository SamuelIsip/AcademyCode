package com.example.academycode.model.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.academycode.R;
import com.example.academycode.menu_principal.foro.UserInfo;
import com.example.academycode.model.Usuario;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private Context mCtx;
    private List<Usuario> userList;

    public UsersAdapter(Context mCtx, List<Usuario> userList) {
        this.mCtx = mCtx;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_amigo_item, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        Usuario user = userList.get(position);

        String name = user.getNombre_usuario();
        String email = user.getEmail();
        String telefono = user.getTelefono();
        String fecha = user.getFecha();

        holder.textViewName.setText(name);
        holder.textViewEmail.setText(email);
        holder.textViewTelefono.setText(telefono);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, UserInfo.class);
                intent.putExtra("username", name);
                intent.putExtra("useremail", email);
                intent.putExtra("usertlf", telefono);
                intent.putExtra("userfecha", fecha);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewEmail, textViewTelefono;
        LinearLayout linearLayout;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewTelefono = itemView.findViewById(R.id.textViewTelefono);
            linearLayout = itemView.findViewById(R.id.linearlayoutamigo);
        }

    }

}
