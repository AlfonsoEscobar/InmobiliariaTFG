package es.dao.util;

import java.sql.Date;

public class CriterioBusqueda2Builder {
	private String localidad;
	private String tipo_anuncio;
	private String sentencia;

	public CriterioBusqueda2Builder() {
		this.sentencia = "";
	}

	public CriterioBusqueda2Builder conLocalidad(String localidad) {
		if (comprobarCadena(localidad) == true) 
			this.localidad = localidad;
		return this;
	}

	public CriterioBusqueda2Builder conTipo_Anuncio(String tipo_anuncio) {
		if (comprobarCadena(localidad) == true) 
			this.tipo_anuncio = tipo_anuncio;
		return this;
	}

	public CriterioBusqueda2Builder conCalle(String calle) {
		if (comprobarCadena(calle) == true)
			sentencia = sentencia + " and i.calle = '" + calle + "'";
		return this;
	}

	public CriterioBusqueda2Builder conPiso(int piso) {
		if (piso != 0)
			sentencia = sentencia + " and i.piso = " + piso;

		return this;
	}

	public CriterioBusqueda2Builder conMetros2(double min_metros2, double max_metros2) {
		if (max_metros2 > min_metros2) {
			if (min_metros2 >= 0 && max_metros2 != 0)
				sentencia = sentencia + " and i.metros2 between " + min_metros2 + " and " + max_metros2;
			else if (min_metros2 >= 0 && max_metros2 == 0)
				sentencia = sentencia + " and i.metros2 between " + min_metros2 + " and " + 9999.0;
		}

		return this;
	}

	public CriterioBusqueda2Builder conNum_habitaciones(int num_habitaciones) {
		if (num_habitaciones != 0)
			sentencia = sentencia + " and i.num_habitaciones = " + num_habitaciones;
		return this;
	}

	public CriterioBusqueda2Builder conMin_num_habitaciones(int min_num_habitaciones) {
		if (min_num_habitaciones != 0)
			sentencia = sentencia + " and i.num_habitaciones > " + (min_num_habitaciones - 1);
		return this;
	}

	public CriterioBusqueda2Builder conNum_banos(int num_banos) {
		if (num_banos != 0)
			sentencia = sentencia + " and i.num_banos = " + num_banos;
		return this;
	}

	public CriterioBusqueda2Builder conMin_num_banos(int min_num_banos) {
		if (min_num_banos != 0)
			sentencia = sentencia + " and i.num_banos > " + (min_num_banos - 1);
		return this;
	}

	public CriterioBusqueda2Builder conTipo_edificacion(String tipo_edificacion) {
		if (comprobarCadena(tipo_edificacion) == true)
			sentencia = sentencia + " and i.tipo_edificacion = '" + tipo_edificacion + "'";
		return this;
	}

	public CriterioBusqueda2Builder conTipo_obra(String tipo_obra) {
		if (comprobarCadena(tipo_obra) == true)
			sentencia = sentencia + " and i.tipo_obra = '" + tipo_obra + "'";
		return this;
	}

	public CriterioBusqueda2Builder conEquipamiento(String equipamiento) {
		if (comprobarCadena(equipamiento) == true)
			sentencia = sentencia + " and i.equipamiento = '" + equipamiento + "'";
		return this;
	}

	public CriterioBusqueda2Builder conExteriores(String exteriores) {
		if (comprobarCadena(exteriores) == true)
			sentencia = sentencia + " and i.exteriores = '" + exteriores + "'";
		return this;
	}

	public CriterioBusqueda2Builder conGaraje(String garaje) {
		if(garaje != null)
			sentencia = sentencia + " and i.garaje = " + garaje;
		return this;
	}

	public CriterioBusqueda2Builder conTrastero(String trastero) {
		if(trastero != null)
			sentencia = sentencia + " and i.trastero = " + trastero;
		return this;
	}

	public CriterioBusqueda2Builder conAscensor(String ascensor) {
		if(ascensor != null)
			sentencia = sentencia + " and i.ascensor = " + ascensor;
		return this;
	}

	public CriterioBusqueda2Builder conUltima_planta(String ultima_planta) {
		if(ultima_planta != null)
			sentencia = sentencia + " and i.ultima_planta = " + ultima_planta;
		return this;
	}

	public CriterioBusqueda2Builder conMascotas(String mascotas) {
		if(mascotas != null)
			sentencia = sentencia + " and i.mascotas = " + mascotas;
		return this;
	}

	public CriterioBusqueda2Builder conPrecio(double min_precio, double max_precio) {
		if (min_precio < max_precio) {
			if (min_precio >= 0 && max_precio != 0)
				sentencia = sentencia + " and a.precio between " + min_precio + " and " + max_precio;
			else if (min_precio >= 0 && max_precio == 0)
				sentencia = sentencia + " and a.precio between " + min_precio + " and " + 999999.0;
		}

		return this;
	}

	public CriterioBusqueda2Builder conFecha_anuncio(Date fecha_anuncio) {
		if (fecha_anuncio != null)
			sentencia = sentencia + " and a.fecha_anunciado = '" + fecha_anuncio + "'";
		return this;
	}

	public CriterioBusqueda2Builder conFecha_ultima_actualizacion(Date fecha_ultima_actualizacion) {
		if (fecha_ultima_actualizacion != null)
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

	public String getTipo_anuncio() {
		return this.tipo_anuncio;
	}

	public boolean comprobarCadena(String cadena) {
		boolean valida = true;
		if (cadena.equals(null)) {
			valida = false;
		} else {
			char c;
			for (int i = 0; i < cadena.length(); i++) {
				c = cadena.charAt(i);
				if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == ' '))) {
					valida = false;
				}
			}
		}
		return valida;
	}
}
