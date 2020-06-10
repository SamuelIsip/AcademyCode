package com.example.academycode.model.response;


public class DefaultResponse {

    //se puede poner mismo nombre que lo que devuelve el json o se puede ponder anotación
    private boolean error;
    private String message;

    public DefaultResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
