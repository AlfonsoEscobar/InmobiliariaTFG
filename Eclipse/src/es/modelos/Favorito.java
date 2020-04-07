package es.modelos;

public class Favorito {
	
	private String usuario_fav;
	private String inmueble_fav;
	private String tipo_anuncio;
	
	public Favorito() {
	}

	public Favorito(String usuario_fav, String inmueble_fav, String tipo_anuncio) {
		super();
		this.usuario_fav = usuario_fav;
		this.inmueble_fav = inmueble_fav;
		this.tipo_anuncio = tipo_anuncio;
	}

	public String getUsuario_fav() {
		return usuario_fav;
	}

	public void setUsuario_fav(String usuario_fav) {
		this.usuario_fav = usuario_fav;
	}

	public String getInmueble_fav() {
		return inmueble_fav;
	}

	public void setInmueble_fav(String inmueble_fav) {
		this.inmueble_fav = inmueble_fav;
	}

	public String getTipo_anuncio() {
		return tipo_anuncio;
	}

	public void setTipo_anuncio(String tipo_anuncio) {
		this.tipo_anuncio = tipo_anuncio;
	}
	
}
