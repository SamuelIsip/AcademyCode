package com.example.academycode.model;

public class Usuario {

    private int id_usuario;
    private String email,password,nombre_usuario,telefono;

    public Usuario(int id_usuario, String email, String password, String nombre_usuario, String telefono) {
        this.id_usuario = id_usuario;
        this.email = email;
        this.password = password;
        this.nombre_usuario = nombre_usuario;
        this.telefono = telefono;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public String getTelefono() {
        return telefono;
    }

}
