package es.dao.util;

import java.util.LinkedList;

import es.modelos.Anuncio;
import es.modelos.Favorito;
import es.modelos.Inmueble;

public class InfoUsuario {
	// private Usuario usuario;
	private String nombreUsuario;
	private String correoUsuario;
	private int id_usuario;
	private String telefono1;
	private String telefono2;
	private LinkedList<Inmueble> inmuebles;
	private LinkedList<Anuncio> anuncios;
	private LinkedList<Favorito> favoritos;

	public InfoUsuario() {
	}
	
	public InfoUsuario(String nombreUsuario, String correoUsuario, String telefono1, String telefono2,
			int id_usuario, LinkedList<Inmueble> inmuebles, LinkedList<Anuncio> anuncios, LinkedList<Favorito> favoritos) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.correoUsuario = correoUsuario;
		this.id_usuario = id_usuario;
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
	
	public int getId_usuario() {
		return id_usuario;
	}
	
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
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