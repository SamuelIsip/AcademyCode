package com.example.academycode.BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteBaseDeDatos extends SQLiteOpenHelper {

    public SQLiteBaseDeDatos(Context context){
        super(context, "AcademyBD", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table usuario(" +
                "email text primary key," +
                "password text," +
                "nombreUsuario text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuario");
    }

    public boolean insertUsuario(String email, String password, String nombreUsu){
        SQLiteDatabase baseDatos = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password",password);
        contentValues.put("nombreUsuario",nombreUsu);
        long insert = baseDatos.insert("usuario", null, contentValues);

        if (insert == -1)
            return false;
        else
            return true;
    }

    //comprobar si el email existe
    public boolean checkEmail(String email){
        SQLiteDatabase baseDatos = this.getReadableDatabase();
        Cursor cursor = baseDatos.rawQuery("Select * from usuario where email=?",new String[]{email});
        if (cursor.getCount()>0)
            return false;
        else
            return true;
    }

    //comprobar si el nombreUsuario existe
    public boolean checkUserName(String nomUser){
        SQLiteDatabase baseDatos = this.getReadableDatabase();
        Cursor cursor = baseDatos.rawQuery("Select * from usuario where nombreUsuario=?",new String[]{nomUser});
        if (cursor.getCount()>0)
            return false;
        else
            return true;
    }

    //comprobar si el usuario y la contraseña son correctos
    public boolean checkUserPasswd (String nombreUsu, String passwd){
        SQLiteDatabase baseDatos = this.getReadableDatabase();
        Cursor cursor = baseDatos.rawQuery("Select * from usuario where nombreUsuario=? and password=?",new String[]{nombreUsu,passwd});
        if (cursor.getCount()==1)
            return true;
        else
            return false;
    }

}
