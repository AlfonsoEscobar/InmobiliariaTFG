package es.modelos;

public class Usuario {
	
	private String correo;
	private String contrasena;
	private int id_usuario;
	private String nombre;
	private String telefono1;
	private String telefono2;
	
	public Usuario() {
	}
	
	public Usuario(String correo, String nombre, String telefono1,
			String telefono2) {
		this.correo = correo;
		this.nombre = nombre;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
	}

	public Usuario(String correo, String contrasena, String nombre, String telefono1,
					String telefono2) {
		this.correo = correo;
		this.contrasena = contrasena;
		this.nombre = nombre;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
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
	
	public int getId_usuario() {
		return id_usuario;
	}
	
	public void setId_usuario(int id) {
		this.id_usuario = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
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

}
