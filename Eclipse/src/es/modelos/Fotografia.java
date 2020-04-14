package es.modelos;

public class Fotografia {
	
	private String ruta;
	private String tipo_habitacion;
	private int inmueble;

	public Fotografia() {
	}

	public Fotografia(String ruta, String tipo_habitacion, int inmueble) {
		this.ruta = ruta;
		this.tipo_habitacion = tipo_habitacion;
		this.inmueble = inmueble;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getTipo_habitacion() {
		return tipo_habitacion;
	}

	public void setTipo_habitacion(String tipo_habitacion) {
		this.tipo_habitacion = tipo_habitacion;
	}

	public int getInmueble() {
		return inmueble;
	}

	public void setInmueble(int inmueble) {
		this.inmueble = inmueble;
	}
	
}
