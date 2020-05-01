package es.modelos;

public class Favorito {
	
	private int usuario_favorito;
	private int inmueble_favorito;
	private String tipo_anuncio;
	
	public Favorito() {
		
	}
	
	public Favorito(int usuario_favorito, int inmueble_favorito, String tipo_anuncio) {
		this.usuario_favorito = usuario_favorito;
		this.inmueble_favorito = inmueble_favorito;
		this.tipo_anuncio = tipo_anuncio;
	}

	public int getUsuario_favorito() {
		return usuario_favorito;
	}

	public void setUsuario_favorito(int usuario_favorito) {
		this.usuario_favorito = usuario_favorito;
	}

	public int getInmueble_favorito() {
		return inmueble_favorito;
	}

	public void setInmueble_favorito(int inmueble_favorito) {
		this.inmueble_favorito = inmueble_favorito;
	}

	public String getTipo_anuncio() {
		return tipo_anuncio;
	}

	public void setTipo_anuncio(String tipo_anuncio) {
		this.tipo_anuncio = tipo_anuncio;
	}
	
	

}
