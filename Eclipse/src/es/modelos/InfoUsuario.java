package es.modelos;

import java.util.LinkedList;

public class InfoUsuario {
	// private Usuario usuario;
	private String nombreUsuario;
	private String correoUsuario;
	private String telefono1;
	private String telefono2;
	private LinkedList<Inmueble> inmuebles; // = new LinkedList<Inmueble>();
	private LinkedList<Anuncio> anuncios;
	private LinkedList<Favorito> favoritos;

	public InfoUsuario() {
		
	}
	
	public InfoUsuario(String nombreUsuario, String correoUsuario, String telefono1, String telefono2,
			LinkedList<Inmueble> inmuebles, LinkedList<Anuncio> anuncios, LinkedList<Favorito> favoritos) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.correoUsuario = correoUsuario;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.inmuebles = inmuebles;
		this.anuncios = anuncios;
		this.favoritos = favoritos;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public String getTelefono1() {
		return telefono1;
	}

	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}

	public String getTelefono2() {
		return telefono2;
	}

	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}

	public LinkedList<Inmueble> getInmuebles() {
		return inmuebles;
	}

	public void setInmuebles(LinkedList<Inmueble> inmuebles) {
		this.inmuebles = inmuebles;
	}

	public LinkedList<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(LinkedList<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}

	public LinkedList<Favorito> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(LinkedList<Favorito> favoritos) {
		this.favoritos = favoritos;
	}

}
