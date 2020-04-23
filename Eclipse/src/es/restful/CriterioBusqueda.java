package es.restful;

import java.sql.Date;

public class CriterioBusqueda {

	private String localidad;
	private String calle;
	private int piso;

	private float min_metros2;
	private float max_metros2;

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

	private String tipo_anuncio;

	private float min_precio;
	private float max_precio;

	private Date fecha_anunciado;
	private Date fecha_ultima_actualizacion;

	public CriterioBusqueda(String localidad, String tipo_anuncio) {

		this.localidad = localidad;
		this.tipo_anuncio = tipo_anuncio;

	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public void setPiso(int piso) {
		this.piso = piso;
	}

	public void setMin_metros2(float min_metros2) {
		this.min_metros2 = min_metros2;
	}

	public void setMax_metros2(float max_metros2) {
		this.max_metros2 = max_metros2;
	}

	public void setNum_habitaciones(int num_habitaciones) {
		this.num_habitaciones = num_habitaciones;
	}

	public void setMax_num_habitaciones(int max_num_habitaciones) {
		this.max_num_habitaciones = max_num_habitaciones;
	}

	public void setNum_banos(int num_banos) {
		this.num_banos = num_banos;
	}

	public void setMax_num_banos(int max_num_banos) {
		this.max_num_banos = max_num_banos;
	}

	public void setTipo_edificacion(String tipo_edificacion) {
		this.tipo_edificacion = tipo_edificacion;
	}

	public void setTipo_obra(String tipo_obra) {
		this.tipo_obra = tipo_obra;
	}

	public void setEquipamiento(String equipamiento) {
		this.equipamiento = equipamiento;
	}

	public void setExteriores(String exteriores) {
		this.exteriores = exteriores;
	}

	public void setGaraje(boolean garaje) {
		this.garaje = garaje;
	}

	public void setTrastero(boolean trastero) {
		this.trastero = trastero;
	}

	public void setAscensor(boolean ascensor) {
		this.ascensor = ascensor;
	}

	public void setUltima_planta(boolean ultima_planta) {
		this.ultima_planta = ultima_planta;
	}

	public void setMascotas(boolean mascotas) {
		this.mascotas = mascotas;
	}

	public void setMin_precio(float min_precio) {
		this.min_precio = min_precio;
	}

	public void setMax_precio(float max_precio) {
		this.max_precio = max_precio;
	}

	public void setFecha_anunciado(Date fecha_anunciado) {
		this.fecha_anunciado = fecha_anunciado;
	}

	public void setFecha_ultima_actualizacion(Date fecha_ultima_actualizacion) {
		this.fecha_ultima_actualizacion = fecha_ultima_actualizacion;
	}

	
	
	public String criterioSQL() {

		String criterio = "";
		
		String busqueda = "SELECT a.*, b.* from anuncio a, inmuble b on a.id_inmueble = b.id_inmueble"
							+ "WHERE a.tipo_anuncio = " + this.tipo_anuncio 
							+ "and b.localidad = " + this.localidad;
		
		
		if (this.calle != null) {
			criterio += criterio + " b.calle = '" + this.calle + "'";
		}

		if (this.piso != 0) {
			criterio += criterio + " b.piso = " + this.calle;
		}

		if (this.min_metros2 != 0 && this.max_metros2 != 0) {
			criterio += criterio + " b.metros2 between " + this.min_metros2 + " and " + this.max_metros2;
		}
		
		if (this.num_habitaciones != 0) {
			criterio += criterio + " b.num_habitaciones = " + this.num_habitaciones;
		}

		if (this.max_num_habitaciones != 0) {
			criterio += criterio + " b.num_habitaciones > " + (this.max_num_habitaciones - 1);
		}

		if (this.num_banos != 0) {
			criterio += criterio + " b.num_banos = " + this.num_banos;
		}
		
		if (this.max_num_banos != 0) {
			criterio += criterio + " b.num_banos > " + (this.max_num_banos - 1);
		}
		
		if (this.tipo_edificacion != null) {
			criterio += criterio + " b.tipo_edificacion = '" + this.tipo_edificacion + "'";
		}

		if (this.tipo_obra != null) {
			criterio += criterio + " b.tipo_obra = '" + this.tipo_obra + "'";
		}

		if (this.equipamiento != null) {
			
		}
		
		if (this.exteriores != null) {
			criterio += criterio + " b.exteriores = '" + this.exteriores + "'";
		}
		
		if (this.garaje) {
			criterio += criterio + " b.garaje = " + this.garaje ;
		}

		if (this.trastero) {
			criterio += criterio + " b.trastero = " + this.trastero ;
		}

		if (this.ascensor) {
			criterio += criterio + " b.ascensor = " + this.ascensor ;
		}
		
		if (this.ultima_planta) {
			criterio += criterio + " b.ultima_planta = " + this.ultima_planta ;
		}
		
		if (this.mascotas) {
			criterio += criterio + " b.mascotas = " + this.mascotas ;
		}
		
		if (this.min_precio != 0 && this.max_precio != 0) {
			criterio += criterio + " a.precio between " + this.min_precio + " and " + this.max_precio;
		}
		
		if (this.fecha_anunciado != null) {
			
		}
		
		if (this.fecha_ultima_actualizacion != null) {
			
		}

		return busqueda + criterio + ";";
	}

}
