package com.example.academycode.model.response;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    //se puede poner mismo nombre que lo que devuelve el json o se puede ponder anotación
    @SerializedName("error")
    private boolean err;
    @SerializedName("message")
    private String msg;

    public DefaultResponse(boolean err, String msg) {
        this.err = err;
        this.msg = msg;
    }

    public boolean isErr() {
        return err;
    }

    public String getMsg() {
        return msg;
    }

}
