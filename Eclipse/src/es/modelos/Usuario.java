package es.modelos;

import java.awt.Image;

public class Usuario {
	
	private String correo;
	private String contrasena;
	private String id_usuario;
	private String nombre;
	private String telefono;
	private String telefono2;
	private Image imagen_prefil;
	
	public Usuario() {
		
	}
	
	public Usuario(String correo, String contrasena, String id_usuario, String nombre, String telefono,
					String telefono2, Image imagen_prefil) {
		this.correo = correo;
		this.contrasena = contrasena;
		this.id_usuario = id_usuario;
		this.nombre = nombre;
		this.telefono = telefono;
		this.telefono2 = telefono2;
		this.imagen_prefil = imagen_prefil;
	}
	
	public String getCorreo() {
		return correo;
	}
	
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public String getId_usuario() {
		return id_usuario;
	}
	
	public void setId_usuario(String id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getTelefono2() {
		return telefono2;
	}
	
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	
	public Image getImagen_prefil() {
		return imagen_prefil;
	}
	
	public void setImagen_prefil(Image imagen_prefil) {
		this.imagen_prefil = imagen_prefil;
	}

}
