package com.example.academycode.almacenamiento;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.academycode.Login.IniciarSesion;
import com.example.academycode.Login.RegistrarUsuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SQLiteBaseDeDatos extends SQLiteOpenHelper {

    Context context;
    //Constructor
    public SQLiteBaseDeDatos(Context context){
        super(context, "AcademyBD", null, 1); //Nombre y versión de la BD
        this.context = context;
    }

    //Creación de las tablas de la BD
    @Override
    public void onCreate(SQLiteDatabase db) {

        //USUARIO
        db.execSQL("create table usuario(" +
                "nombreUsuario text primary key," +
                "fotoPerfil text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuario");
    }

    public String recuperarFotoUser(String nombUsuario){
        SQLiteDatabase baseDatos = this.getReadableDatabase();
        Cursor cursor = baseDatos.rawQuery("Select fotoPerfil from usuario where nombreUsuario=?",new String[]{nombUsuario});

        String foto = "";
        if (cursor.moveToFirst()){
            foto = cursor.getString(0);
        }

        return foto;
    }

    //Insertar foto perfil de usuario en bd
    public void insertarFotoUser(String path_uri, String nombreU){
        SQLiteDatabase baseDatos = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fotoPerfil", path_uri);

        int cantidad = baseDatos.update("usuario", contentValues,"nombreUsuario = ?",new String[]{nombreU});

        if (cantidad == 1){
            System.out.println("FOTO GUARDADA CORRECTAMENTE");
        }else{
            System.out.println("FOTO NO GUARDADA CORRECTAMENTE");
        }

    }

    //Insertar en tabla Usuario
    public void insertUsuario(String nombreUsu){

        try{
            SQLiteDatabase baseDatos = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombreUsuario",nombreUsu);

            long insert = baseDatos.insertOrThrow("usuario", null, contentValues);
        }catch (Exception e){

        }

    }




}
