package com.tfg.inmobiliariatfg.utiles;

import com.tfg.inmobiliariatfg.modelos.InfoUsuario;
import com.tfg.inmobiliariatfg.modelos.Usuario;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RegistroService {
    @GET("usuario/alfonso@gmail.es/{Pass}")
    Call<InfoUsuario> getUsuario(@Path("Pass") String Pass);
}
