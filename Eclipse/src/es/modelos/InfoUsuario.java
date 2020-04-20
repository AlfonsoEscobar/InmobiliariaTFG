package es.modelos;

import java.util.List;

public class InfoUsuario {
	//private Usuario usuario;
	private String nombreUsuario;
	private String correoUsuario;
	private String telefono1;
	private String telefono2;
	private List<Inmueble> inmuebles;
	private List<Anuncio> anuncios;
	private List<Favorito> favoritos;
	
	
	public InfoUsuario(String nombreUsuario, String correoUsuario, String telefono1, String telefono2,
			List<Inmueble> inmuebles, List<Anuncio> anuncios, List<Favorito> favoritos) {
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
	public List<Inmueble> getInmuebles() {
		return inmuebles;
	}
	public void setInmuebles(List<Inmueble> inmuebles) {
		this.inmuebles = inmuebles;
	}
	public List<Anuncio> getAnuncios() {
		return anuncios;
	}
	public void setAnuncios(List<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}
	public List<Favorito> getFavoritos() {
		return favoritos;
	}
	public void setFavoritos(List<Favorito> favoritos) {
		this.favoritos = favoritos;
	}
	
	
}
