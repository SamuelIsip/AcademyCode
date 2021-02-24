package com.example.academycode.model;

public class EjercicioTeoria {

    private int id_ejercicios;
    private String titulo, tematica, fecha, autor, portada, url_pdf;

    public EjercicioTeoria(int id_ejercicios, String titulo, String tematica, String fecha, String autor, String portada, String url_pdf) {
        this.id_ejercicios = id_ejercicios;
        this.titulo = titulo;
        this.tematica = tematica;
        this.fecha = fecha;
        this.autor = autor;
        this.portada = portada;
        this.url_pdf = url_pdf;
    }

    public int getId_ejercicios() {
        return id_ejercicios;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTematica() {
        return tematica;
    }

    public String getFecha() {
        return fecha;
    }

    public String getAutor() {
        return autor;
    }

    public String getPortada() {
        return portada;
    }

    public String getUrl_pdf() {
        return url_pdf;
    }
}
