package com.tfg.inmobiliariatfg.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("correo")
    @Expose
    private String correo;

    @SerializedName("contrasena")
    @Expose
    private String contrasena;

    @SerializedName("id_usuario")
    @Expose
    private int id_usuario;

    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("telefono1")
    @Expose
    private String telefono1;

    @SerializedName("telefono2")
    @Expose
    private String telefono2;

    @SerializedName("imagen_perfil")
    @Expose
    private byte[] imagen_perfil;

    public Usuario() {

    }

    public Usuario(String correo, String contrasena, String nombre, String telefono1,
                   String telefono2, byte[] imagen_perfil) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.imagen_perfil = imagen_perfil;
        //this.id_usuario = 0;//Lo asignaremos cuando vayamos a insertarlo en la base de datos
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id) {
        this.id_usuario = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public byte[] getImagen_perfil() {
        return imagen_perfil;
    }

    public void setImagen_perfil(byte[] imagen_perfil) {
        this.imagen_perfil = imagen_perfil;
    }

}
