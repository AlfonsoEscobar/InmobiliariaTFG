package es.dao.util;

import java.io.File;
import java.sql.Date;
import java.util.List;

import es.modelos.Inmueble;

public class InfoAnuncio {
	private int id_inmueble;
	private double precio;
	private String tipo_anuncio;
	private Date fecha_anunciado;
	private Date fecha_ultima_actualizacion;
	private Inmueble inmueble;
	private String telefono1;
	private String telefono2;
	private String correo;
	
	private List<String> listaRutas;
	private File imagen;
	
	public InfoAnuncio() {
		
	}
	
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
	
	public String getTipo_anuncio() {
		return tipo_anuncio;
	}
	
	public void setTipo_anuncio(String tipo_anuncio) {
		this.tipo_anuncio = tipo_anuncio;
	}
	
	public Date getFeha_anunciado() {
		return fecha_anunciado;
	}
	
	public void setFecha_anunciado(Date fecha) {
		this.fecha_anunciado = fecha;
	}
	
	public Date getFecha_ultima_actualizacion() {
		return fecha_ultima_actualizacion;
	}
	
	public void setFecha_ultima_actualizacion(Date fecha_actualizacion) {
		this.fecha_ultima_actualizacion = fecha_actualizacion;
	}

	public Inmueble getInmueble() {
		return inmueble;
	}

	public void setInmueble(Inmueble inmueble) {
		this.inmueble = inmueble;
	}
	
	public File getImagen() {
		return imagen;
	}
	
	public void setImagen(File img) {
		this.imagen = img;
	}
	
	public List<String> getListaRutas(){
		return listaRutas;
	}
	
	public void setListaRutas(List<String> lista) {
		this.listaRutas = lista;
	}
	
	public String getTelefono1(){
		return telefono1;
	}
	
	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}
	
	public String getTelefono2(){
		return telefono2;
	}
	
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	
	public String getCorreo(){
		return correo;
	}
	
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	
}
