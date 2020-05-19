package com.example.academycode.model.response;

import com.example.academycode.model.EjercicioTeoria;

import java.util.List;

public class EjerciciosTResponse {

    private boolean error;
    private List<EjercicioTeoria> ejercicios;

    public EjerciciosTResponse(boolean error, List<EjercicioTeoria> ejercicios) {
        this.error = error;
        this.ejercicios = ejercicios;
    }

    public boolean isError() {
        return error;
    }

    public List<EjercicioTeoria> getEjercicios() {
        return ejercicios;
    }
}
