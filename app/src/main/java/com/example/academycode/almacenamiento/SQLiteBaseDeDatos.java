package com.example.academycode.almacenamiento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteBaseDeDatos extends SQLiteOpenHelper {

    Context context;

    public SQLiteBaseDeDatos(Context context) {
        super(context, "AcademyBD", null, 1); //Nombre y versión de la BD
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table usuario(" +
                "nombreUsuario text primary key," +
                "fotoPerfil text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuario");
    }

    public String recuperarFotoUser(String nombUsuario) {
        SQLiteDatabase baseDatos = this.getReadableDatabase();
        Cursor cursor = baseDatos.rawQuery("Select fotoPerfil from usuario where nombreUsuario=?", new String[]{nombUsuario});

        String foto = "";
        if (cursor.moveToFirst()) {
            foto = cursor.getString(0);
        }

        return foto;
    }

    public void insertarFotoUser(String path_uri, String nombreU) {
        SQLiteDatabase baseDatos = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fotoPerfil", path_uri);

        int cantidad = baseDatos.update("usuario", contentValues, "nombreUsuario = ?", new String[]{nombreU});

        if (cantidad == 1) {
            System.out.println("FOTO GUARDADA CORRECTAMENTE");
        } else {
            System.out.println("FOTO NO GUARDADA CORRECTAMENTE");
        }

    }

    public void insertUsuario(String nombreUsu) {

        try {
            SQLiteDatabase baseDatos = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombreUsuario", nombreUsu);

            long insert = baseDatos.insertOrThrow("usuario", null, contentValues);
        } catch (Exception e) {

        }

    }


}
