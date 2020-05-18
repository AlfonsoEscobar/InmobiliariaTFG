package com.tfg.inmobiliariatfg.utiles;

import com.tfg.inmobiliariatfg.modelos.InfoAnuncio;
import com.tfg.inmobiliariatfg.modelos.Inmueble;
import com.tfg.inmobiliariatfg.modelos.Usuario;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {


    @PUT("usuario/{idUsuario}")
    Call<Void> putModificarUsuarioPerfil(@Path("idUsuario") int idUsuario, @Body Usuario usuario);

    @Multipart
    @POST("fotografia/inmueble/{nombre}")
    Call<ResponseBody> postImagenPerfil(
            @Part("nombre") RequestBody nombre,
            @Part MultipartBody.Part file
    );
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

    @POST("usuario")
    Call<Void> createUsuario(@Body Usuario usuario);

}
