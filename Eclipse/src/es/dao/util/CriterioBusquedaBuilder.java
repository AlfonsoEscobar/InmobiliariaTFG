package es.dao.util;

import java.sql.Date;

import es.dao.util.enums.Equipamiento;
import es.dao.util.enums.Exteriores;
import es.dao.util.enums.TipoEdificacion;
import es.dao.util.enums.TipoObra;

public class CriterioBusquedaBuilder {
	private String localidad;
	private String tipo_anuncio;
	
	private String clausula;
	
	public CriterioBusquedaBuilder() {
		this.clausula = "";
	}
	
	public CriterioBusquedaBuilder conLocalidad(String localidad) {
		this.localidad = localidad;
		clausula = clausula + " and i.localidad = '" + localidad + "'";
		return this;
	}
	
	public CriterioBusquedaBuilder conTipo_Anuncio(String tipo_anuncio) {
		this.tipo_anuncio = tipo_anuncio;
		clausula = clausula + " and a.tipo_anuncio = '" + tipo_anuncio + "'";
		return this;
	}
	
	public CriterioBusquedaBuilder conCalle(String calle) {
		clausula = clausula + " and i.calle = '" + calle + "'";
		return this;
	}

	public CriterioBusquedaBuilder conPiso(int piso) {
		clausula = clausula + " and i.piso = '" + piso + "'";
		return this;
	}
	
	public CriterioBusquedaBuilder conMetros2(double min_metros2, double max_metros2) {
		clausula = clausula + " and i.metros2 between " + min_metros2 + " and " + max_metros2;
		return this;
	}


	public CriterioBusquedaBuilder conNum_habitaciones(int num_habitaciones) {
		clausula = clausula + " and i.num_habitaciones = " + num_habitaciones;
		return this;
	}

	public CriterioBusquedaBuilder conMin_num_habitaciones(int min_num_habitaciones) {
		clausula = clausula + " and i.num_habitaciones > " + (min_num_habitaciones - 1);
		return this;
	}
	

	public CriterioBusquedaBuilder conNum_banos(int num_banos) {
		clausula = clausula + " and i.num_banos = " + num_banos;
		return this;
	}

	public CriterioBusquedaBuilder conMin_num_banos(int min_num_banos) {
		clausula = clausula + " and i.num_banos > " + (min_num_banos - 1);
		return this;
	}

	public CriterioBusquedaBuilder conTipo_edificacion(TipoEdificacion tipo_edificacion) {
		clausula = clausula + " and i.tipo_edificacion = '" + tipo_edificacion.name() + "'";
		return this;
	}

	public CriterioBusquedaBuilder conTipo_obra(TipoObra tipo_obra) {
		clausula = clausula + " and i.tipo_obra = '" + tipo_obra.name() + "'";
		return this;
	}

	public CriterioBusquedaBuilder conEquipamiento(Equipamiento equipamiento) {
		clausula = clausula + " and i.equipamiento = '" + equipamiento + "'";
		return this;
	}

	public CriterioBusquedaBuilder conExteriores(Exteriores exteriores) {
		clausula = clausula + " and i.exteriores = '" + exteriores + "'";
		return this;
	}

	public CriterioBusquedaBuilder conGaraje(boolean garaje) {
		clausula = clausula + " and i.garaje = " + garaje;
		return this;
	}

	public CriterioBusquedaBuilder conTrastero(boolean trastero) {
		clausula = clausula + " and i.trastero = " + trastero;
		return this;
	}

	public CriterioBusquedaBuilder conAscensor(boolean ascensor) {
		clausula = clausula + " and i.ascensor = " + ascensor;
		return this;
	}

	public CriterioBusquedaBuilder conUltima_planta(boolean ultima_planta) {
		clausula = clausula + " and i.ultima_planta = " + ultima_planta;
		return this;
	}

	public CriterioBusquedaBuilder conMascotas(boolean mascotas) {
		clausula = clausula + " and i.mascotas = " + mascotas;
		return this;
	}

	public CriterioBusquedaBuilder conPrecio (double min_precio, double max_precio) {
		clausula = clausula + " and a.precio between " + min_precio + " and " + max_precio;
		return this;
	}

	public CriterioBusquedaBuilder conFecha_anuncio(Date fecha_anuncio) {
		clausula = clausula + " and a.fecha_anuncio = '" + fecha_anuncio + "'";
		return this;
	}

	public CriterioBusquedaBuilder conFecha_ultima_actualizacion(Date fecha_ultima_actualizacion) {
		clausula = clausula + " and a.fecha_ultima_actualizacion = '" + fecha_ultima_actualizacion + "'";
		return this;
	}
	
	public CriterioBusqueda build() {
		return new CriterioBusqueda(this);
	}
	
	public String getClausula() {
		return this.clausula;
	}
	
	public String getLocalidad() {
		return this.localidad;
	}
	
	public String getTipo_anuncio () {
		return this.tipo_anuncio;
	}
}
