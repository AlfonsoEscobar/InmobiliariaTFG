package com.tfg.inmobiliariatfg.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedList;

public class InfoUsuario {
    @SerializedName("nombreUsuario")
    @Expose
    private String nombreUsuario;
    @SerializedName("correoUsuario")
    @Expose
    private String correoUsuario;
    @SerializedName("id_usuario")
    @Expose
    private int id_usuario;
    @SerializedName("telefono1")
    @Expose
    private String telefono1;
    @SerializedName("telefono2")
    @Expose
    private String telefono2;
    @SerializedName("inmuebles")
    @Expose
    private LinkedList<Inmueble> inmuebles;
    @SerializedName("anuncios")
    @Expose
    private LinkedList<Anuncio> anuncios;
   /* @SerializedName("favoritos")
    @Expose
    private LinkedList<Favorito> favoritos;*/

    public InfoUsuario() {
    }

    public InfoUsuario(String nombreUsuario, String correoUsuario, String telefono1, String telefono2,
                       int id_usuario, LinkedList<Inmueble> inmuebles, LinkedList<Anuncio> anuncios/*, LinkedList<Favorito> favoritos*/) {
        super();
        this.nombreUsuario = nombreUsuario;
        this.correoUsuario = correoUsuario;
        this.id_usuario = id_usuario;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.inmuebles = inmuebles;
        this.anuncios = anuncios;
        //this.favoritos = favoritos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public LinkedList<Inmueble> getInmuebles() {
        return inmuebles;
    }

    public void setInmuebles(LinkedList<Inmueble> inmuebles) {
        this.inmuebles = inmuebles;
    }

    public LinkedList<Anuncio> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(LinkedList<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

    /*public LinkedList<Favorito> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(LinkedList<Favorito> favoritos) {
        this.favoritos = favoritos;
    }*/

}
