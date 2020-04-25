package com.example.academycode.api;

import com.example.academycode.model.DefaultResponse;
import com.example.academycode.model.LoginResponse;
import com.example.academycode.model.UsuarioResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @FormUrlEncoded
    @POST("createuser")
    Call<DefaultResponse> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("nombre_usuario") String nombre_usuario,
            @Field("telefono") String telefono
    );

    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("existuser/{email}")
    Call<UsuarioResponse> getUser(
            @Path("email") String email
    );

}
