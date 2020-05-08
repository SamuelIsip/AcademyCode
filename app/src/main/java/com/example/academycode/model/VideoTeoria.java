package com.example.academycode.model;

public class VideoTeoria {

    private int id_video;
    private String titulo, tematica, fecha, url_video;

    public VideoTeoria(int id_video, String titulo, String tematica, String fecha, String url_video) {
        this.id_video = id_video;
        this.titulo = titulo;
        this.tematica = tematica;
        this.fecha = fecha;
        this.url_video = url_video;
    }

    public int getId_video() {
        return id_video;
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

    public String getUrl_video() {
        return url_video;
    }
}
