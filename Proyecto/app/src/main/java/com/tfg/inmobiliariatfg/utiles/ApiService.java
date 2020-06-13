package com.tfg.inmobiliariatfg.utiles;

import com.tfg.inmobiliariatfg.modelos.Anuncio;
import com.tfg.inmobiliariatfg.modelos.Favorito;
import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.modelos.Usuario;
import com.tfg.inmobiliariatfg.modelos.ValoresBusqueda;
import com.tfg.inmobiliariatfg.modelos.ValoresPredeterminadosInmueble;

import java.io.File;
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

    @PUT("inmueble/{idInmueble}")
    Call<Void> putModificarInmueble(@Path("idInmueble") int idInmueble, @Body Inmueble inmueble);

    @PUT("anuncio/{idInmueble}/{tipoAnuncio}/{precio}")
    Call<Void> putModificarAnuncio(@Path("idInmueble") int idInmueble, @Path("tipoAnuncio") String tipoAnuncio,@Path("precio")  Double precio);

    @PUT("anuncio/criteriosBusqueda")
    Call <List<InfoAnuncio>> getAnunciosAvanzados(@Body ValoresBusqueda valoresBusqueda);

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    @GET("fotografia/{idInmueble}")
    Call<List<String>> getStringsUriFotos(@Path("idInmueble") int idInmueble);

    @GET("valores")
    Call<ValoresPredeterminadosInmueble> getSpinnersRegistrarInmueble();

    @GET("favorito/{idUsuario}")
    Call <List<Favorito>> getCompararFavorito(@Path("idUsuario") int idUsuario);

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
    Call<Void> createAnuncio(@Body Anuncio anuncio);

    //-----------------------------------------------------------------------------------------------------------------------------------------------

    @DELETE("usuario/{idUsuario}")
    Call<Void> eliminarUsuario(@Path("idUsuario") int idUsuario);

    @DELETE("inmueble/{idInmueble}")
    Call<Void> eliminarInmueble(@Path("idInmueble") int idInmueble);

    @DELETE("favorito/{idUsuario}/{idInmueble}/{tipo_anuncio}")
    Call<Void> eliminarFavorito(@Path("idUsuario") int idUsuario, @Path("idInmueble") int idInmueble, @Path("tipo_anuncio") String tipo_anuncio);

    @DELETE("anuncio/{idInmueble}/{tipoAnuncio}")
    Call<Void> eliminarAnuncio(@Path("idInmueble") int idInmueble, @Path("tipoAnuncio") String tipoAnuncio);

}
