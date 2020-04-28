package com.tfg.inmobiliariatfg.modelos;

import java.util.Date;

public class InfoAnuncio {

    private int id_inmueble;
    private double precio;
    private String tipo_anuncio;
    private Date fecha_anunciado;
    private Date fecha_ultima_actualizacion;
    private Inmueble inmueble;

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
}
