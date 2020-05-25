package com.tfg.inmobiliariatfg.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Favorito implements Serializable {

    @SerializedName("usuario_favorito")
    @Expose
    private int usuario_favorito;
    @SerializedName("inmueble_favorito")
    @Expose
    private int inmueble_favorito;
    @SerializedName("tipo_anuncio")
    @Expose
    private String tipo_anuncio;

    public Favorito(int usuario_favorito, int inmueble_favorito, String tipo_anuncio) {
        this.usuario_favorito = usuario_favorito;
        this.inmueble_favorito = inmueble_favorito;
        this.tipo_anuncio = tipo_anuncio;
    }
    public Favorito(){

    }

    public int getUsuario_favorito() {
        return usuario_favorito;
    }

    public void setUsuario_favorito(int usuario_favorito) {
        this.usuario_favorito = usuario_favorito;
    }

    public int getInmueble_favorito() {
        return inmueble_favorito;
    }

    public void setInmueble_favorito(int inmueble_favorito) {
        this.inmueble_favorito = inmueble_favorito;
    }

    public String getTipo_anuncio() {
        return tipo_anuncio;
    }

    public void setTipo_anuncio(String tipo_anuncio) {
        this.tipo_anuncio = tipo_anuncio;
    }


}
