package com.tfg.inmobiliariatfg.modelos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InfoAnuncio implements Serializable {

    @SerializedName("id_inmueble")
    @Expose
    private int id_inmueble;
    @SerializedName("precio")
    @Expose
    private double precio;
    @SerializedName("tipo_anuncio")
    @Expose
    private String tipo_anuncio;
    @SerializedName("feha_anunciado")
    @Expose
    private Date fecha_anunciado;
    @SerializedName("fecha_ultima_actualizacion")
    @Expose
    private Date fecha_ultima_actualizacion;
    @SerializedName("telefono1")
    @Expose
    private String telefono1;
    @SerializedName("telefono2")
    @Expose
    private String telefono2;
    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("inmueble")
    @Expose
    private Inmueble inmueble;
    @SerializedName("listaRutas")
    @Expose
    private List<String> listaRutas;
    public InfoAnuncio() {

    }

    public InfoAnuncio(int id_inmueble, double precio, Inmueble inmueble) {
        super();
        this.id_inmueble = id_inmueble;
        this.precio = precio;
        this.inmueble = inmueble;
    }

    public int getId_inmueble() {
        return id_inmueble;
    }

    public void setId_inmueble(int id_inmueble) {
        this.id_inmueble = id_inmueble;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipo_anuncio() {
        return tipo_anuncio;
    }

    public void setTipo_anuncio(String tipo_anuncio) {
        this.tipo_anuncio = tipo_anuncio;
    }

    public Date getFeha_anunciado() {
        return fecha_anunciado;
    }

    public void setFecha_anunciado(Date fecha) {
        this.fecha_anunciado = fecha;
    }

    public Date getFecha_ultima_actualizacion() {
        return fecha_ultima_actualizacion;
    }

    public void setFecha_ultima_actualizacion(Date fecha_actualizacion) {
        this.fecha_ultima_actualizacion = fecha_actualizacion;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Date getFecha_anunciado() {
        return fecha_anunciado;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public String getCorreo() {
        return correo;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<String> getListaRutas() {
        return listaRutas;
    }

    public void setListaRutas(List<String> listaRutas) {
        this.listaRutas = listaRutas;
    }

}
