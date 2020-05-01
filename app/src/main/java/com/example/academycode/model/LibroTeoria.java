package com.example.academycode.model;

public class LibroTeoria {

    private int id_teoria;
    private String titulo, tematica, autor, url_pdf;

    public LibroTeoria(int id_teoria, String titulo, String tematica, String autor, String url_pdf) {
        this.id_teoria = id_teoria;
        this.titulo = titulo;
        this.tematica = tematica;
        this.autor = autor;
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

    public String getAutor() {
        return autor;
    }

    public String getUrl_pdf() {
        return url_pdf;
    }
}
