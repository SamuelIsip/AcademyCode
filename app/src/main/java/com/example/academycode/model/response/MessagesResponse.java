package com.example.academycode.model.response;

import com.example.academycode.model.Mensaje;

import java.util.List;

public class MessagesResponse {

    private boolean error;
    private List<Mensaje> messages;

    public MessagesResponse(boolean error, List<Mensaje> messages) {
        this.error = error;
        this.messages = messages;
    }

    public boolean isError() {
        return error;
    }

    public List<Mensaje> getMessages() {
        return messages;
    }
}
