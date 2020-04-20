package es.modelos;

import java.util.Date;

public class Anuncio {
	
	private int id_inmueble;
	private String tipo_anuncio;
	private double precio;
	private Date fecha_anuncio;
	private Date fecha_ultima_actualizacion;
	
	public Anuncio() {
	}

	public Anuncio(int id_inmueble, String tipo_anuncio, double precio, Date fecha_anuncio,
					Date fecha_ultima_actualizacion) {
		this.id_inmueble = id_inmueble;
		this.tipo_anuncio = tipo_anuncio;
		this.precio = precio;
		this.fecha_anuncio = fecha_anuncio;
		this.fecha_ultima_actualizacion = fecha_ultima_actualizacion;
	}

	public int getId_inmueble() {
		return id_inmueble;
	}

	public void setId_inmueble(int id_inmueble) {
		this.id_inmueble = id_inmueble;
	}

	public String getTipo_anuncio() {
		return tipo_anuncio;
	}

	public void setTipo_anuncio(String tipo_anuncio) {
		this.tipo_anuncio = tipo_anuncio;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Date getFecha_anuncio() {
		return fecha_anuncio;
	}

	public void setFecha_anuncio(Date fecha_anuncio) {
		this.fecha_anuncio = fecha_anuncio;
	}

	public Date getFecha_ultima_actualizacion() {
		return fecha_ultima_actualizacion;
	}

	public void setFecha_ultima_actualizacion(Date fecha_ultima_actualizacion) {
		this.fecha_ultima_actualizacion = fecha_ultima_actualizacion;
	}
	
}
