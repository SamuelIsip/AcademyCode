package com.example.academycode.BaseDeDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SQLiteBaseDeDatos extends SQLiteOpenHelper {

    //Constructor
    public SQLiteBaseDeDatos(Context context){
        super(context, "AcademyBD", null, 1); //Nombre y versión de la BD
    }

    //Creación de las tablas de la BD
    @Override
    public void onCreate(SQLiteDatabase db) {

        //USUARIO
        db.execSQL("create table usuario(" +
                "email text primary key," +
                "password text," +
                "nombreUsuario text," +
                "telefono text," +
                "fechaCreacion text," +
                "fotoPerfil text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists usuario");
    }

    //Insertar en tabla Usuario
    public boolean insertUsuario(String email, String password, String nombreUsu, String telefUsu){

        SQLiteDatabase baseDatos = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", encriptar(nombreUsu,email)); //se encripta email
        contentValues.put("password",encriptar(nombreUsu,password)); //se encripta password
        contentValues.put("nombreUsuario",nombreUsu);
        contentValues.put("telefono",telefUsu);
        contentValues.put("fechaCreacion",fechaSistema());

        long insert = baseDatos.insert("usuario", null, contentValues);

        if (insert == -1) return false;
        else return true;

    }

    //Fecha del sistema
    public String fechaSistema(){
        Date fecha = new Date();
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-MM-yyyy");

        return objSDF.format(fecha);
    }

    //Comprobar si el email existe
    public boolean checkEmail(String email){
        SQLiteDatabase baseDatos = this.getReadableDatabase();
        Cursor cursor = baseDatos.rawQuery("Select * from usuario where email=?",new String[]{email});
        if (cursor.getCount()>0)
            return false;
        else
            return true;
    }

    //Comprobar si el nombreUsuario existe
    public boolean checkUserName(String nomUser){
        SQLiteDatabase baseDatos = this.getReadableDatabase();
        Cursor cursor = baseDatos.rawQuery("Select * from usuario where nombreUsuario=?",new String[]{nomUser});
        if (cursor.getCount()>0)
            return false;
        else
            return true;
    }

    //Comprobar si el usuario y la contraseña son correctos
    public boolean checkUserPasswd (String nombreUsu, String passwd){
        SQLiteDatabase baseDatos = this.getReadableDatabase();
        Cursor cursor = baseDatos.rawQuery("Select * from usuario where nombreUsuario=? and password=?"
                ,new String[]{nombreUsu,encriptar(nombreUsu,passwd)});

        if (cursor.getCount()==1)
            return true;
        else
            return false;
    }

    //Encriptar datos con método AES
    private String encriptar(String nombreUsu, String datoAencriptar){
        String datosEncriptadosString = "";

        try{
           SecretKeySpec secretKey = generateKey(nombreUsu);
           Cipher cipher = Cipher.getInstance("AES");
           cipher.init(Cipher.ENCRYPT_MODE, secretKey);
           byte[] datosEncriptadosBytes = cipher.doFinal(datoAencriptar.getBytes());
           datosEncriptadosString = Base64.encodeToString(datosEncriptadosBytes, Base64.DEFAULT);


        }catch (Exception c){
            c.printStackTrace();
        }

        return datosEncriptadosString;
    }

    //Crear la key de encriptación
    private SecretKeySpec generateKey(String datoAencriptar) throws Exception{
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = datoAencriptar.getBytes("UTF-8");
        key = sha.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key,"AES");

        return secretKey;
    }

    //Desencriptar datos
    private String desencriptar(String nombreUsu){
        String datosDesencriptadosString = "";

        SQLiteDatabase baseDatos = this.getReadableDatabase();
        Cursor fila = baseDatos.rawQuery("Select password from usuario where nombreUsuario=?",new String[]{nombreUsu});

        String datoAdesencriptar = "";

        if (fila.moveToFirst()) {
            datoAdesencriptar = fila.getString(0);
        }

        try{
            SecretKeySpec secretKey = generateKey(nombreUsu);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] datosDescodificados = Base64.decode(datoAdesencriptar, Base64.DEFAULT);
            byte[] datosDesencriptadosByte = cipher.doFinal(datosDescodificados);
            datosDesencriptadosString = new String(datosDesencriptadosByte);
        }catch (Exception c){
            c.printStackTrace();
        }

        return datosDesencriptadosString;
    }



}
