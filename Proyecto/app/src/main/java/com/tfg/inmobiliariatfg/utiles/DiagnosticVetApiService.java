package com.tfg.inmobiliariatfg.utiles;


import com.tfg.inmobiliariatfg.modelos.InfoUsuario;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Path;

public interface DiagnosticVetApiService {

    @GET("usuario/{correoUsuario}/{contrasena}")
    Call<InfoUsuario> getUsuario(@Path("correoUsuario") String correoUsuario, @Path("contrasena") String contrasena);

}
