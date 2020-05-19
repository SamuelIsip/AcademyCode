package com.example.academycode.fragments;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.academycode.login.IniciarSesion;
import com.example.academycode.R;
import com.example.academycode.almacenamiento.SharedPrefManager;
import com.example.academycode.api.RetrofitClient;
import com.example.academycode.model.response.DefaultResponse;
import com.example.academycode.model.response.LoginResponse;
import com.example.academycode.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    private EditText editTextEmail, editTextName, editTextTelefono;
    private EditText editTextCurrentPassword, editTextNewPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextName = view.findViewById(R.id.editTextName);
        editTextTelefono = view.findViewById(R.id.editTextTelefono);
        editTextCurrentPassword = view.findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword);

        view.findViewById(R.id.buttonSave).setOnClickListener(this);
        view.findViewById(R.id.buttonChangePassword).setOnClickListener(this);
        view.findViewById(R.id.buttonDelete).setOnClickListener(this);
        view.findViewById(R.id.btnAtras).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSave:
                updateProfile();
                break;
            case R.id.buttonChangePassword:
                updatePassword();
                break;
            case R.id.buttonDelete:
                deleteUser();
                break;
            case R.id.btnAtras:
                onBackPressed();
                break;
        }
    }

    private void updatePassword() {
        String currentpassword = editTextCurrentPassword.getText().toString().trim();
        String newpassword = editTextNewPassword.getText().toString().trim();

        if (currentpassword.isEmpty()){
            editTextCurrentPassword.setError("Password necesaria");
            editTextCurrentPassword.requestFocus();
            return;
        }

        if (newpassword.isEmpty()){
            editTextNewPassword.setError("Introduce nueva password");
            editTextNewPassword.requestFocus();
            return;
        }

        Usuario user = SharedPrefManager.getInstance(getActivity()).getUser();

        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi()
                .updatePassword(currentpassword,newpassword ,user.getEmail());

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

    private void updateProfile() {
        String email = editTextEmail.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String telefono = editTextTelefono.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email es necesario");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Introduce un email válido");
            editTextEmail.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            editTextName.setError("Nombre es necesario");
            editTextName.requestFocus();
            return;
        }

        if (telefono.isEmpty()) {
            editTextTelefono.setError("Teléfono es necesario");
            editTextTelefono.requestFocus();
            return;
        }

        //El usuario ya logueado
        Usuario user = SharedPrefManager.getInstance(getActivity()).getUser();

        Call<LoginResponse> call = RetrofitClient.getInstance()
                .getApi().updateUser(
                        user.getId_usuario(),
                        email,
                        name,
                        telefono
                );

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                if (!response.body().isError()) {
                    SharedPrefManager.getInstance(getActivity()).saveUser(response.body().getUser());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

    private void deleteUser() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Estás seguro?");
        builder.setMessage("Esta cuenta será borrada. La acción es irreversible...");

        builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Usuario user = SharedPrefManager.getInstance(getActivity()).getUser();
                Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().deleteUser(user.getId_usuario());

                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                        if (!response.body().isErr()){
                            SharedPrefManager.getInstance(getActivity()).clear();
                            Intent intent = new Intent(getActivity(), IniciarSesion.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {

                    }
                });
            }
        });

        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog ad = builder.create();
        ad.show();

    }

    public void onBackPressed(){
        getFragmentManager().beginTransaction().remove(this).commit();
    }


}
