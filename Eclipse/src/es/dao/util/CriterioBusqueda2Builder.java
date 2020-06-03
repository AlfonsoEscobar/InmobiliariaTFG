package es.dao.util;

import java.sql.Date;

public class CriterioBusqueda2Builder {
	private String localidad;
	private String tipo_anuncio;
	
	/*private String calle;
	private int piso;

	private double min_metros2;
	private double max_metros2;

	private int num_habitaciones;
	private int max_num_habitaciones;

	private int num_banos;
	private int max_num_banos;

	private String tipo_edificacion;
	private String tipo_obra;
	private String equipamiento;
	private String exteriores;
	private boolean garaje;
	private boolean trastero;
	private boolean ascensor;
	private boolean ultima_planta;
	private boolean mascotas;

	private double min_precio;
	private double max_precio;

	private Date fecha_anunciado;
	private Date fecha_ultima_actualizacion;*/
	
	private String sentencia;
	
	
	public CriterioBusqueda2Builder() {
		this.sentencia = "";
	}
	
	public CriterioBusqueda2Builder conLocalidad(String localidad) {
		if(comprobarCadena(localidad) == true) {
			this.localidad = localidad;
			sentencia = sentencia + " and i.localidad = '" + localidad + "'";
		}
		return this;
	}
	
	public CriterioBusqueda2Builder conTipo_Anuncio(String tipo_anuncio) {
		if(comprobarCadena(localidad) == true) {
			this.tipo_anuncio = tipo_anuncio;
			sentencia = sentencia + " and a.tipo_anuncio = '" + tipo_anuncio + "'";
		}
		return this;
	}
	
	public CriterioBusqueda2Builder conCalle(String calle) {
		if(comprobarCadena(calle) == true) 
			sentencia = sentencia + " and i.calle = '" + calle + "'";
		return this;
	}

	public CriterioBusqueda2Builder conPiso(int piso) {
		sentencia = sentencia + " and i.piso = '" + piso + "'";
		return this;
	}
	
	public CriterioBusqueda2Builder conMetros2(double min_metros2, double max_metros2) {
		sentencia = sentencia + " and i.metros2 between " + min_metros2 + " and " + max_metros2;
		return this;
	}

	public CriterioBusqueda2Builder conNum_habitaciones(int num_habitaciones) {
		sentencia = sentencia + " and i.num_habitaciones = " + num_habitaciones;
		return this;
	}

	public CriterioBusqueda2Builder conMin_num_habitaciones(int min_num_habitaciones) {
		sentencia = sentencia + " and i.num_habitaciones > " + (min_num_habitaciones - 1);
		return this;
	}

	public CriterioBusqueda2Builder conNum_banos(int num_banos) {
		sentencia = sentencia + " and i.num_banos = " + num_banos;
		return this;
	}

	public CriterioBusqueda2Builder conMin_num_banos(int min_num_banos) {
		sentencia = sentencia + " and i.num_banos > " + (min_num_banos - 1);
		return this;
	}

	public CriterioBusqueda2Builder conTipo_edificacion(String tipo_edificacion) {
		sentencia = sentencia + " and i.tipo_edificacion = '" + tipo_edificacion + "'";
		return this;
	}

	public CriterioBusqueda2Builder conTipo_obra(String tipo_obra) {
		sentencia = sentencia + " and i.tipo_obra = '" + tipo_obra + "'";
		return this;
	}

	public CriterioBusqueda2Builder conEquipamiento(String equipamiento) {
		sentencia = sentencia + " and i.equipamiento = '" + equipamiento + "'";
		return this;
	}

	public CriterioBusqueda2Builder conExteriores(String exteriores) {
		sentencia = sentencia + " and i.exteriores = '" + exteriores + "'";
		return this;
	}

	public CriterioBusqueda2Builder conGaraje(boolean garaje) {
		sentencia = sentencia + " and i.garaje = " + garaje;
		return this;
	}

	public CriterioBusqueda2Builder conTrastero(boolean trastero) {
		sentencia = sentencia + " and i.trastero = " + trastero;
		return this;
	}

	public CriterioBusqueda2Builder conAscensor(boolean ascensor) {
		sentencia = sentencia + " and i.ascensor = " + ascensor;
		return this;
	}

	public CriterioBusqueda2Builder conUltima_planta(boolean ultima_planta) {
		sentencia = sentencia + " and i.ultima_planta = " + ultima_planta;
		return this;
	}

	public CriterioBusqueda2Builder conMascotas(boolean mascotas) {
		sentencia = sentencia + " and i.mascotas = " + mascotas;
		return this;
	}

	public CriterioBusqueda2Builder conPrecio (double min_precio, double max_precio) {
		sentencia = sentencia + " and a.precio between " + min_precio + " and " + max_precio;
		return this;
	}

	public CriterioBusqueda2Builder conFecha_anuncio(Date fecha_anuncio) {
		sentencia = sentencia + " and a.fecha_anuncio = '" + fecha_anuncio + "'";
		return this;
	}

	public CriterioBusqueda2Builder conFecha_ultima_actualizacion(Date fecha_ultima_actualizacion) {
		sentencia = sentencia + " and a.fecha_ultima_actualizacion = '" + fecha_ultima_actualizacion + "'";
		return this;
	}
	
	public CriterioBusqueda2 build() {
		return new CriterioBusqueda2(this);
	}
	
	public String getSentencia() {
		return this.sentencia;
	}
	
	public String getLocalidad() {
		return this.localidad;
	}
	
	public String getTipo_anuncio () {
		return this.tipo_anuncio;
	}
	
	
	public boolean comprobarCadena(String cadena) {
		boolean valida = true;
		char c;
		for(int i = 0; i < cadena.length(); i ++) {
			c = cadena.charAt(i);
			if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == ' '))) {
				valida = false;
			}
		}
		
		return valida;
	}
}
