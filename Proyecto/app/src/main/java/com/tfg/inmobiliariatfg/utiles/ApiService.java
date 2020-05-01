package com.tfg.inmobiliariatfg.utiles;


import com.tfg.inmobiliariatfg.modelos.Anuncio;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.InfoUsuario;
import com.tfg.inmobiliariatfg.modelos.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("usuario/{correoUsuario}/{contrasena}")
    Call<Usuario> getUsuario(@Path("correoUsuario") String correoUsuario, @Path("contrasena") String contrasena);

    @GET("anuncio/{tipoAnuncio}/{localidadAnuncio}")
    Call<List<InfoAnuncio>> getAnuncioLocalidad(@Path("tipoAnuncio") String tipoAnuncio, @Path("localidadAnuncio") String localidadAnuncio);
}
