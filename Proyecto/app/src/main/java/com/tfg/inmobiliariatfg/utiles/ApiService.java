package com.tfg.inmobiliariatfg.utiles;


import com.tfg.inmobiliariatfg.modelos.InfoUsuario;
import com.tfg.inmobiliariatfg.modelos.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @GET("usuario/{correoUsuario}/{contrasena}")
    Call<Usuario> getUsuario(@Path("correoUsuario") String correoUsuario, @Path("contrasena") String contrasena);

    @POST
    Call<Usuario> setUsuario(@Path("correoUsuario") String correoUsuario,@Path("nombre") String nombre,@Path("telefono1") String telefono1,@Path("telefono2") String telefono2, @Path("contrasena") String contrasena);

}
