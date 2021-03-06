package com.tfg.inmobiliariatfg.modelos;

import java.io.Serializable;
import java.util.Date;

public class ValoresBusqueda implements Serializable {

    private String tipo_anuncio;
    private String localidad;
    private String calle;
    private int piso;

    private double min_metros2;
    private double max_metros2;

    private int num_habitaciones;
    private int min_num_habitaciones;

    private int num_banos;
    private int min_num_banos;

    private String tipo_edificacion;
    private String tipo_obra;
    private String equipamiento;
    private String exteriores;
    private String garaje;
    private String trastero;
    private String ascensor;
    private String ultima_planta;
    private String mascotas;

    private double min_precio;
    private double max_precio;

    private Date fecha_anunciado;
    private Date fecha_ultima_actualizacion;

    public ValoresBusqueda() {
    }

    public String getTipo_anuncio() {
        return tipo_anuncio;
    }

    public void setTipo_anuncio(String tipo_anuncio) {
        this.tipo_anuncio = tipo_anuncio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public double getMin_metros2() {
        return min_metros2;
    }

    public void setMin_metros2(double min_metros2) {
        this.min_metros2 = min_metros2;
    }

    public double getMax_metros2() {
        return max_metros2;
    }

    public void setMax_metros2(double max_metros2) {
        this.max_metros2 = max_metros2;
    }

    public int getNum_habitaciones() {
        return num_habitaciones;
    }

    public void setNum_habitaciones(int num_habitaciones) {
        this.num_habitaciones = num_habitaciones;
    }

    public int getMin_num_habitaciones() {
        return min_num_habitaciones;
    }

    public void setMin_num_habitaciones(int min_num_habitaciones) {
        this.min_num_habitaciones = min_num_habitaciones;
    }

    public int getNum_banos() {
        return num_banos;
    }

    public void setNum_banos(int num_banos) {
        this.num_banos = num_banos;
    }

    public int getMin_num_banos() {
        return min_num_banos;
    }

    public void setMin_num_banos(int min_num_banos) {
        this.min_num_banos = min_num_banos;
    }

    public String getTipo_edificacion() {
        return tipo_edificacion;
    }

    public void setTipo_edificacion(String tipo_edificacion) {
        this.tipo_edificacion = tipo_edificacion;
    }

    public String getTipo_obra() {
        return tipo_obra;
    }

    public void setTipo_obra(String tipo_obra) {
        this.tipo_obra = tipo_obra;
    }

    public String getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(String equipamiento) {
        this.equipamiento = equipamiento;
    }

    public String getExteriores() {
        return exteriores;
    }

    public void setExteriores(String exteriores) {
        this.exteriores = exteriores;
    }

    public String getGaraje() {
        return garaje;
    }

    public void setGaraje(String garaje) {
        this.garaje = garaje;
    }

    public String getTrastero() {
        return trastero;
    }

    public void setTrastero(String trastero) {
        this.trastero = trastero;
    }

    public String getAscensor() {
        return ascensor;
    }

    public void setAscensor(String ascensor) {
        this.ascensor = ascensor;
    }

    public String getUltima_planta() {
        return ultima_planta;
    }

    public void setUltima_planta(String ultima_planta) {
        this.ultima_planta = ultima_planta;
    }

    public String getMascotas() {
        return mascotas;
    }

    public void setMascotas(String mascotas) {
        this.mascotas = mascotas;
    }

    public double getMin_precio() {
        return min_precio;
    }

    public void setMin_precio(double min_precio) {
        this.min_precio = min_precio;
    }

    public double getMax_precio() {
        return max_precio;
    }

    public void setMax_precio(double max_precio) {
        this.max_precio = max_precio;
    }

    public Date getFecha_anunciado() {
        return fecha_anunciado;
    }

    public void setFecha_anunciado(Date fecha_anunciado) {
        this.fecha_anunciado = fecha_anunciado;
    }

    public Date getFecha_ultima_actualizacion() {
        return fecha_ultima_actualizacion;
    }

    public void setFecha_ultima_actualizacion(Date fecha_ultima_actualizacion) {
        this.fecha_ultima_actualizacion = fecha_ultima_actualizacion;
    }


}
