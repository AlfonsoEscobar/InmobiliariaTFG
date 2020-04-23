package es.dao.util;

public class CritBusq {
	private double metros2_min;
	private double metros2_max;
	private int num_habitaciones;
	private int num_banos;
	private TipoEdificacion tipo_edificacion;
	private TipoObra tipo_obra;
	private Equipamiento equipamiento;
	private Exteriores exteriores;
	private boolean garaje;
	private boolean trastero;
	private boolean ascensor;
	private boolean ultima_planta;
	private boolean mascotas;
	
	public double getMetros2_min() {
		return metros2_min;
	}
	public void setMetros2_min(double metros2_min) {
		this.metros2_min = metros2_min;
	}
	public double getMetros2_max() {
		return metros2_max;
	}
	public void setMetros2_max(double metros2_max) {
		this.metros2_max = metros2_max;
	}
	public int getNum_habitaciones() {
		return num_habitaciones;
	}
	public void setNum_habitaciones(int num_habitaciones) {
		this.num_habitaciones = num_habitaciones;
	}
	public int getNum_banos() {
		return num_banos;
	}
	public void setNum_banos(int num_banos) {
		this.num_banos = num_banos;
	}
	public TipoEdificacion getTipo_edificacion() {
		return tipo_edificacion;
	}
	public void setTipo_edificacion(TipoEdificacion tipo_edificacion) {
		this.tipo_edificacion = tipo_edificacion;
	}
	public TipoObra getTipo_obra() {
		return tipo_obra;
	}
	public void setTipo_obra(TipoObra tipo_obra) {
		this.tipo_obra = tipo_obra;
	}
	public Equipamiento getEquipamiento() {
		return equipamiento;
	}
	public void setEquipamiento(Equipamiento equipamiento) {
		this.equipamiento = equipamiento;
	}
	public Exteriores getExteriores() {
		return exteriores;
	}
	public void setExteriores(Exteriores exteriores) {
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
	
	public String crearSentencia() {
		String sentencia = "Select * from inmueble where ";
		String clausula = "";
		if (metros2_min != 0) {
			if(metros2_max == 0) {
				sentencia = anadirFiltro(sentencia, "metros2 > " + metros2_min);
				clausula = "metros2 > " + metros2_min;
			}else {
				clausula = "metro2 between (" + this.metros2_min + " and " + this.metros2_max + " )"; 
			}
		}
		if (num_habitaciones != 0) {
			//sentencia
		}
		
		return sentencia;
	}
	
	public String anadirFiltro(String sentencia, String clausula) {
		return sentencia = sentencia + " and " + clausula;
	}
	
	/*private double metro2_min;
	private double metros2_max;
	private int num_habitaciones;
	private int num_banos;
	private TipoEdificacion tipo_edificacion;
	private TipoObra tipo_obra;
	private Equipamiento equipamiento;
	private Exteriores exteriores;
	private boolean garaje;
	private boolean trastero;
	private boolean ascensor;
	private boolean ultima_planta;
	private boolean mascotas;*/
}
