package com.example.academycode.model;

public class LibroTeoria {

    private int id_teoria;
    private String titulo, tematica, fecha, autor, portada, url_pdf;

    public LibroTeoria(int id_teoria, String titulo, String tematica, String fecha, String autor, String portada, String url_pdf) {
        this.id_teoria = id_teoria;
        this.titulo = titulo;
        this.tematica = tematica;
        this.fecha = fecha;
        this.autor = autor;
        this.portada = portada;
        this.url_pdf = url_pdf;
    }

    public int getId_teoria() {
        return id_teoria;
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
