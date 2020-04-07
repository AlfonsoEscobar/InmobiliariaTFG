package es.modelos;

public class Fotografia {
	
	private String ruta;
	private String tipo_hab;
	private String inmueble;
	
	public Fotografia() {
	}

	public Fotografia(String ruta, String tipo_hab, String inmueble) {
		this.ruta = ruta;
		this.tipo_hab = tipo_hab;
		this.inmueble = inmueble;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getTipo_hab() {
		return tipo_hab;
	}

	public void setTipo_hab(String tipo_hab) {
		this.tipo_hab = tipo_hab;
	}

	public String getInmueble() {
		return inmueble;
	}

	public void setInmueble(String inmueble) {
		this.inmueble = inmueble;
	}
	
}
