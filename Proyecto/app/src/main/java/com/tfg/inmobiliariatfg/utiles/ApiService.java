package com.tfg.inmobiliariatfg.utiles;

import android.icu.text.IDNA;

import com.google.gson.annotations.JsonAdapter;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.modelos.Usuario;

import java.net.URI;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("favortio/{idUsuario}")
    Call<List<InfoAnuncio>> getFavortiosUsuario(@Path("idUsuario") int idUsuario);

    @GET("inmueble/{propietario}")
    Call<List<Inmueble>> getInmueblePropietario(@Path("propietario") int propietario);

    @GET("anuncio/{propietario}")
    Call<List<InfoAnuncio>> getAnuncioPropietario(@Path("propietario") int propietario);

    @GET("usuario/{correoUsuario}/{contrasena}")
    Call<Usuario> getUsuario(@Path("correoUsuario") String correoUsuario, @Path("contrasena") String contrasena);

    @GET("anuncio/{tipoAnuncio}/{localidadAnuncio}")
    Call<List<InfoAnuncio>> getAnuncioLocalidad(@Path("tipoAnuncio") String tipoAnuncio, @Path("localidadAnuncio") String localidadAnuncio);

    @POST("usuario")
    Call<Void> createUsuario(@Body Usuario usuario);

    /*
    @POST("usuario/")
    Call <Usuario> createUsuario(
      @Field("correo")  String correo,
      @Field("nombre") String nombre,
      @Field("id_usuario") int id_usuario,
      @Field("contrasena") String contrasena,
      @Field("telefono1") String telefono1,
      @Field("telefono2") String telefono2,
      @Field("imagen_perfil") byte[] imagenPerfil
    );*/
}
