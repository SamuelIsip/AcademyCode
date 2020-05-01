package com.example.academycode.model;

public class Usuario {

    private int id_usuario;
    private String email, nombre_usuario, telefono;

    public Usuario(int id_usuario, String email, String nombre_usuario, String telefono) {
        this.id_usuario = id_usuario;
        this.email = email;
        this.nombre_usuario = nombre_usuario;
        this.telefono = telefono;
    }


    public int getId_usuario() {
        return id_usuario;
    }

    public String getEmail() {
        return email;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public String getTelefono() {
        return telefono;
    }

}
