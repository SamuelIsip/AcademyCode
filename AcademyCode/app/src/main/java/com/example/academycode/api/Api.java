package com.example.academycode.api;

import com.example.academycode.model.response.DefaultResponse;
import com.example.academycode.model.response.EjerciciosTResponse;
import com.example.academycode.model.response.LibrosTResponse;
import com.example.academycode.model.response.LoginResponse;
import com.example.academycode.model.response.MessagesResponse;
import com.example.academycode.model.response.UsersResponse;
import com.example.academycode.model.response.VideosTResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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


    @FormUrlEncoded
    @PUT("updateuser/{id_usuario}")
    Call<LoginResponse> updateUser(
            @Path("id_usuario") int id_usuario,
            @Field("email") String email,
            @Field("nombre_usuario") String nombre_usuario,
            @Field("telefono") String telefono
    );

    @FormUrlEncoded
    @PUT("updatepassword")
    Call<DefaultResponse> updatePassword(
            @Field("currentpassword") String currentpassword,
            @Field("newpassword") String newpassword,
            @Field("email") String email
    );

    @FormUrlEncoded
    @PUT("restorepassword")
    Call<DefaultResponse> restorePassword(
            @Field("newpassword") String newpassword,
            @Field("email") String email
    );

    @DELETE("deleteuser/{id_usuario}")
    Call<DefaultResponse> deleteUser(
            @Path("id_usuario") int id_usuario
    );

    @GET("allusers")
    Call<UsersResponse> getAllUsers();

    @GET("oneuser/{email}")
    Call<UsersResponse> getOneUser(
            @Path("email") String email
    );

    @GET("librosteoria/{tematica}")
    Call<LibrosTResponse> getAllLibrosT(
            @Path("tematica") String tematica
    );

    @GET("librosejercicios/{tematica}")
    Call<EjerciciosTResponse> getAllEjerciciosT(
            @Path("tematica") String tematica
    );

    @GET("videotutoriales/{tematica}")
    Call<VideosTResponse> getAllVideosT(
            @Path("tematica") String tematica
    );

    @FormUrlEncoded
    @POST("guardarmensajes")
    Call<DefaultResponse> saveMessage(
            @Field("ip_usuario") String ip_usuario,
            @Field("nombre_usuario") String nombre_usuario,
            @Field("email") String email,
            @Field("mensaje") String mensaje
    );

    @GET("allmessages")
    Call<MessagesResponse> getAllMessages();

    @GET("allamigos/{nombre_user}")
    Call<UsersResponse> getAllFriends(
            @Path("nombre_user") String nombre_user
    );

    @FormUrlEncoded
    @POST("guardaramigo")
    Call<DefaultResponse> saveFriend(
            @Field("nombre_user") String nombre_user,
            @Field("nombre_user_amigo") String nombre_user_amigo
    );


}
