package com.example.academycode.model;

import java.util.List;

public class LibrosTResponse {

    private boolean error;
    private List<LibroTeoria> libros;

    public LibrosTResponse(boolean error, List<LibroTeoria> libros) {
        this.error = error;
        this.libros = libros;
    }

    public boolean isError() {
        return error;
    }

    public List<LibroTeoria> getLibros() {
        return libros;
    }
}
