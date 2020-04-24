package com.example.academycode.model;

public class LoginResponse {

    private boolean error;
    private String message;
    private Usuario user;

    public LoginResponse(boolean error, String message, Usuario user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Usuario getUser() {
        return user;
    }
}
