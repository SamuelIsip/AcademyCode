package com.example.academycode.model;

public class UsuarioResponse {

    private boolean error;
    private Usuario user;

    public UsuarioResponse(boolean error, Usuario user) {
        this.error = error;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public Usuario getUser() {
        return user;
    }
}
