package es.dao.util;

import java.util.Date;

public class ValoresBusqueda {
	
	private String tipo_anuncio;
	private String localidad;
	private String calle;
	private int piso;

	private double min_metros2;
	private double max_metros2;

	private int num_habitaciones;
	private int min_num_habitaciones;

	private int num_banos;
	private int min_num_banos;

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
	private Date fecha_ultima_actualizacion;
	
	public ValoresBusqueda() {}
	
	

	public ValoresBusqueda(String tipo_anuncio, String localidad, String calle, int piso, double min_metros2,
			double max_metros2, int num_habitaciones, int min_num_habitaciones, int num_banos, int min_num_banos,
			String tipo_edificacion, String tipo_obra, String equipamiento, String exteriores, boolean garaje,
			boolean trastero, boolean ascensor, boolean ultima_planta, boolean mascotas, double min_precio,
			double max_precio, Date fecha_anunciado, Date fecha_ultima_actualizacion) {
		super();
		this.tipo_anuncio = tipo_anuncio;
		this.localidad = localidad;
		this.calle = calle;
		this.piso = piso;
		this.min_metros2 = min_metros2;
		this.max_metros2 = max_metros2;
		this.num_habitaciones = num_habitaciones;
		this.min_num_habitaciones = min_num_habitaciones;
		this.num_banos = num_banos;
		this.min_num_banos = min_num_banos;
		this.tipo_edificacion = tipo_edificacion;
		this.tipo_obra = tipo_obra;
		this.equipamiento = equipamiento;
		this.exteriores = exteriores;
		this.garaje = garaje;
		this.trastero = trastero;
		this.ascensor = ascensor;
		this.ultima_planta = ultima_planta;
		this.mascotas = mascotas;
		this.min_precio = min_precio;
		this.max_precio = max_precio;
		this.fecha_anunciado = fecha_anunciado;
		this.fecha_ultima_actualizacion = fecha_ultima_actualizacion;
	}

	public String getTipo_anuncio() {
		return tipo_anuncio;
	}

	public void setTipo_anuncio(String tipo_anuncio) {
		this.tipo_anuncio = tipo_anuncio;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getPiso() {
		return piso;
	}

	public void setPiso(int piso) {
		this.piso = piso;
	}

	public double getMin_metros2() {
		return min_metros2;
	}

	public void setMin_metros2(double min_metros2) {
		this.min_metros2 = min_metros2;
	}

	public double getMax_metros2() {
		return max_metros2;
	}

	public void setMax_metros2(double max_metros2) {
		this.max_metros2 = max_metros2;
	}

	public int getNum_habitaciones() {
		return num_habitaciones;
	}

	public void setNum_habitaciones(int num_habitaciones) {
		this.num_habitaciones = num_habitaciones;
	}

	public int getMin_num_habitaciones() {
		return min_num_habitaciones;
	}

	public void setMin_num_habitaciones(int min_num_habitaciones) {
		this.min_num_habitaciones = min_num_habitaciones;
	}

	public int getNum_banos() {
		return num_banos;
	}

	public void setNum_banos(int num_banos) {
		this.num_banos = num_banos;
	}

	public int getMin_num_banos() {
		return min_num_banos;
	}

	public void setMin_num_banos(int min_num_banos) {
		this.min_num_banos = min_num_banos;
	}

	public String getTipo_edificacion() {
		return tipo_edificacion;
	}

	public void setTipo_edificacion(String tipo_edificacion) {
		this.tipo_edificacion = tipo_edificacion;
	}

	public String getTipo_obra() {
		return tipo_obra;
	}

	public void setTipo_obra(String tipo_obra) {
		this.tipo_obra = tipo_obra;
	}

	public String getEquipamiento() {
		return equipamiento;
	}

	public void setEquipamiento(String equipamiento) {
		this.equipamiento = equipamiento;
	}

	public String getExteriores() {
		return exteriores;
	}

	public void setExteriores(String exteriores) {
		this.exteriores = exteriores;
	}

	public boolean isGaraje() {
		return garaje;
	}

	public void setGaraje(boolean garaje) {
		this.garaje = garaje;
	}

	public boolean isTrastero() {
		return trastero;
	}

	public void setTrastero(boolean trastero) {
		this.trastero = trastero;
	}

	public boolean isAscensor() {
		return ascensor;
	}

	public void setAscensor(boolean ascensor) {
		this.ascensor = ascensor;
	}

	public boolean isUltima_planta() {
		return ultima_planta;
	}

	public void setUltima_planta(boolean ultima_planta) {
		this.ultima_planta = ultima_planta;
	}

	public boolean isMascotas() {
		return mascotas;
	}

	public void setMascotas(boolean mascotas) {
		this.mascotas = mascotas;
	}

	public double getMin_precio() {
		return min_precio;
	}

	public void setMin_precio(double min_precio) {
		this.min_precio = min_precio;
	}

	public double getMax_precio() {
		return max_precio;
	}

	public void setMax_precio(double max_precio) {
		this.max_precio = max_precio;
	}

	public Date getFecha_anunciado() {
		return fecha_anunciado;
	}

	public void setFecha_anunciado(Date fecha_anunciado) {
		this.fecha_anunciado = fecha_anunciado;
	}

	public Date getFecha_ultima_actualizacion() {
		return fecha_ultima_actualizacion;
	}

	public void setFecha_ultima_actualizacion(Date fecha_ultima_actualizacion) {
		this.fecha_ultima_actualizacion = fecha_ultima_actualizacion;
	}
	
	
	
}
