package com.example.academycode.model.response;

import com.example.academycode.model.Usuario;

import java.util.List;

public class UsersResponse {
//AQUÍ, EL NOMBRE DE LIST, IGUAL QUE EN PHP
    private boolean error;
    private List<Usuario> users;

    public UsersResponse(boolean error, List<Usuario> users) {
        this.error = error;
        this.users = users;
    }

    public boolean isError() {
        return error;
    }

    public List<Usuario> getUsers() {
        return users;
    }
}
