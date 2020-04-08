/*El atributo tipo_anuncio es booleano para tomar dos valores, true/false, a cada uno de estos valores le atribuiremos venta/alquiler
 **/
package es.modelos;

import java.util.Date;

public class Anuncio {
	
	private String id_inmueble;
	private boolean tipo_anuncio;
	private float precio;
	private Date fecha_anuncio;
	private Date fecha_ultima_actualizacion;
	
	public Anuncio() {
	}

	public Anuncio(String id_inmueble, boolean tipo_anuncio, float precio, Date fecha_anuncio,
					Date fecha_ultima_actualizacion) {
		this.id_inmueble = id_inmueble;
		this.tipo_anuncio = tipo_anuncio;
		this.precio = precio;
		this.fecha_anuncio = fecha_anuncio;
		this.fecha_ultima_actualizacion = fecha_ultima_actualizacion;
	}

	public String getId_inmueble() {
		return id_inmueble;
	}

	public void setId_inmueble(String id_inmueble) {
		this.id_inmueble = id_inmueble;
	}

	public boolean isTipo_anuncio() {
		return tipo_anuncio;
	}

	public void setTipo_anuncio(boolean tipo_anuncio) {
		this.tipo_anuncio = tipo_anuncio;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
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
