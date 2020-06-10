package com.example.academycode.almacenamiento;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.academycode.model.Usuario;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_pref";

    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }

        return mInstance;
    }

    public void saveUser(Usuario user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id_usuario", user.getId_usuario());
        editor.putString("email", user.getEmail());
        editor.putString("nombre_usuario", user.getNombre_usuario());
        editor.putString("telefono", user.getTelefono());
        editor.putString("fecha", user.getFecha());

        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id_usuario", -1) != -1;
    }

    public Usuario getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return new Usuario(
                sharedPreferences.getInt("id_usuario", -1),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("nombre_usuario", null),
                sharedPreferences.getString("telefono", null),
                sharedPreferences.getString("fecha", null)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
