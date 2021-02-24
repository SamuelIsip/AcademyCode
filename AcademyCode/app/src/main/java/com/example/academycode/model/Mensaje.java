package com.example.academycode.model;

public class Mensaje {

    private String ip_usu, nombre_usuario, email, mensaje, fecha;

    public Mensaje(String ip_usu, String nombre_usuario, String email, String mensaje, String fecha) {
        this.ip_usu = ip_usu;
        this.nombre_usuario = nombre_usuario;
        this.email = email;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public String getIp_usu() {
        return ip_usu;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public String getEmail() {
        return email;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getFecha() {
        return fecha;
    }
}
