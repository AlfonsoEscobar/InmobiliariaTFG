package es.dao.util;

import es.modelos.Inmueble;

public class InfoAnuncio {
	private int id_inmueble;
	private double precio;
	private Inmueble inmueble;
	
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

	public Inmueble getInmueble() {
		return inmueble;
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}
	
	
}
