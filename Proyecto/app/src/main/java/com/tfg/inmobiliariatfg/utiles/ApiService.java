package com.tfg.inmobiliariatfg.utiles;

import com.tfg.inmobiliariatfg.modelos.Favorito;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.modelos.ValoresPredeterminadosInmueble;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @PUT("usuario/{idUsuario}")
    Call<Void> putModificarUsuarioPerfil(@Path("idUsuario") int idUsuario, @Body Usuario usuario);

    @PUT("usuario/{idUsuario}")
    Call<Void> putModificarInmueblePerfil(@Path("idUsuario") int idUsuario, @Body Inmueble usuario);

    @PUT("usuario/{idUsuario}")
    Call<Void> putModificarAnuncioPerfil(@Path("idUsuario") int idUsuario, @Body InfoAnuncio usuario);

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    @GET("valores")
    Call<ValoresPredeterminadosInmueble> getSpinnersRegistrarInmueble();

    @GET("favorito/info/{idUsuario}")
    Call<List<InfoAnuncio>> getFavortiosUsuario(@Path("idUsuario") int idUsuario);

    @GET("inmueble/{propietario}")
    Call<List<Inmueble>> getInmueblePropietario(@Path("propietario") int propietario);

    @GET("anuncio/{propietario}")
    Call<List<InfoAnuncio>> getAnuncioPropietario(@Path("propietario") int propietario);

    @GET("usuario/{correoUsuario}/{contrasena}")
    Call<Usuario> getUsuario(@Path("correoUsuario") String correoUsuario, @Path("contrasena") String contrasena);

    @GET("anuncio/{tipoAnuncio}/{localidadAnuncio}")
    Call<List<InfoAnuncio>> getAnuncioLocalidad(@Path("tipoAnuncio") String tipoAnuncio, @Path("localidadAnuncio") String localidadAnuncio);

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    @POST("usuario")
    Call<Void> createUsuario(@Body Usuario usuario);

    @POST("inmueble")
    Call<Void> createInmueble(@Body Inmueble inmueble);

    @POST("favorito")
    Call<Void> createFavorito(@Body Favorito favorito);

    @POST("anuncio")
    Call<Void> createAnuncio(@Body InfoAnuncio anuncio);

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    @DELETE("usuario/{idUsuario}")
    Call<Void> eliminarUsuario(@Path("idUsuario") int idUsuario);

    @DELETE("inmueble/{idUsuario}")
    Call<Void> eliminarInmueble(@Path("idUsuario") int idUsuario);

    @DELETE("favorito/{idUsuario}")
    Call<Void> eliminarFavorito(@Path("idUsuario") int idUsuario);

    @DELETE("anuncio/{idInmueble}/{tipoAnuncio}")
    Call<Void> eliminarAnuncio(@Path("idInmueble") int idInmueble, @Path("idUsuario") int idUsuario);

}
